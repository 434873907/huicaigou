/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.controllers.run;

import com.chengzi.apiunion.common.data.beans.ListRouteOperate;
import com.chengzi.apiunion.common.event.EventBusFactory;
import com.chengzi.apiunion.common.module.shard.enums.TableShardStrategyDefinitionEnum;
import com.chengzi.apiunion.common.module.shard.strategy.DivisorTableShardStrategy;
import com.chengzi.apiunion.common.mybatis.shard.ShardUtil;
import com.chengzi.apiunion.common.web.controllers.AbstractManageController;
import com.chengzi.apiunion.item.dao.ItemSkuSupplierApproveMapper;
import com.chengzi.apiunion.item.dao.ItemSkuSupplierMapper;
import com.chengzi.apiunion.item.enums.ItemApproveStatusEnum;
import com.chengzi.apiunion.item.event.pojo.ItemApproveUpdatedEvent;
import com.chengzi.apiunion.item.event.pojo.ItemsUpdatedEvent;
import com.chengzi.apiunion.item.pojo.*;
import com.chengzi.apiunion.item.pojo.ItemSkuSupplierQuery.StatusTuple;
import com.chengzi.apiunion.item.service.ItemService;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import com.chengzi.common.web.formbean.EmptyForm;
import org.apache.commons.lang3.StringUtils;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 修复商品审核记录
 * 
 * @author Kolor
 */
public class FixItemApproveStatusController extends AbstractManageController<EmptyForm> {

    @Override
    protected Result<?> doInnerBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {
        ItemSkuSupplierMapper itemSkuSupplierMapper = BeanFactory.getBean(ItemSkuSupplierMapper.class);
        ItemSkuSupplierApproveMapper approveMapper = BeanFactory.getBean(ItemSkuSupplierApproveMapper.class);
        ItemService itemService = BeanFactory.getBean(ItemService.class);
        List<Long> approveIds = new ArrayList<>();
        List<Long> supplierIds = new ArrayList<>();

        ItemSkuSupplierQuery query = new ItemSkuSupplierQuery();
        query.setMatchItemId(false);
        query.setStatusList(CollectionUtil
                .asArrayList(new StatusTuple().setApprovedStatusList(CollectionUtil.asArrayList(ItemApproveStatusEnum.APPROVED.getCode()))));

        StringBuilder builder = new StringBuilder();
        Set<String> tableNames = ShardUtil.loadTableSchema(TableShardStrategyDefinitionEnum.ITEM_SKU_SUPPLIER);
        DivisorTableShardStrategy shardStrategy = (DivisorTableShardStrategy) TableShardStrategyDefinitionEnum.ITEM_SKU_SUPPLIER.getShardStrategy();
        for (String tableName : tableNames) {
            int idx = StringUtils.lastIndexOf(tableName, "_");
            int suffixNum = Integer.parseInt(StringUtils.substring(tableName, idx + 1));

            query.setItemId(suffixNum * shardStrategy.getDivisor() + 1);
            List<ItemSkuSupplierDO> supplierDOList = itemSkuSupplierMapper.selectByQuery(query);
            if (CollectionUtil.isNotEmpty(supplierDOList)) {
                Map<Long, List<ItemSkuSupplierDO>> skuIdSupplierListMap = CollectionUtil.toListMap(supplierDOList, "skuId");
                Set<Long> itemIds = CollectionUtil.getDisctinctFieldValueList(supplierDOList, "itemId");
                ItemSkuSupplierApproveQuery supplierApproveQuery = new ItemSkuSupplierApproveQuery();
                supplierApproveQuery.setStatusList(CollectionUtil.asArrayList(ItemApproveStatusEnum.WAIT_APPROVE.getCode()));
                supplierApproveQuery.setItemIds(new ArrayList<>(itemIds));
                List<ItemSkuSupplierApproveDO> approveDOList = approveMapper.selectByQueryWithBLOBs(supplierApproveQuery);

                List<ItemSkuSupplierApproveDO> needUpdateApproveList = new ArrayList<>();
                List<ItemSkuSupplierDO> needUpdateSupplierList = new ArrayList<>();

                for (ItemSkuSupplierApproveDO approveDO : approveDOList) {
                    List<ItemSkuSupplierDO> itemSkuSupplierDOList = skuIdSupplierListMap.get(approveDO.getSkuId());
                    if (CollectionUtil.isNotEmpty(itemSkuSupplierDOList)) {
                        for (ItemSkuSupplierDO supplierDO : itemSkuSupplierDOList) {
                            if (supplierDO.getChannelType() == approveDO.getChannelType() && supplierDO.getSupplierId() == approveDO.getSupplierId()) {
                                approveDO.setStatus(ItemApproveStatusEnum.APPROVED.getCode());
                                needUpdateApproveList.add(approveDO);
                                if (approveDO.getSnapshot() != null && !approveDO.getSnapshot().equals(supplierDO.getSupplierItemSnapshot())) {
                                    supplierDO.setSupplierItemSnapshot(approveDO.getSnapshot());
                                    needUpdateSupplierList.add(supplierDO);
                                }
                            }
                        }
                    }
                }

                    if (!needUpdateApproveList.isEmpty()) {
                        Set<Long> ids = CollectionUtil.getDisctinctFieldValueListIgnoreNullKey(needUpdateApproveList, "id");
                        approveMapper.updateApprovedStatusByIds(ListRouteOperate.of(ids), ItemApproveStatusEnum.APPROVED.getCode(), "审核状态修正", 0L);
                        for (ItemSkuSupplierApproveDO itemSkuSupplierApproveDO : needUpdateApproveList) {
                            EventBusFactory.getSyncEventBus().post(new ItemApproveUpdatedEvent(itemSkuSupplierApproveDO.getItemId()));
                            approveIds.add(itemSkuSupplierApproveDO.getId());
                        }
                    }

                    if (!needUpdateSupplierList.isEmpty()) {
                        Set<Long> needUpdateItemIds = new HashSet<>();
                        for (ItemSkuSupplierDO itemSkuSupplierDO : needUpdateSupplierList) {
                            itemSkuSupplierMapper.update(itemSkuSupplierDO);
                            needUpdateItemIds.add(itemSkuSupplierDO.getItemId());
                            supplierIds.add(itemSkuSupplierDO.getId());
                        }
                        EventBusFactory.getSyncEventBus().post(new ItemsUpdatedEvent(needUpdateItemIds));
                    }
            }
        }
        return Result.buildSuccessResult(String.format("更新的审核记录Id如下：%s，更新的库存Id如下：%s",approveIds.toString(),supplierIds.toString()));
    }

}
