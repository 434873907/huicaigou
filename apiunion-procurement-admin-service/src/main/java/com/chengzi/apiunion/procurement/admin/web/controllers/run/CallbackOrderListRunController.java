package com.chengzi.apiunion.procurement.admin.web.controllers.run;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.chengzi.apiunion.procurement.admin.web.controllers.run.form.CallbackOrderListRunForm;
import com.chengzi.common.util.CollectionUtil;
import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.event.EventBusFactory;
import com.chengzi.apiunion.common.web.controllers.AbstractManageController;
import com.chengzi.apiunion.order.event.pojo.OrderStatusUpdateEvent;
import com.chengzi.apiunion.order.event.pojo.TrackUpdateEvent;
import com.chengzi.apiunion.order.pojo.OrderDO;
import com.chengzi.apiunion.order.service.OrderService;
import com.chengzi.common.data.validate.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * 主动推送 回调信息
 * 主要针对 小象等采购商， 推送物流轨迹,订单状态
 */
public class CallbackOrderListRunController extends AbstractManageController<CallbackOrderListRunForm> {

    @Override
    protected Result<?> doInnerBiz(HttpServletRequest request, HttpServletResponse response, CallbackOrderListRunForm command) throws Exception {

        OrderService orderService = BeanFactory.getBean(OrderService.class);

//        String orderNum = command.getOrderNum();
        List<String> orderNumList = command.getOrderNums();


        long userId = command.getUserId();
        long routId = RequestContext.getRouteId();
        LOGGER.error("param:" + command.toJsonString());

        LOGGER.error("routeId:" + routId);

        if(CollectionUtil.isEmpty(orderNumList)) {

            return Result.buildOpFailedResult("订单list为空");
        }

        List<CallbackErrorMessage> errorMessages = new ArrayList<>();

        for (String orderNum : orderNumList) {

            OrderDO orderDO = orderService.getOrderByOrderNum(userId, orderNum);
            LOGGER.error("orderDO:" + orderDO.toJsonString());

            if (orderDO == null) {
                CallbackErrorMessage errorMessage =  CallbackErrorMessage.buildErrorMessage(orderNum, userId, "订单不存在");
                errorMessages.add(errorMessage);
            } else {
                // 代表轨迹发生变化，直接发送回调信息
                EventBusFactory.getSyncEventBus().post(new TrackUpdateEvent(orderNum, userId));
                EventBusFactory.getSyncEventBus().post(new OrderStatusUpdateEvent(orderNum, userId));
                CallbackErrorMessage errorMessage =  CallbackErrorMessage.buildErrorMessage(orderNum, userId, orderDO.getStatus()+ "");
                errorMessages.add(errorMessage);
            }

        }
        return Result.buildSuccessMsg(JSON.toJSONString(errorMessages));
    }
    private static class CallbackErrorMessage {
        private String orderNum;
        private long userId;
        private String desc;

        public static CallbackErrorMessage buildErrorMessage(String orderNum, long userId,String desc) {
            CallbackErrorMessage errorMessage = new CallbackErrorMessage();
            errorMessage.setOrderNum(orderNum);
            errorMessage.setUserId(userId);
            errorMessage.setDesc(desc);
            return errorMessage;
        }

        public String getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(String orderNum) {
            this.orderNum = orderNum;
        }

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}


