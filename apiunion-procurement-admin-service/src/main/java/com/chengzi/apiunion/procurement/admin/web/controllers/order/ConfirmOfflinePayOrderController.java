package com.chengzi.apiunion.procurement.admin.web.controllers.order;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.order.enums.OrderStatusEnum;
import com.chengzi.apiunion.order.pojo.OrderDO;
import com.chengzi.apiunion.order.service.OrderService;
import com.chengzi.apiunion.procurement.admin.web.formbean.order.ConfirmOfflinePayOrderForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 确认线下支付
 *
 * @author 月汐
 * @date 2019/1/30 20:29
 */
public class ConfirmOfflinePayOrderController extends AbstractApiController<ConfirmOfflinePayOrderForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, ConfirmOfflinePayOrderForm command) throws Exception {
        OrderService orderService = BeanFactory.getBean(OrderService.class);

        OrderDO orderDO = orderService.getOrderByOrderNum(command.getUserId(), command.getOrderNum());
        orderDO.setStatus(OrderStatusEnum.ORDER_STATUS_WAIT_SEND.getCode());
        orderService.updateOrderForPay(orderDO);

        return Result.buildSuccessResult("确认成功");
    }
}
