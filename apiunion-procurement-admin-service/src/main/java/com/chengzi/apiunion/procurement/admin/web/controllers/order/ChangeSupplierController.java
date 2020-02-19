package com.chengzi.apiunion.procurement.admin.web.controllers.order;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.order.service.OrderService;
import com.chengzi.apiunion.procurement.admin.web.formbean.order.ChangeSupplierForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * <p>
 *     更换供应商
 * </p>
 * User: 摩天
 * Date: 2018-11-15 11:29
 */
public class ChangeSupplierController extends AbstractApiController<ChangeSupplierForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, ChangeSupplierForm command)
            throws Exception {
        long userId = command.getUserId();
        long orderItemId = command.getOrderItemId();
        long supplierId = command.getSupplierId();
        OrderService orderService = BeanFactory.getBean(OrderService.class);
        return orderService.changeSupplier(userId, orderItemId, supplierId, command.getNeedSplit());
    }
}
