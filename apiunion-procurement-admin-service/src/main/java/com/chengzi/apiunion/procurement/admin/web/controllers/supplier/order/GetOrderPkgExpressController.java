package com.chengzi.apiunion.procurement.admin.web.controllers.supplier.order;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.supplier.order.OrderPkgExperssForm;
import com.chengzi.apiunion.supplier.common.data.beans.PkgExpressDetailBO;
import com.chengzi.apiunion.supplier.common.order.service.SupplierOrderExpressService;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 获取订单的包裹物流
 */
public class GetOrderPkgExpressController  extends AbstractApiController<OrderPkgExperssForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, OrderPkgExperssForm command) throws Exception {
        SupplierOrderExpressService orderExpressService = BeanFactory.getBean(SupplierOrderExpressService.class);
        try {
            Result<PkgExpressDetailBO> result = orderExpressService.getOrderPkgExpressDetail(command.getId(), command.getOrderNum(), command.getPkgNo());
            return result;
        } catch (Exception e) {
            LOGGER.error("error", e);
            return Result.buildOpFailedResult("服务器错误");
        }
    }
}
