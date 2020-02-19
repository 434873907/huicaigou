package com.chengzi.apiunion.procurement.admin.web.controllers.supplier.order;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.order.enums.OrderStatusEnum;
import com.chengzi.apiunion.procurement.admin.web.formbean.supplier.order.StatisticByDayForm;
import com.chengzi.apiunion.supplier.common.order.query.SupplierOrderSearchQuery;
import com.chengzi.apiunion.supplier.common.order.service.SupplierOrderExpressSearchService;
import com.chengzi.common.data.support.Range;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 月汐
 * @date 2019/3/15 14:36
 */
public class StatisticByDayController extends AbstractApiController<StatisticByDayForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, StatisticByDayForm command) throws Exception {
        SupplierOrderExpressSearchService searchService = BeanFactory.getBean(SupplierOrderExpressSearchService.class);

        SupplierOrderSearchQuery query = new SupplierOrderSearchQuery();

        Range<Date> dateRange = new Range<>();
        dateRange.setLeft(command.getStartTime());
        dateRange.setRight(command.getEndTime());
        query.setDateRange(dateRange);

        return Result.buildSuccessResult(searchService.statisticByDay(query));
    }

    private List<Integer> getOrderStatus() {
        List<Integer> statusList = new ArrayList<>();

        // 已付款
        statusList.add(OrderStatusEnum.ORDER_STATUS_WAIT_SEND.getCode());
        // 已发货
        statusList.add(OrderStatusEnum.ORDER_STATUS_WAIT_CONFIRM.getCode());
        // 已签收
        statusList.add(OrderStatusEnum.ORDER_STATUS_COMPLETE.getCode());
        // 已退款
        statusList.add(OrderStatusEnum.ORDER_STATUS_REFUND.getCode());

        return statusList;
    }
}
