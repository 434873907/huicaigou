package com.chengzi.apiunion.procurement.admin.web.controllers.express;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.express.pojo.OrderExpressDO;
import com.chengzi.apiunion.express.service.OrderExpressService;
import com.chengzi.apiunion.procurement.admin.web.formbean.express.UpdateOrderExpressAttrForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 更新订单物流补充信息
 *
 * @author 月汐
 * @date 2018/10/30 14:36
 */
public class UpdateOrderExpressAttrController extends AbstractApiController<UpdateOrderExpressAttrForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, UpdateOrderExpressAttrForm command) throws Exception {
        OrderExpressService service = BeanFactory.getBean(OrderExpressService.class);
        OrderExpressDO orderExpressDO = new OrderExpressDO();
        orderExpressDO.setId(command.getId());
        orderExpressDO.setUserId(command.getUserId());
        orderExpressDO.setAttr(command.getAttr());
        service.updateAttr(orderExpressDO);
        return Result.buildEmptySuccess();
    }

}
