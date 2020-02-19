/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.controllers.run;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.data.beans.ListRouteOperate;
import com.chengzi.apiunion.common.data.search.elastic.SearchScrollable;
import com.chengzi.apiunion.common.data.search.elastic.pojo.ScrollResult;
import com.chengzi.apiunion.common.data.search.elastic.scroll.ScrollDataProcessor;
import com.chengzi.apiunion.common.event.EventBusFactory;
import com.chengzi.apiunion.common.web.controllers.AbstractManageController;
import com.chengzi.apiunion.item.dao.ItemSkuSupplierApproveMapper;
import com.chengzi.apiunion.item.dao.ItemSkuSupplierMapper;
import com.chengzi.apiunion.item.enums.ItemApproveStatusEnum;
import com.chengzi.apiunion.item.event.pojo.ItemApproveUpdatedEvent;
import com.chengzi.apiunion.item.pojo.ItemSkuSupplierDO;
import com.chengzi.apiunion.item.pojo.search.ItemApproveSearchBO;
import com.chengzi.apiunion.item.util.ItemShardUtil;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import com.chengzi.common.web.formbean.EmptyForm;

/**
 * 修复商品审核记录
 * 
 * @author Kolor
 */
public class FixInvalidApproveController extends AbstractManageController<EmptyForm> {

    @Override
    protected Result<?> doInnerBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {
        SearchScrollable searchScrollable = BeanFactory.getBeansOfType(SearchScrollable.class).get("itemApproveSearchServiceImpl");
        ItemSkuSupplierMapper itemSkuSupplierMapper = BeanFactory.getBean(ItemSkuSupplierMapper.class);
        ItemSkuSupplierApproveMapper itemSkuSupplierApproveMapper = BeanFactory.getBean(ItemSkuSupplierApproveMapper.class);
        List<Long> updateIds = new ArrayList<>();
        searchScrollable.scroll(600_000, 1000, requestBuilder -> {
                    // 组装条件
                    BoolQueryBuilder postFilter = QueryBuilders.boolQuery();
                    postFilter.must(QueryBuilders.termQuery("joinType", "approve"));
                    postFilter.must(QueryBuilders.termsQuery("approveStatus", CollectionUtil.asArrayList(ItemApproveStatusEnum.WAIT_APPROVE.getCode())));
                    postFilter.must(QueryBuilders.termQuery("routeId", RequestContext.getRouteId()));
                    requestBuilder.setPostFilter(postFilter);
                },
                new ScrollDataProcessor<ItemApproveSearchBO>() {
                    @Override
                    public void onScrolled(ScrollResult<ItemApproveSearchBO> scrollResult) {
                        ArrayList<ItemApproveSearchBO> needUpdateList = new ArrayList<>();
                        List<ItemApproveSearchBO> approveList = scrollResult.getData();
                        if (CollectionUtil.isNotEmpty(approveList)) {
                            Map<String, List<ItemSkuSupplierDO>> supplierListMap = new HashMap<>();
                            Set<Long> itemIds = CollectionUtil.getDisctinctFieldValueList(approveList, "approveItemId");
                            Map<Long, List<Long>> shardItemIdsMap = ItemShardUtil.shardItemIdsForItemSkuSupplier(new ArrayList<>(itemIds));
                            for (List<Long> shardItemIds : shardItemIdsMap.values()) {
                                List<ItemSkuSupplierDO> supplierDOList = itemSkuSupplierMapper.selectByItemIds(ListRouteOperate.of(shardItemIds), null);
                                Map<String, List<ItemSkuSupplierDO>> map = CollectionUtil.toListMap(supplierDOList, "itemId+skuId+channelType+supplierId");
                                supplierListMap.putAll(map);
                            }
                            Iterator<ItemApproveSearchBO> iterator = approveList.iterator();
                            while (iterator.hasNext()) {
                                ItemApproveSearchBO itemApproveSearchBO = iterator.next();
                                String key = buildKey(itemApproveSearchBO);
                                List<ItemSkuSupplierDO> itemSkuSupplierDOList = supplierListMap.get(key);
                                if (CollectionUtil.isEmpty(itemSkuSupplierDOList)) {
                                    needUpdateList.add(itemApproveSearchBO);
                                }
                            }
                        }
                        if (!needUpdateList.isEmpty()) {
                            List<Long> approveIds = CollectionUtil.getFieldValueList(needUpdateList, "approveId");
                            itemSkuSupplierApproveMapper.updateApprovedStatusByIds(ListRouteOperate.of(approveIds),
                                    ItemApproveStatusEnum.INVALID.getCode(),"自动失效",0L);
                            for (ItemApproveSearchBO itemApproveSearchBO : needUpdateList) {
                                EventBusFactory.getSyncEventBus().post(new ItemApproveUpdatedEvent(itemApproveSearchBO.getApproveItemId()));
                            }
                            updateIds.addAll(approveIds);
                        }
                    }
                });
        return Result.buildSuccessResult(String.format("更新成功,处理数据如下：%s",updateIds.toString()));
    }

    public String buildKey(ItemApproveSearchBO itemApproveSearchBO){
            return String.format("%d+%d+%d+%d",itemApproveSearchBO.getApproveItemId(),itemApproveSearchBO.getApproveSkuId(),
                    itemApproveSearchBO.getApproveChannelType(),itemApproveSearchBO.getApproveSupplierId());
    }

}
