package com.chengzi.apiunion.procurement.admin.web.controllers.order;

import com.alibaba.fastjson.JSON;
import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.event.EventBusFactory;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.order.constant.OrderRefundConstants;
import com.chengzi.apiunion.order.enums.OrderOperateRecordEnum;
import com.chengzi.apiunion.order.event.pojo.OrderOperateEvent;
import com.chengzi.apiunion.order.event.pojo.OrderRefundUpdateEvent;
import com.chengzi.apiunion.order.pojo.OrderItemDO;
import com.chengzi.apiunion.order.pojo.OrderRefundItemDO;
import com.chengzi.apiunion.order.service.OrderRefundService;
import com.chengzi.apiunion.order.service.OrderService;
import com.chengzi.apiunion.order.util.OrderUtil;
import com.chengzi.apiunion.procurement.admin.partneruser.pojo.PartnerUserDO;
import com.chengzi.apiunion.procurement.admin.partneruser.service.PartnerUserService;
import com.chengzi.apiunion.procurement.admin.web.formbean.order.OrderRefundForm;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.chengzi.apiunion.order.enums.OrderRefundStatusEnum.REFUND_APPLY;

/**
 * 订单退款
 *
 * @author 月汐
 * @date 2018/12/25 11:44
 */
public class OrderRefundController extends AbstractApiController<OrderRefundForm> {

    @Autowired
    private PartnerUserService partnerUserService;
    private static final Logger logger = LoggerFactory.getLogger(OrderRefundController.class);
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, OrderRefundForm command) throws Exception {
        OrderRefundService orderRefundService = BeanFactory.getBean(OrderRefundService.class);
        OrderService orderService = BeanFactory.getBean(OrderService.class);
        long userId = command.getUserId();
        String orderNum = command.getOrderNum();
        List<OrderItemDO> orderItemDOList = orderService.queryByOrderNum(userId, command.getOrderNum());
        List<OrderRefundItemDO> recordDOS = new ArrayList<>();
        List<OrderRefundItemDO> extraRefundDOS = new ArrayList<>();
        List<OrderRefundForm.RefundOrderItem> refundOrderItems = OrderRefundForm.parse(command.getRefundOrderItems());
        Map<Long, OrderItemDO> orderItemDOMap = CollectionUtil.toMap(orderItemDOList, "id");
//        Map<Long, BigDecimal> itemActualPriceMap = OrderUtil.getItemActualPrice(userId, command.getOrderNum());
        //使用新的计算单价逻辑 TODO 致远
        Map<Long, BigDecimal> itemActualPriceMap = OrderUtil.getItemDealUnitPrice(orderItemDOList);
        logger.error("item actual price map:" + JSON.toJSONString(itemActualPriceMap));
        for (OrderRefundForm.RefundOrderItem refundOrderItem : refundOrderItems) {
            if (refundOrderItem.getOrderItemId() > 0 && refundOrderItem.getRefundReason() < 0) {
                return Result.buildIllegalArgumentResult("请选择退款原因");
            }

            OrderRefundItemDO orderRefundItemDO = new OrderRefundItemDO();
            orderRefundItemDO.setOrderItemId(refundOrderItem.getOrderItemId());
            orderRefundItemDO.setUserId(userId);
//            orderRefundItemDO.setRemark(refundOrderItem.getRemark());
            orderRefundItemDO.setRefundReasonType(refundOrderItem.getRefundReason());
            orderRefundItemDO.setRefundReason(refundOrderItem.getRemark());
            orderRefundItemDO.setRefundNum(refundOrderItem.getRefundNum());
            orderRefundItemDO.setRefundAmount(new BigDecimal(Double.toString(refundOrderItem.getRefundAmount())));
            orderRefundItemDO.setOrderNum(command.getOrderNum());

            if (orderRefundItemDO.getOrderItemId() == 0) {
                orderRefundItemDO.setReceiveUserName(refundOrderItem.getReceiveUserName());
                orderRefundItemDO.setReceivePlatform(refundOrderItem.getReceivePlatform());
                orderRefundItemDO.setReceiveAccount(refundOrderItem.getReceiveAccount());
                extraRefundDOS.add(orderRefundItemDO);
            } else {
                BigDecimal actualPrice = itemActualPriceMap.get(orderItemDOMap.get(refundOrderItem.getOrderItemId()).getSkuId());
                orderRefundItemDO.setOrderStatus(orderItemDOMap.get(refundOrderItem.getOrderItemId()).getStatus());
                if (actualPrice.doubleValue() * refundOrderItem.getRefundNum() < refundOrderItem.getRefundAmount()) {
                    return Result.buildIllegalArgumentResult("退款金额不能超过商品金额");
                }
                recordDOS.add(orderRefundItemDO);
            }
        }

        Result refund = orderRefundService.refund(userId, recordDOS, extraRefundDOS, REFUND_APPLY.getCode(), RequestContext.getUserName(), OrderRefundConstants.REFUND_FROM_ORDER);
        if (refund.isSuccess()) {
            if ((Long) refund.getData() != 0) {
                EventBusFactory.getAsyncEventBus().post(new OrderRefundUpdateEvent(userId, (Long) refund.getData()));
                buildOrderOperateRefundApplyEvent(orderNum, userId, (Long) refund.getData());
            }

            return Result.buildSuccessResult("退款提交成功");
        } else {
            return refund;
        }
    }
    //发送订单操作记录 event
    private void buildOrderOperateRefundApplyEvent(String orderNum, Long userId, long orderRefundId) {

        logger.error("orderRefund orderOperate,orderNum:" + orderNum);
        OrderOperateEvent event = new OrderOperateEvent(orderNum, userId, OrderOperateRecordEnum.REFUND_HUMAN_APPLY);
        OrderOperateEvent.OperateParam operateParam = new OrderOperateEvent.OperateParam();
        operateParam.setOrderRefundId(orderRefundId);
        long operatorId = RequestContext.getUserId();
        PartnerUserDO partnerUserDO = partnerUserService.getUserById(operatorId);
        operateParam.setOperator(partnerUserDO.getNickName());
        event.setOperateParam(operateParam);
        EventBusFactory.getAsyncEventBus().post(event);
    }
}
