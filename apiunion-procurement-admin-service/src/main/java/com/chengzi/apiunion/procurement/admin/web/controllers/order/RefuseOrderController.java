package com.chengzi.apiunion.procurement.admin.web.controllers.order;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.order.service.OrderService;
import com.chengzi.apiunion.procurement.admin.web.formbean.order.RefuseOrderForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 驳回订单
 *
 * @author 月汐
 * @date 2019/1/14 15:21
 */
public class RefuseOrderController extends AbstractApiController<RefuseOrderForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, RefuseOrderForm command) throws Exception {
        OrderService orderService = BeanFactory.getBean(OrderService.class);
        return orderService.rejectOrder(command.getUserId(), command.getOrderNum(), command.getOperator());
    }
}
