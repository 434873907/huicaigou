/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.controllers.run;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.common.module.shard.enums.TableShardStrategyDefinitionEnum;
import com.chengzi.apiunion.common.module.shard.strategy.DivisorTableShardStrategy;
import com.chengzi.apiunion.common.mybatis.shard.ShardUtil;
import com.chengzi.apiunion.common.web.controllers.AbstractManageController;
import com.chengzi.apiunion.item.dao.ItemSkuSupplierMapper;
import com.chengzi.apiunion.item.dao.ThirdItemMapper;
import com.chengzi.apiunion.item.enums.ItemApproveStatusEnum;
import com.chengzi.apiunion.item.pojo.ItemRichDO;
import com.chengzi.apiunion.item.pojo.ItemSkuSupplierDO;
import com.chengzi.apiunion.item.pojo.ItemSkuSupplierQuery;
import com.chengzi.apiunion.item.pojo.ItemSkuSupplierQuery.StatusTuple;
import com.chengzi.apiunion.item.pojo.ThirdItemDO;
import com.chengzi.apiunion.item.pojo.ThirdItemQuery;
import com.chengzi.apiunion.item.pojo.ThirdItemSkuAttr;
import com.chengzi.apiunion.item.service.ItemService;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import com.chengzi.common.web.formbean.EmptyForm;

/**
 * 修复在ThirdItem中不存在关联的供应商
 * 
 * @author Kolor
 */
public class FixItemSkuSupplierController extends AbstractManageController<EmptyForm> {

    @Override
    protected Result<?> doInnerBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {
        ThirdItemMapper thirdItemMapper = BeanFactory.getBean(ThirdItemMapper.class);
        ItemService itemService = BeanFactory.getBean(ItemService.class);
        ThirdItemQuery thirdQuery = new ThirdItemQuery();
        List<ThirdItemDO> thirdItemList = thirdItemMapper.selectByQuery(thirdQuery);
        Map<String, ThirdItemDO> thirdItemMap = CollectionUtil.toMap(thirdItemList, "thirdItemId");

        ItemSkuSupplierMapper itemSkuSupplierMapper = BeanFactory.getBean(ItemSkuSupplierMapper.class);
        ItemSkuSupplierQuery query = new ItemSkuSupplierQuery();
        query.setIsDeleted(null);
        query.setMatchItemId(false);

        StringBuilder builder = new StringBuilder();
        Set<String> tableNames = ShardUtil.loadTableSchema(TableShardStrategyDefinitionEnum.ITEM_SKU_SUPPLIER);
        DivisorTableShardStrategy shardStrategy = (DivisorTableShardStrategy) TableShardStrategyDefinitionEnum.ITEM_SKU_SUPPLIER.getShardStrategy();
        for (String tableName : tableNames) {
            int idx = StringUtils.lastIndexOf(tableName, "_");
            int suffixNum = Integer.parseInt(StringUtils.substring(tableName, idx + 1));

            query.setItemId(suffixNum * shardStrategy.getDivisor() + 1);
            List<ItemSkuSupplierDO> list = itemSkuSupplierMapper.selectByQuery(query);
            Set<Long> itemIds = CollectionUtil.getDisctinctFieldValueList(list, "itemId");
            List<ItemRichDO> itemList = itemService.getItemRichByIds(itemIds);
            Map<Long, ItemRichDO> itemMap = CollectionUtil.toMap(itemList, "id");

            for (ItemSkuSupplierDO itemSkuSupplierDO : list) {
                ItemRichDO itemRich = itemMap.get(itemSkuSupplierDO.getItemId());
                if (itemRich == null || itemRich.isDeleted()) {
                    continue;
                }
                if (StringUtils.isNotBlank(itemSkuSupplierDO.getThirdAttr())) {
                    ThirdItemSkuAttr thirdAttr = ThirdItemSkuAttr.fromJson(itemSkuSupplierDO.getThirdAttr());
                    if (thirdAttr != null) {
                        ThirdItemDO thirdItemDO = thirdItemMap.get(thirdAttr.getThirdItemId());
                        if (thirdItemDO == null || thirdItemDO.getItemId() != itemSkuSupplierDO.getItemId()) {
                            builder.append(
                                    String.format("UPDATE au_item_sku_supplier_%d SET is_deleted = 1 WHERE id = %d and item_id = %d; -- %s",
                                            suffixNum,
                                            itemSkuSupplierDO.getId(), itemSkuSupplierDO.getItemId(),
                                            thirdItemDO == null ? "" : thirdItemDO.getItemId()))
                                    .append("\r\n");
                            ;
                        }
                    }
                }
            }
        }
        return Result.buildSuccessResult(builder.toString());
    }

}
