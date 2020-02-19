package com.chengzi.apiunion.procurement.admin.web.controllers.run;

import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chengzi.apiunion.common.module.shard.enums.TableShardStrategyDefinitionEnum;
import com.chengzi.apiunion.common.module.shard.strategy.DivisorTableShardStrategy;
import com.chengzi.apiunion.common.mybatis.shard.ShardUtil;
import com.chengzi.apiunion.item.dao.ItemSpecialPriceMapper;
import com.chengzi.apiunion.item.pojo.*;
import org.apache.commons.lang3.StringUtils;
import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.common.data.beans.ListRouteOperate;
import com.chengzi.apiunion.common.event.EventBusFactory;
import com.chengzi.apiunion.common.module.currency.util.AmountUtil;
import com.chengzi.apiunion.common.web.controllers.AbstractManageController;
import com.chengzi.apiunion.item.dao.ItemSkuSupplierMapper;
import com.chengzi.apiunion.item.event.pojo.ItemsUpdatedEvent;
import com.chengzi.apiunion.procurement.admin.web.formbean.run.EsResetItemApproveForm;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;

/**
* @author 行者
 * 天马售价调整
*/
public class UpdateTianmaPriceController extends AbstractManageController<EsResetItemApproveForm> {

    //上浮比例
    private static final BigDecimal RATE = new BigDecimal(1.06);

   @Override
   protected Result<?> doInnerBiz(HttpServletRequest request, HttpServletResponse response, EsResetItemApproveForm command) throws Exception {

       ItemSpecialPriceMapper itemSpecialPriceMapper = BeanFactory.getBean(ItemSpecialPriceMapper.class);
       ItemSkuSupplierMapper itemSkuSupplierMapper = BeanFactory.getBean(ItemSkuSupplierMapper.class);
       ItemSkuSupplierQuery query = new ItemSkuSupplierQuery();
       query.setMatchItemId(false);
       query.setSupplierId(41);

       Set<Long> needUpdateItemIds = new HashSet<>();
       Set<String> tableNames = ShardUtil.loadTableSchema(TableShardStrategyDefinitionEnum.ITEM_SKU_SUPPLIER);
       DivisorTableShardStrategy shardStrategy = (DivisorTableShardStrategy) TableShardStrategyDefinitionEnum.ITEM_SKU_SUPPLIER.getShardStrategy();
       for (String tableName : tableNames) {
           int idx = StringUtils.lastIndexOf(tableName, "_");
           int suffixNum = Integer.parseInt(StringUtils.substring(tableName, idx + 1));
           long shardItemId = suffixNum * shardStrategy.getDivisor() + 1;
           query.setItemId(shardItemId);
           List<ItemSkuSupplierDO> list = itemSkuSupplierMapper.selectByQuery(query);
           if (CollectionUtil.isEmpty(list)) {
               continue;
           }
           List<Long> itemIds = CollectionUtil.getFieldValueList(list, "itemId");
           List<ItemSpecialPriceDO> specialPriceDOList = itemSpecialPriceMapper.selectByItemIds(shardItemId, ListRouteOperate.of(itemIds));
           Map<Long, ItemSpecialPriceDO> skuIdSpecialPriceDOMap = CollectionUtil.toMap(specialPriceDOList, "skuId");
           for (ItemSkuSupplierDO itemSkuSupplierDO : list) {
               BigDecimal sellPrice = itemSkuSupplierDO.getActualChannelPrice().multiply(RATE).setScale(0, BigDecimal.ROUND_HALF_UP);;
               if (itemSkuSupplierDO.getPrice().compareTo(sellPrice) != 0) {
                   itemSkuSupplierDO.setPrice(sellPrice);
                   itemSkuSupplierMapper.update(itemSkuSupplierDO);

                   ItemSpecialPriceDO itemSpecialPriceDO = skuIdSpecialPriceDOMap.get(itemSkuSupplierDO.getSkuId());
                   if (itemSpecialPriceDO != null) {
                       itemSpecialPriceDO.setPrice(sellPrice);
                       itemSpecialPriceMapper.update(itemSpecialPriceDO);
                   }

                   needUpdateItemIds.add(itemSkuSupplierDO.getItemId());
               }
           }
       }
       EventBusFactory.getSyncEventBus().post(new ItemsUpdatedEvent(needUpdateItemIds));
       return Result.buildSuccessResult(String.format("执行成功，共更新%d条数据：%s",needUpdateItemIds.size(),needUpdateItemIds.toString()));
   }
}
