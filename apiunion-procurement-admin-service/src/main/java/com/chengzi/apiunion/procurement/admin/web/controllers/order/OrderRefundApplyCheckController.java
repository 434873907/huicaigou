package com.chengzi.apiunion.procurement.admin.web.controllers.order;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.event.EventBusFactory;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.order.event.pojo.OrderRefundUpdateEvent;
import com.chengzi.apiunion.order.service.OrderRefundService;
import com.chengzi.apiunion.order.service.OrderService;
import com.chengzi.apiunion.procurement.admin.web.formbean.order.OrderRefundApplyCheckForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *     退款审核
 * </p>
 * User: 摩天
 * Date: 2019-01-31 10:42
 */
public class OrderRefundApplyCheckController extends AbstractApiController<OrderRefundApplyCheckForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, OrderRefundApplyCheckForm command) throws Exception {
        OrderService orderService = BeanFactory.getBean(OrderService.class);
        Result<String> result = orderService.refundApplyCheck(command.getUserId(), command.getOrderNum(), RequestContext.getUserName(),
                command.getIsPass() == 1 ? true : false);
        if (result.isSuccess()) {
            return Result.buildSuccessResult("操作成功");
        }
        return result;
    }
}
