package com.chengzi.apiunion.procurement.admin.web.controllers.run;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.data.search.elastic.SearchScrollable;
import com.chengzi.apiunion.common.data.search.elastic.pojo.ScrollResult;
import com.chengzi.apiunion.common.data.search.elastic.scroll.ScrollDataProcessor;
import com.chengzi.apiunion.common.web.controllers.AbstractManageController;
import com.chengzi.apiunion.item.pojo.search.ItemApproveSearchBO;
import com.chengzi.apiunion.order.pojo.OrderDO;
import com.chengzi.apiunion.order.pojo.search.OrderSearchBO;
import com.chengzi.apiunion.order.service.OrderSearchService;
import com.chengzi.apiunion.order.service.OrderService;
import com.chengzi.apiunion.order.service.impl.OrderSearchServiceImpl;
import com.chengzi.apiunion.procurement.admin.web.controllers.run.form.EsOrderRefreshForm;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author 月汐
 * @date 2019/07/29 20:51
 */
public class EsOrderRefreshController extends AbstractManageController<EsOrderRefreshForm> {
    @Override
    protected Result<?> doInnerBiz(HttpServletRequest request, HttpServletResponse response, EsOrderRefreshForm command) throws Exception {
        OrderService orderService = BeanFactory.getBean(OrderService.class);

        // SearchScrollable searchScrollable = BeanFactory.getBeansOfType(SearchScrollable.class).get("orderSearchServiceImpl");
        // searchScrollable.scroll(600_000, 500, requestBuilder -> {
        //             // 组装条件
        //             BoolQueryBuilder postFilter = QueryBuilders.boolQuery();
        //             postFilter.must(QueryBuilders.termQuery("routeId", RequestContext.getRouteId()));
        //             requestBuilder.setPostFilter(postFilter);
        //         },
        //         new ScrollDataProcessor<OrderSearchBO>() {
        //             @Override
        //             public void onScrolled(ScrollResult<OrderSearchBO> scrollResult) {
        //                 List<OrderSearchBO> list = scrollResult.getData();
        //                 if (CollectionUtil.isNotEmpty(list)) {
        //                     Map<String, Long> orderNumUserIdMap = new HashMap<>();
        //                     for (OrderSearchBO bo : list) {
        //                         orderNumUserIdMap.put(bo.getOrderNum(), bo.getUserId());
        //                     }
        //                     List<OrderDO> orderDOList = orderService.getOrderByOrderNums(orderNumUserIdMap);
        //                     Map<String, OrderDO> orderNumMap = CollectionUtil.toMap(orderDOList, "orderNum");
        //
        //                     Iterator<OrderSearchBO> iterator = list.iterator();
        //                     while (iterator.hasNext()){
        //                         OrderSearchBO orderSearchBO = iterator.next();
        //                         OrderDO orderDO = orderNumMap.get(orderSearchBO.getOrderNum());
        //                         if (orderDO != null) {
        //                             iterator.remove();
        //                         }
        //                     }
        //                     for (OrderSearchBO bo : list) {
        //                         searchScrollable.deleteById(bo.get_id());
        //                     }
        //                 }
        //             }
        //         });

        return orderService.updateOrderEs(command.getTableNum(), false);
    }
}
