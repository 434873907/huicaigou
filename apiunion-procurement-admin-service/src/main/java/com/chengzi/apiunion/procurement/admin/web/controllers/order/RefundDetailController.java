package com.chengzi.apiunion.procurement.admin.web.controllers.order;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.order.pojo.OrderDO;
import com.chengzi.apiunion.order.pojo.OrderRefundDO;
import com.chengzi.apiunion.order.pojo.OrderRefundItemDO;
import com.chengzi.apiunion.order.service.OrderRefundService;
import com.chengzi.apiunion.order.service.OrderService;
import com.chengzi.apiunion.procurement.admin.web.formbean.order.RefundDetailForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.order.RefundDetailBO;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 退款详情
 *
 * @author 月汐
 * @date 2019/1/4 16:24
 */
public class RefundDetailController extends AbstractApiController<RefundDetailForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, RefundDetailForm command) throws Exception {
        OrderRefundService orderRefundService = BeanFactory.getBean(OrderRefundService.class);
        OrderService orderService = BeanFactory.getBean(OrderService.class);

        OrderDO order = orderService.getOrderByOrderNum(command.getUserId(), command.getOrderNum());
        List<OrderRefundItemDO> orderRefundItemDOS = orderRefundService.queryRefundItemsByOrderNum(command.getUserId(), command.getOrderNum());
        List<OrderRefundDO> orderRefundDOS = orderRefundService.queryRefundByOrderNum(command.getUserId(), command.getOrderNum());
        return Result.buildSuccessResult(RefundDetailBO.convert(orderRefundItemDOS, orderRefundDOS, order));
    }
}
