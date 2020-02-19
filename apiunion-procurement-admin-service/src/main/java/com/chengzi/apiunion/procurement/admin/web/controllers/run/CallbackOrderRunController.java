package com.chengzi.apiunion.procurement.admin.web.controllers.run;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chengzi.apiunion.common.event.EventBusFactory;
import com.chengzi.apiunion.order.event.pojo.OrderStatusUpdateEvent;
import com.chengzi.apiunion.order.event.pojo.TrackUpdateEvent;
import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.web.controllers.AbstractManageController;
import com.chengzi.apiunion.order.pojo.OrderDO;
import com.chengzi.apiunion.order.service.OrderService;
import com.chengzi.apiunion.procurement.admin.web.controllers.run.form.CompleteOrderRunForm;
import com.chengzi.common.data.validate.Result;

/**
 * 主动推送 回调信息
 * 主要针对 小象等采购商， 推送物流轨迹
 */
public class CallbackOrderRunController extends AbstractManageController<CompleteOrderRunForm> {

    @Override
    protected Result<?> doInnerBiz(HttpServletRequest request, HttpServletResponse response, CompleteOrderRunForm command) throws Exception {

        OrderService orderService = BeanFactory.getBean(OrderService.class);

        String orderNum = command.getOrderNum();
        long userId = command.getUserId();
        long routId = RequestContext.getRouteId();
        LOGGER.error("param:" + command.toJsonString());

        LOGGER.error("routeId:" + routId);
        OrderDO orderDO = orderService.getOrderByOrderNum(userId, orderNum);
        LOGGER.error("orderDO:" + orderDO.toJsonString());

        if(orderDO == null) {

            return Result.buildOpFailedResult("订单不存在");
        }

        // 代表轨迹发生变化，直接发送回调信息
        EventBusFactory.getSyncEventBus().post(new TrackUpdateEvent(orderNum, userId));

        EventBusFactory.getSyncEventBus().post(new OrderStatusUpdateEvent(orderNum, userId));

        return Result.buildSuccessMsg("回调信息处理完成");
    }
}
