package com.chengzi.apiunion.procurement.admin.web.controllers.run;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.event.EventBusFactory;
import com.chengzi.apiunion.common.web.controllers.AbstractManageController;
import com.chengzi.apiunion.express.enums.ExpressStatusEnum;
import com.chengzi.apiunion.order.enums.OrderOperateRecordEnum;
import com.chengzi.apiunion.order.enums.OrderStatusEnum;
import com.chengzi.apiunion.order.event.pojo.OrderOperateEvent;
import com.chengzi.apiunion.order.event.pojo.OrderUpdateEvent;
import com.chengzi.apiunion.order.pojo.OrderDO;
import com.chengzi.apiunion.order.pojo.OrderItemDO;
import com.chengzi.apiunion.order.service.OrderService;
import com.chengzi.apiunion.procurement.admin.web.controllers.run.form.CompleteOrderRunForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CompleteOrderRunController extends AbstractManageController<CompleteOrderRunForm> {

    @Override
    protected Result<?> doInnerBiz(HttpServletRequest request, HttpServletResponse response, CompleteOrderRunForm command) throws Exception {

        OrderService orderService = BeanFactory.getBean(OrderService.class);

        String orderNum = command.getOrderNum();
        long userId = command.getUserId();
        long routId = RequestContext.getRouteId();
        LOGGER.error("param:" + command.toJsonString());

        LOGGER.error("routeId:" + routId);
//        orderService.queryByOrderNum(userId, orderNum);

        OrderDO orderDO = orderService.getOrderByOrderNum(userId, orderNum);
        LOGGER.error("orderDO:" + orderDO.toJsonString());

        if(orderDO == null) {

            return Result.buildOpFailedResult("订单不存在");
        }


//        for (OrderDO orderDO : orderDOList) {
//            if (orderDO.getStatus() == OrderStatusEnum.ORDER_STATUS_WAIT_CONFIRM.getCode()) {
//                List<OrderItemDO> orderItemDOList = orderNumOrderItemsMap.get(orderDO.getOrderNum());
//                boolean complete = true;
//                for (OrderItemDO orderItemDO : orderItemDOList) {
//                    if (orderItemDO.getExpressStatus() != ExpressStatusEnum.SIGNED.getCode()
//                            || orderItemDO.getSignTime() == null || orderItemDO.getSignTime().after(getEndTime())) {
//                        complete = false;
//                    }
//                }
//                if (complete) {
//                    orderDO.setStatus(OrderStatusEnum.ORDER_STATUS_COMPLETE.getCode());
//                    orderMapper.updateStatusByOrderNum(orderDO);
//                    EventBusFactory.getAsyncEventBus().post(new OrderUpdateEvent(orderDO.getOrderNum(), orderDO.getUserId()));
//                    EventBusFactory.getAsyncEventBus().post(new OrderOperateEvent(orderDO.getOrderNum(), orderDO.getUserId(), OrderOperateRecordEnum.ORDER_FINISH));
//                }
//            }
//        }












        return Result.buildSuccessMsg("订单处理完成");
    }
}
