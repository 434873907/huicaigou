package com.chengzi.apiunion.procurement.admin.web.controllers.express;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.express.pojo.OrderExpressDO;
import com.chengzi.apiunion.express.service.OrderExpressService;
import com.chengzi.apiunion.procurement.admin.web.formbean.express.UpdateOrderExpressForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 月汐
 * @date 2019/4/1 16:12
 */
public class UpdateOrderExpressController extends AbstractApiController<UpdateOrderExpressForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, UpdateOrderExpressForm command) throws Exception {
        OrderExpressService service = BeanFactory.getBean(OrderExpressService.class);
        OrderExpressDO orderExpressDO = new OrderExpressDO();
        orderExpressDO.setId(command.getId());
        orderExpressDO.setUserId(command.getUserId());
        orderExpressDO.setExpressCompany(command.getExpressCompany());
        orderExpressDO.setExpressNum(command.getExpressNum());
        orderExpressDO.setTrackingNum(command.getTrackingNum());
        orderExpressDO.setDelivered(0);
        orderExpressDO.setDetail("");
        return service.update(orderExpressDO);
    }
}
