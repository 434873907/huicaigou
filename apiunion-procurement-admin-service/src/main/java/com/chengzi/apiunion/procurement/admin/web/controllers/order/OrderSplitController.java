package com.chengzi.apiunion.procurement.admin.web.controllers.order;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.order.service.OrderService;
import com.chengzi.apiunion.procurement.admin.web.formbean.order.OrderSplitForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 订单拆包
 *
 * @author 月汐
 * @date 2018/12/10 11:34
 */
public class OrderSplitController extends AbstractApiController<OrderSplitForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, OrderSplitForm command) throws Exception {
        OrderService orderService = BeanFactory.getBean(OrderService.class);
        return orderService.updateOrderForSplit(command.getOrderUserId(), command.getOrderNum(), command.getId(), command.getPkgNo(), command.getNum(), RequestContext.getUserName(),true);
    }

}
