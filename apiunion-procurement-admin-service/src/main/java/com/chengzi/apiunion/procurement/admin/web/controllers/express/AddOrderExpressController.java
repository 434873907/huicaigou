package com.chengzi.apiunion.procurement.admin.web.controllers.express;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.event.EventBusFactory;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.express.pojo.OrderExpressDO;
import com.chengzi.apiunion.express.service.OrderExpressService;
import com.chengzi.apiunion.expresscompany.service.ExpressCompanyService;
import com.chengzi.apiunion.order.enums.OrderOperateRecordEnum;
import com.chengzi.apiunion.order.event.pojo.OrderOperateEvent;
import com.chengzi.apiunion.procurement.admin.partneruser.pojo.PartnerUserDO;
import com.chengzi.apiunion.procurement.admin.partneruser.service.PartnerUserService;
import com.chengzi.apiunion.procurement.admin.web.formbean.express.AddOrderExpressForm;
import com.chengzi.common.data.validate.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 新增订单物流
 *
 * @author 月汐
 * @date 2018/10/30 11:57
 */
public class AddOrderExpressController extends AbstractApiController<AddOrderExpressForm> {

    @Autowired
    private PartnerUserService partnerUserService;
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, AddOrderExpressForm command) throws Exception {
        OrderExpressService service = BeanFactory.getBean(OrderExpressService.class);
        ExpressCompanyService expressCompanyService = BeanFactory.getBean(ExpressCompanyService.class);
        OrderExpressDO orderExpressDO = new OrderExpressDO();
        orderExpressDO.setUserId(command.getUserId());
        orderExpressDO.setOrderNum(command.getOrderNum());
        orderExpressDO.setPkgNo(command.getPkgNo());
        orderExpressDO.setType(command.getType());
        orderExpressDO.setParentId(command.getParentId());
        orderExpressDO.setExpressCompany(command.getExpressCompany());
        orderExpressDO.setRealExpressCompanyName(expressCompanyService.getById(command.getExpressCompany()).getCompanyName());
        orderExpressDO.setExpressNum(command.getExpressNum());
        orderExpressDO.setTrackingNum(command.getTrackingNum());
        orderExpressDO.setDelivered(0);
        orderExpressDO.setRealExpressNum(command.getExpressNum());
        Result<Void> addExpressResult = service.add(orderExpressDO);
        if(addExpressResult.isSuccess()) {
            orderOperateAddExpress(orderExpressDO.getOrderNum(), orderExpressDO.getUserId(), orderExpressDO);
        }
        return addExpressResult;
    }
    //管理端手动设置物流，pkg 已经发货
    private void orderOperateAddExpress(String orderNum, Long userId, OrderExpressDO orderExpressDO) {
        OrderOperateEvent event = new OrderOperateEvent(orderNum, userId, OrderOperateRecordEnum.PKG_SEND_EXPRESS_ADMIN);

        OrderOperateEvent.OperateParam operateParam = new OrderOperateEvent.OperateParam();
        operateParam.setExpressCompanyName(orderExpressDO.getRealExpressCompanyName());
        operateParam.setPkgNo(orderExpressDO.getPkgNo());
        operateParam.setExpressNum( orderExpressDO.getExpressNum());

        long operatorId = RequestContext.getUserId();
        PartnerUserDO partnerUserDO = partnerUserService.getUserById(operatorId);
        operateParam.setOperator(partnerUserDO.getNickName());
        event.setOperateParam(operateParam);

        EventBusFactory.getAsyncEventBus().post(event);
    }
}
