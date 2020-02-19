package com.chengzi.apiunion.procurement.admin.web.controllers.express;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.express.pojo.OrderExpressDO;
import com.chengzi.apiunion.express.service.OrderExpressService;
import com.chengzi.apiunion.procurement.admin.web.formbean.express.UpdateOrderExpressDeliveredForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 更新订单物流投递状态
 *
 * @author 月汐
 * @date 2018/10/30 15:09
 */
public class UpdateOrderExpressDeliveredController extends AbstractApiController<UpdateOrderExpressDeliveredForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, UpdateOrderExpressDeliveredForm command) throws Exception {
        OrderExpressService service = BeanFactory.getBean(OrderExpressService.class);
        OrderExpressDO orderExpressDO = new OrderExpressDO();
        orderExpressDO.setId(command.getId());
        orderExpressDO.setUserId(command.getUserId());
        orderExpressDO.setDelivered(command.getDelivered());
        service.updateDelivered(orderExpressDO);
        return Result.buildEmptySuccess();
    }

}
