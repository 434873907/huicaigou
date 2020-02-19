package com.chengzi.apiunion.procurement.admin.web.controllers.supplier;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.supplier.ResetSupplierPasswordForm;
import com.chengzi.apiunion.supplier.service.SupplierService;
import com.chengzi.common.data.validate.Result;

/**
 * 重置供应商登录密码
 *
 * @author 随风
 * @create 2020-01-08 14:07
 **/
public class ResetSupplierPasswordController extends AbstractApiController<ResetSupplierPasswordForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, ResetSupplierPasswordForm command) throws Exception {
        SupplierService supplierService = BeanFactory.getBean(SupplierService.class);

        return supplierService.resetPassword(command.getSupplierId(), "123456");
    }
}
