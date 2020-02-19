package com.chengzi.apiunion.procurement.admin.web.controllers.order;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.event.EventBusFactory;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.order.enums.OrderOperateRecordEnum;
import com.chengzi.apiunion.order.event.pojo.OrderOperateEvent;
import com.chengzi.apiunion.order.pojo.OrderRefundItemDO;
import com.chengzi.apiunion.order.service.OrderRefundItemService;
import com.chengzi.apiunion.order.service.OrderRefundService;
import com.chengzi.apiunion.procurement.admin.partneruser.pojo.PartnerUserDO;
import com.chengzi.apiunion.procurement.admin.partneruser.service.PartnerUserService;
import com.chengzi.apiunion.procurement.admin.web.formbean.order.UpdateRefundAmountForm;
import com.chengzi.common.data.validate.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

/**
 * <p>
 * </p>
 * User: 摩天
 * Date: 2019-03-18 23:46
 */
public class UpdateRefundAmountController extends AbstractApiController<UpdateRefundAmountForm> {

    @Autowired
    private OrderRefundItemService orderRefundItemService;
    @Autowired
    private PartnerUserService partnerUserService;
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, UpdateRefundAmountForm command) throws Exception {
        OrderRefundService orderRefundService = BeanFactory.getBean(OrderRefundService.class);

        OrderRefundItemDO orderRefundItemDO = orderRefundItemService.getByRefundItemId(command.getUserId(), command.getId());
        Result<String> updateResult = orderRefundService.updateRefundAmount(command.getUserId(), command.getId(), command.getAmount());
        if(updateResult.isSuccess()) {
            if(orderRefundItemDO != null) {
                String orderNum = orderRefundItemDO.getOrderNum();
                buildOrderOperateRefundModifyAmountEvent(orderNum, command.getUserId(), orderRefundItemDO.getOrderRefundId(),
                        command.getId(),
                        orderRefundItemDO.getRefundAmount(),
                        new BigDecimal(command.getAmount()),
                        OrderOperateRecordEnum.REFUND_AMOUNT_MODIFY);
            }

        }
        return updateResult;
    }

    /**
     * 退款确认，退款取消事件
     */
    private void buildOrderOperateRefundModifyAmountEvent(String orderNum, Long userId, Long orderRefundId,Long refundItemId,
                                                          BigDecimal oriRefundAmount,
                                                          BigDecimal modifyRefundAmount,
                                                          OrderOperateRecordEnum operateRecordEnum) {
        OrderOperateEvent event = new OrderOperateEvent(orderNum, userId, operateRecordEnum);
        OrderOperateEvent.OperateParam operateParam = new OrderOperateEvent.OperateParam();

        operateParam.setOrderRefundId(orderRefundId);
        operateParam.setRefundItemId(refundItemId);
        operateParam.setOriRefundAmount(oriRefundAmount);
        operateParam.setModifyRefundAmount(modifyRefundAmount);
        long operatorId = RequestContext.getUserId();
        PartnerUserDO partnerUserDO = partnerUserService.getUserById(operatorId);
        operateParam.setOperator(partnerUserDO.getNickName());
        event.setOperateParam(operateParam);
        EventBusFactory.getAsyncEventBus().post(event);
    }
}
