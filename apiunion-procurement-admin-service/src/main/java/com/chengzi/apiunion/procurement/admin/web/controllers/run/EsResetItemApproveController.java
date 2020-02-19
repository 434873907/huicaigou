package com.chengzi.apiunion.procurement.admin.web.controllers.run;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chengzi.apiunion.common.event.EventBusFactory;
import com.chengzi.apiunion.item.event.pojo.ItemApproveUpdatedEvent;
import com.chengzi.common.data.enums.BooleanStatusEnum;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.data.beans.ListRouteOperate;
import com.chengzi.apiunion.common.data.search.elastic.SearchScrollable;
import com.chengzi.apiunion.common.data.search.elastic.pojo.ScrollResult;
import com.chengzi.apiunion.common.data.search.elastic.scroll.ScrollDataProcessor;
import com.chengzi.apiunion.common.web.controllers.AbstractManageController;
import com.chengzi.apiunion.item.dao.ItemSkuMapper;
import com.chengzi.apiunion.item.dao.ItemSkuSupplierApproveMapper;
import com.chengzi.apiunion.item.enums.ItemApproveStatusEnum;
import com.chengzi.apiunion.item.pojo.ItemSkuDO;
import com.chengzi.apiunion.item.pojo.ItemSkuSupplierApproveDO;
import com.chengzi.apiunion.item.pojo.ItemSkuSupplierApproveQuery;
import com.chengzi.apiunion.item.pojo.search.ItemApproveSearchBO;
import com.chengzi.apiunion.item.search.ItemApproveSearchService;
import com.chengzi.apiunion.item.service.ItemSkuService;
import com.chengzi.apiunion.procurement.admin.web.formbean.run.EsResetItemApproveForm;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;

/**
 * @author 月汐
 */
public class EsResetItemApproveController extends AbstractManageController<EsResetItemApproveForm> {
    @Override
    protected Result<?> doInnerBiz(HttpServletRequest request, HttpServletResponse response, EsResetItemApproveForm command){
        if (command.isInit()) {
            ItemApproveSearchService itemApproveSearchService = BeanFactory.getBean(ItemApproveSearchService.class);
            ItemSkuSupplierApproveMapper itemSkuSupplierApproveMapper = BeanFactory.getBean(ItemSkuSupplierApproveMapper.class);
            ItemSkuSupplierApproveQuery query = new ItemSkuSupplierApproveQuery();
            query.setStatusList(CollectionUtil.asArrayList(ItemApproveStatusEnum.WAIT_APPROVE.getCode()));

            List<ItemSkuSupplierApproveDO> list = itemSkuSupplierApproveMapper.selectByQuery(query);
            List<ItemApproveSearchBO> boList = new ArrayList<>();
            for (ItemSkuSupplierApproveDO itemSkuSupplierDO : list) {
                boList.add(ItemApproveSearchBO.convert(itemSkuSupplierDO));
            }
            itemApproveSearchService.bulkIndexItemApproves(boList);
            return Result.buildSuccessResult(String.format("更新成功,共处理了%d条数据", boList.size()));
        } else {

            SearchScrollable searchScrollable = BeanFactory.getBeansOfType(SearchScrollable.class).get("itemApproveSearchServiceImpl");
            ItemSkuSupplierApproveMapper itemSkuSupplierApproveMapper = BeanFactory.getBean(ItemSkuSupplierApproveMapper.class);
            ItemSkuMapper itemSkuMapper = BeanFactory.getBean(ItemSkuMapper.class);
            final AtomicInteger total = new AtomicInteger();
            List<Long> failureIds = new ArrayList<>();
            List<Long> successIds = new ArrayList<>();
            searchScrollable.scroll(600_000, 500, requestBuilder -> {
                // 组装条件
                BoolQueryBuilder postFilter = QueryBuilders.boolQuery();
                postFilter.must(QueryBuilders.termQuery("joinType", "approve"));
                postFilter.must(QueryBuilders.termQuery("routeId", RequestContext.getRouteId()));
                requestBuilder.setPostFilter(postFilter);
            },
                    new ScrollDataProcessor<ItemApproveSearchBO>() {
                        @Override
                        public void onScrolled(ScrollResult<ItemApproveSearchBO> scrollResult) {
                            List<ItemApproveSearchBO> listES = scrollResult.getData();
                            Set<Long> ids = CollectionUtil.getDisctinctFieldValueList(listES, "approveId");
                            List<ItemSkuSupplierApproveDO> listDB = new ArrayList<>();
                            if (CollectionUtil.isNotEmpty(ids)) {
                                listDB = itemSkuSupplierApproveMapper.selectByIds(ListRouteOperate.of(ids));
                            }
                            Map<Long, ItemSkuSupplierApproveDO> idItemSkuSupplierApproveDOMap = CollectionUtil.toMap(listDB, "id");
                            List<ItemSkuDO> ItemSkuDOList = new ArrayList<>();
                            if (CollectionUtil.isNotEmpty(listDB)) {
                                ItemSkuDOList = itemSkuMapper
                                        .selectByItemIds(ListRouteOperate.of(CollectionUtil.getDisctinctFieldValueList(listDB, "itemId")));
                            }
                            Map<Long, ItemSkuDO> idSkuDOMap = CollectionUtil.toMap(ItemSkuDOList, "id");
                            Iterator<ItemApproveSearchBO> iterator = listES.iterator();
                            while (iterator.hasNext()) {
                                ItemApproveSearchBO itemApproveSearchBO = iterator.next();
                                if (idItemSkuSupplierApproveDOMap.containsKey(itemApproveSearchBO.getApproveId())) {
                                    ItemSkuSupplierApproveDO itemSkuSupplierApproveDO = idItemSkuSupplierApproveDOMap
                                            .get(itemApproveSearchBO.getApproveId());
                                    if (!itemSkuSupplierApproveDO.isDeleted()) {
                                        if (itemSkuSupplierApproveDO.getSkuId() == itemApproveSearchBO.getApproveSkuId()) {
                                            if (itemSkuSupplierApproveDO.getStatus() != itemApproveSearchBO.getApproveStatus()) {
                                                EventBusFactory.getSyncEventBus().post(new ItemApproveUpdatedEvent(itemSkuSupplierApproveDO.getItemId()));
                                            }
                                            ItemSkuDO itemSkuDO = idSkuDOMap.get(itemSkuSupplierApproveDO.getSkuId());
                                            if (itemSkuDO != null && !itemSkuDO.isDeleted()) {
                                                iterator.remove();
                                            }
                                        }
                                    }
                                }
                            }

                            for (ItemApproveSearchBO itemApproveSearchBO : listES) {
                                searchScrollable.deleteById(itemApproveSearchBO.buildSearchId());
                                successIds.add(itemApproveSearchBO.getApproveId());
                                total.incrementAndGet();
                            }
                        }
                    });
            return Result.buildSuccessResult(String.format("更新成功,共处理了%d条数据,成功同步数据如下：%s，同步失败数据如下：%s", total.get(),successIds.toString(),failureIds.toString()));
        }
    }
}
