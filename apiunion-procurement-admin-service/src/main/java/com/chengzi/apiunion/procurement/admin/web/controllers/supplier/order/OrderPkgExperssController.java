package com.chengzi.apiunion.procurement.admin.web.controllers.supplier.order;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.express.pojo.OrderExpressDO;
import com.chengzi.apiunion.express.service.OrderExpressService;
import com.chengzi.apiunion.procurement.admin.web.formbean.supplier.order.OrderPkgExperssForm;
import com.chengzi.apiunion.supplier.common.order.pojo.SupplierOrderDO;
import com.chengzi.apiunion.supplier.common.order.service.SupplierOrderExpressService;
import com.chengzi.apiunion.supplier.common.order.service.SupplierOrderService;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class OrderPkgExperssController extends AbstractApiController<OrderPkgExperssForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, OrderPkgExperssForm command) throws Exception {
        SupplierOrderService supplierOrderService = BeanFactory.getBean(SupplierOrderService.class);
        SupplierOrderExpressService supplierOrderExpressService = BeanFactory.getBean(SupplierOrderExpressService.class);

        OrderExpressService orderExpressService = BeanFactory.getBean(OrderExpressService.class);

//        SupplierOrderDO supplierOrderDO = supplierOrderService.getOrderById(command.getId());
//        if(supplierOrderDO == null) {
//            return Result.buildOpFailedResult("订单不存在");
//        }
//
//        String userOrderNum = supplierOrderDO.getUserOrderNum();
//
//        OrderExpressDO orderExpressDO = new OrderExpressDO();
////        orderExpressDO.setUserId(command.getUserId());
//        orderExpressDO.setPkgNo(command.getPkgNo());
////        List<OrderExpressDO> expressList = service.getByPkgNo(orderExpressDO);




        return null;
    }
}
