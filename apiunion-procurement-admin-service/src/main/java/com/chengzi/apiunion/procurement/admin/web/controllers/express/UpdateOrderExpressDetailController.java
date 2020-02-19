package com.chengzi.apiunion.procurement.admin.web.controllers.express;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.express.pojo.OrderExpressDO;
import com.chengzi.apiunion.express.service.OrderExpressService;
import com.chengzi.apiunion.procurement.admin.web.formbean.express.UpdateOrderExpressDetailForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 更新订单物流轨迹
 *
 * @author 月汐
 * @date 2018/10/30 14:26
 */
public class UpdateOrderExpressDetailController extends AbstractApiController<UpdateOrderExpressDetailForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, UpdateOrderExpressDetailForm command) throws Exception {
        OrderExpressService service = BeanFactory.getBean(OrderExpressService.class);
        OrderExpressDO orderExpressDO = new OrderExpressDO();
        orderExpressDO.setId(command.getId());
        orderExpressDO.setUserId(command.getUserId());
        orderExpressDO.setDetail(command.getDetail());
        service.updateDetail(orderExpressDO);
        return Result.buildEmptySuccess();
    }

}
