package com.chengzi.apiunion.procurement.admin.web.controllers.supplier.order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.supplier.order.UpdateLockStatusForm;
import com.chengzi.apiunion.supplier.common.order.service.SupplierOrderExpressService;
import com.chengzi.common.data.validate.Result;

/**
 * @author 随风
 * @create 2020-01-16 10:23
 **/
public class UpdateLockStatusController extends AbstractApiController<UpdateLockStatusForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, UpdateLockStatusForm command) throws Exception {
        SupplierOrderExpressService expressService = BeanFactory.getBean(SupplierOrderExpressService.class);
        return expressService.updateLockStatusByIds(command.getIds(), command.getOrderNum(), command.getLock());
    }
}
