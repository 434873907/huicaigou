package com.chengzi.apiunion.procurement.admin.web.controllers.supplier.order;

import com.alibaba.fastjson.JSON;
import com.chengzi.apiunion.common.data.rocketmq.RocketMQProducer;
import com.chengzi.apiunion.common.data.rocketmq.pojo.RocketMQRouteMsg;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.supplier.order.OrderPkgExperssForm;
import com.chengzi.apiunion.supplier.common.data.beans.PkgExpressDetailBO;
import com.chengzi.apiunion.supplier.common.order.enums.SupplierOrderExpressStatusEnum;
import com.chengzi.apiunion.supplier.common.order.pojo.SupplierOrderDO;
import com.chengzi.apiunion.supplier.common.order.pojo.SupplierOrderExpressDO;
import com.chengzi.apiunion.supplier.common.order.service.SupplierOrderExpressService;
import com.chengzi.apiunion.supplier.common.order.service.SupplierOrderService;
import com.chengzi.apiunion.thirdparty.order.event.OrderExpressSyncEvent;
import com.chengzi.common.data.validate.Result;
import com.chengzi.yuncang.common.util.StringUtil;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 同步订单的包裹物流信息
 */
public class SyncOrderPkgExpressController extends AbstractApiController<OrderPkgExperssForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, OrderPkgExperssForm command) throws Exception {
        SupplierOrderExpressService orderExpressService = BeanFactory.getBean(SupplierOrderExpressService.class);
        SupplierOrderService supplierOrderService = BeanFactory.getBean(SupplierOrderService.class);
        try {
            long orderId = command.getId();
            if (orderId == 0) {
                return Result.buildOpFailedResult("订单ID不能为空");
            }

            SupplierOrderDO supplierOrderDO = supplierOrderService.getOrderById(orderId);
            if (supplierOrderDO == null) {
                return Result.buildOpFailedResult("订单不存在");
            }
            String userOrderNum = supplierOrderDO.getUserOrderNum();
            String orderNum = command.getOrderNum();
            String pkgNo = command.getPkgNo();

            SupplierOrderExpressDO supplierOrderExpressDO = orderExpressService.selectByOrderPkgNo(orderNum, pkgNo);
            if (supplierOrderExpressDO == null) {
                return Result.buildOpFailedResult("物流信息不存在");
            }
            if (supplierOrderExpressDO.getExpressStatus() == SupplierOrderExpressStatusEnum.NO_SHIPPED.getCode()) {
                return Result.buildOpFailedResult("包裹未发货");
            }

            String expressNum = supplierOrderExpressDO.getExpressNum();
            long expressCompanyId = supplierOrderExpressDO.getExpressCompanyId();

            OrderExpressSyncEvent event = new OrderExpressSyncEvent();
            event.setPkgNo(userOrderNum);
            event.setExpressNum(expressNum);
            event.setExpressCompanyId(expressCompanyId);

            pushThirdOrderSync(event);

            return Result.buildSuccessMsg("已经同步");
        } catch (Exception e) {
            LOGGER.error("error", e);
            return Result.buildOpFailedResult("服务器错误");
        }
    }

    private void pushThirdOrderSync(OrderExpressSyncEvent event) {
        try {
            RocketMQProducer thirdOrderSyncMQProducer = BeanFactory.getBean("orderExpressSyncMQProducer", RocketMQProducer.class);
            if (thirdOrderSyncMQProducer == null) {
                LOGGER.error("PushThirdOrderSync failed for  no orderExpressSyncMQProducer found");
                return;
            }
            List<Message> mqMsgs = new ArrayList<>();
            mqMsgs.add(new Message(null, null, RocketMQRouteMsg.KRYO.serialize(event)));

            SendResult sendResult = thirdOrderSyncMQProducer.send(mqMsgs);
            LOGGER.error(JSON.toJSONString(sendResult));
        } catch (Exception e) {
            LOGGER.error(StringUtil.buildStatLog("pushThirdOrderSync send push order event failed"), e);
        }
    }
}
