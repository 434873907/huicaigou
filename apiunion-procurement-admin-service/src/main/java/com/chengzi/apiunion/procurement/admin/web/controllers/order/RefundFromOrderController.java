package com.chengzi.apiunion.procurement.admin.web.controllers.order;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.order.pojo.OrderCouponDO;
import com.chengzi.apiunion.order.pojo.OrderItemDO;
import com.chengzi.apiunion.order.pojo.OrderRefundItemDO;
import com.chengzi.apiunion.order.service.OrderCouponService;
import com.chengzi.apiunion.order.service.OrderRefundService;
import com.chengzi.apiunion.order.service.OrderService;
import com.chengzi.apiunion.procurement.admin.web.formbean.order.RefundFromOrderForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.order.RefundFromOrderBO;
import com.chengzi.common.data.validate.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 订单及退款列表
 *
 * @author 月汐
 * @date 2018/12/28 16:32
 */
public class RefundFromOrderController extends AbstractApiController<RefundFromOrderForm> {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderCouponService orderCouponService;
    @Autowired
    private OrderRefundService orderRefundService;

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, RefundFromOrderForm command) throws Exception {
        List<OrderItemDO> orderItemDOList = orderService.queryByOrderNum(command.getUserId(), command.getOrderNum());
        List<OrderRefundItemDO> orderRefundItemDOS = orderRefundService.queryRefundItemsByOrderNum(command.getUserId(), command.getOrderNum());
        List<OrderCouponDO> orderCouponDOS = orderCouponService.queryByOrderNum(command.getUserId(), command.getOrderNum());
        return Result.buildSuccessResult(RefundFromOrderBO.convert(orderItemDOList, orderRefundItemDOS, orderCouponDOS));
    }
}
