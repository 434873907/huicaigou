package com.chengzi.apiunion.procurement.admin.web.controllers.order;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.event.EventBusFactory;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.order.enums.OrderOperateRecordEnum;
import com.chengzi.apiunion.order.event.pojo.OrderOperateEvent;
import com.chengzi.apiunion.order.event.pojo.OrderRefundUpdateEvent;
import com.chengzi.apiunion.order.service.OrderRefundService;
import com.chengzi.apiunion.procurement.admin.partneruser.pojo.PartnerUserDO;
import com.chengzi.apiunion.procurement.admin.partneruser.service.PartnerUserService;
import com.chengzi.apiunion.procurement.admin.web.formbean.order.OrderRefundApplyReviewForm;
import com.chengzi.common.data.validate.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

/**
 * <p>
 *     退款申请审核
 * </p>
 * User: 摩天
 * Date: 2019-01-29 17:54
 */
public class OrderRefundApplyReviewController extends AbstractApiController<OrderRefundApplyReviewForm> {

    @Autowired
    private PartnerUserService partnerUserService;
    Logger logger = LoggerFactory.getLogger(OrderRefundApplyReviewController.class);

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, OrderRefundApplyReviewForm command) throws Exception {

        OrderRefundService orderRefundService = BeanFactory.getBean(OrderRefundService.class);
        long userId = command.getUserId();
        long orderRefundId = command.getOrderRefundId();
        try {
            if (command.getIsPass() == 1){
                Result<String> result = orderRefundService.confirmRefund(userId, orderRefundId, RequestContext.getUserName());
                if (result.isSuccess()) {
                    buildOrderOperateRefundEvent(null, userId, orderRefundId, OrderOperateRecordEnum.REFUND_COMFIRE);
                    return Result.buildSuccessResult("退款成功");
                }
            } else {
                Result<String> result = orderRefundService.cancelRefund(userId, orderRefundId, RequestContext.getUserName());
                if (result.isSuccess()) {
                    buildOrderOperateRefundEvent(null, userId, orderRefundId, OrderOperateRecordEnum.REFUND_CANCEL);
                    return result;
                }
            }
        } catch (Exception e) {
            logger.error("ORDER_REFUND_APPLY_REVIEW|refund fail", e);
            return Result.buildOpFailedResult("退款失败");
        }
        return Result.buildOpFailedResult("退款失败");
    }

    /**
     * 退款确认，退款取消事件
     */
    private void buildOrderOperateRefundEvent(String orderNum, Long userId, Long orderRefundId, OrderOperateRecordEnum operateRecordEnum) {
        OrderOperateEvent event = new OrderOperateEvent(orderNum, userId, operateRecordEnum);
        OrderOperateEvent.OperateParam operateParam = new OrderOperateEvent.OperateParam();
        long operatorId = RequestContext.getUserId();
        PartnerUserDO partnerUserDO = partnerUserService.getUserById(operatorId);
        operateParam.setOperator(partnerUserDO.getNickName());
//        operateParam.setRefundAmount(refundAmount);
        operateParam.setOrderRefundId(orderRefundId);
        event.setOperateParam(operateParam);
        EventBusFactory.getAsyncEventBus().post(event);

    }
}
