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

import com.chengzi.apiunion.common.data.beans.SimpleRouteOperate;
import com.chengzi.apiunion.common.module.shard.enums.TableShardStrategyDefinitionEnum;
import com.chengzi.apiunion.common.module.shard.strategy.DivisorTableShardStrategy;
import com.chengzi.apiunion.common.mybatis.shard.ShardUtil;
import com.chengzi.apiunion.common.web.controllers.AbstractManageController;
import com.chengzi.apiunion.item.dao.ItemSkuMapper;
import com.chengzi.apiunion.item.dao.ItemSkuSupplierApproveMapper;
import com.chengzi.apiunion.item.dao.ItemSkuSupplierMapper;
import com.chengzi.apiunion.item.enums.ItemApproveStatusEnum;
import com.chengzi.apiunion.item.enums.ItemApproveTypeEnum;
import com.chengzi.apiunion.item.pojo.ItemRichDO;
import com.chengzi.apiunion.item.pojo.ItemSkuDO;
import com.chengzi.apiunion.item.pojo.ItemSkuSupplierApproveDO;
import com.chengzi.apiunion.item.pojo.ItemSkuSupplierApproveQuery;
import com.chengzi.apiunion.item.pojo.ItemSkuSupplierDO;
import com.chengzi.apiunion.item.pojo.ItemSkuSupplierQuery;
import com.chengzi.apiunion.item.pojo.ItemSkuSupplierQuery.StatusTuple;
import com.chengzi.apiunion.item.pojo.ThirdItemSkuAttr;
import com.chengzi.apiunion.item.service.ItemService;
import com.chengzi.apiunion.item.service.ItemSkuSupplierApproveService;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import com.chengzi.common.web.formbean.EmptyForm;

/**
 * 修复商品审核记录
 * 
 * @author Kolor
 */
public class FixItemApproveController extends AbstractManageController<EmptyForm> {

    @Override
    protected Result<?> doInnerBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {
        ItemService itemService = BeanFactory.getBean(ItemService.class);

        ItemSkuSupplierMapper itemSkuSupplierMapper = BeanFactory.getBean(ItemSkuSupplierMapper.class);
        ItemSkuSupplierQuery query = new ItemSkuSupplierQuery();
        query.setIsDeleted(null);
        query.setMatchItemId(false);
        query.setStatusList(CollectionUtil
                .asArrayList(new StatusTuple().setApprovedStatusList(CollectionUtil.asArrayList(ItemApproveStatusEnum.WAIT_APPROVE.getCode()))));

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
                        ItemSkuSupplierApproveMapper approveMapper = BeanFactory.getBean(ItemSkuSupplierApproveMapper.class);
                        ItemSkuSupplierApproveService itemSkuSupplierApproveService = BeanFactory.getBean(ItemSkuSupplierApproveService.class);
                        ItemSkuMapper itemSkuMapper = BeanFactory.getBean(ItemSkuMapper.class);

                        ItemSkuSupplierApproveQuery approveQuery = new ItemSkuSupplierApproveQuery();
                        approveQuery.setItemIds(CollectionUtil.asArrayList(itemSkuSupplierDO.getItemId()));
                        approveQuery.setChannelType(itemSkuSupplierDO.getChannelType());
                        approveQuery.setSkuId(itemSkuSupplierDO.getSkuId());
                        approveQuery.setSupplierId(itemSkuSupplierDO.getSupplierId());
                        approveQuery.setStatusList(CollectionUtil.asArrayList(ItemApproveStatusEnum.WAIT_APPROVE.getCode()));

                        List<ItemSkuSupplierApproveDO> approveList = approveMapper.selectByQuery(approveQuery);
                        ItemSkuDO skuDO = itemSkuMapper.selectById(SimpleRouteOperate.of(itemSkuSupplierDO.getSkuId()),
                                itemSkuSupplierDO.getItemId());
                        if (skuDO == null || skuDO.isDeleted()) {
                            if (CollectionUtil.isNotEmpty(approveList)) {
                                List<Long> ids = CollectionUtil.getFieldValueList(approveList, "id");
                                itemSkuSupplierApproveService.updateApprovedStatus(ids, ItemApproveStatusEnum.INVALID, "数据修正", 0L);
                            }
                        } else {
                            if (CollectionUtil.isEmpty(approveList)) {
                                ItemSkuSupplierApproveDO approveDO = new ItemSkuSupplierApproveDO();
                                approveDO.setChannelType(itemSkuSupplierDO.getChannelType());
                                approveDO.setInfo("数据修正");
                                approveDO.setItemId(itemSkuSupplierDO.getItemId());
                                approveDO.setRouteId(itemSkuSupplierDO.getRouteId());
                                approveDO.setSkuId(itemSkuSupplierDO.getSkuId());
                                approveDO.setSnapshot(itemSkuSupplierDO.getSupplierItemSnapshot());
                                approveDO.setStatus(ItemApproveStatusEnum.WAIT_APPROVE.getCode());
                                approveDO.setSupplierId(itemSkuSupplierDO.getSupplierId());
                                approveDO.setType(ItemApproveTypeEnum.NEW_SUPPLIER.getCode());

                                itemSkuSupplierApproveService.addApproveWithTransactional(approveDO);
                            }
                        }
                    }
                }
            }
        }
        return Result.buildSuccessResult(builder.toString());
    }

}
