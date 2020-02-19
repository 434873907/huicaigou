package com.chengzi.apiunion.procurement.admin.web.controllers.order;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.event.EventBusFactory;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.order.enums.OrderAddressVerifyStatusEnum;
import com.chengzi.apiunion.order.enums.OrderOperateRecordEnum;
import com.chengzi.apiunion.order.event.pojo.OrderOperateEvent;
import com.chengzi.apiunion.order.event.pojo.OrderUpdateAddressEvent;
import com.chengzi.apiunion.order.pojo.OrderDO;
import com.chengzi.apiunion.order.pojo.OrderDeliveryInfo;
import com.chengzi.apiunion.order.service.OrderService;
import com.chengzi.apiunion.procurement.admin.partneruser.pojo.PartnerUserDO;
import com.chengzi.apiunion.procurement.admin.partneruser.service.PartnerUserService;
import com.chengzi.apiunion.procurement.admin.web.formbean.order.UpdateOrderAddressForm;
import com.chengzi.common.data.validate.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 修改订单收货地址
 *
 * @author 行者
 */
public class UpdateOrderAddressController extends AbstractApiController<UpdateOrderAddressForm> {
    @Autowired
    private PartnerUserService partnerUserService;
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, UpdateOrderAddressForm command) throws Exception {
        long userId = command.getUserId();
        String orderNum = command.getOrderNum();
        OrderService orderService = BeanFactory.getBean(OrderService.class);

        OrderDO orderDO = orderService.getOrderByOrderNum(userId, orderNum);
        if (orderDO.getOriginalDeliveryInfo() != null &&
                orderDO.getAddressVerifyStatus() != OrderAddressVerifyStatusEnum.VERIFY_FAILED.getCode()) {
            return Result.buildOpFailedResult("该订单不可再次修改");
        }

        OrderDeliveryInfo deliveryInfo = new OrderDeliveryInfo();
        deliveryInfo.setCountry(command.getCountry());
        deliveryInfo.setProvince(command.getProvince());
        deliveryInfo.setCity(command.getCity());
        deliveryInfo.setDistrict(command.getDistrict());
        deliveryInfo.setAddress1(command.getAddress1());
        deliveryInfo.setName(command.getName());
        deliveryInfo.setPhone(command.getPhoneNum());
        deliveryInfo.setIdcardNumber(command.getIdCardNum());

        if (StringUtils.isBlank(orderDO.getOriginalDeliveryInfo())) {
            orderDO.setOriginalDeliveryInfo(orderDO.getOrderDeliveryInfo());
        }
        orderDO.setOrderDeliveryInfo(deliveryInfo.toJsonString());
        orderDO.setAddressVerifyStatus(OrderAddressVerifyStatusEnum.WAIT_VERIFY.getCode());

        Result<?> result = orderService.updateOrderDeliveryInfo(orderDO);
        if(result.isSuccess()) {
            orderOperateModifyOrderAddressEvent(command.getOrderNum(), command.getUserId());
        }
        return result;
    }
    private void orderOperateModifyOrderAddressEvent(String orderNum, Long userId) {
        OrderOperateEvent event = new OrderOperateEvent(orderNum, userId, OrderOperateRecordEnum.ADDRESS_MODIFY);

        OrderOperateEvent.OperateParam operateParam = new OrderOperateEvent.OperateParam();
        long operatorId = RequestContext.getUserId();
        PartnerUserDO partnerUserDO = partnerUserService.getUserById(operatorId);
        operateParam.setOperator(partnerUserDO.getNickName());

        event.setOperateParam(operateParam);

        EventBusFactory.getAsyncEventBus().post(event);
    }
}
