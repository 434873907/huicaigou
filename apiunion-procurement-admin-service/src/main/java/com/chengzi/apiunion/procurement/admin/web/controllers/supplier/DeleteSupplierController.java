package com.chengzi.apiunion.procurement.admin.web.controllers.supplier;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.supplier.DeleteSupplierForm;
import com.chengzi.apiunion.supplier.service.SupplierService;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 删除供应商
 *
 * @author 月汐
 * @date 2018/11/23 19:39
 */
public class DeleteSupplierController extends AbstractApiController<DeleteSupplierForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, DeleteSupplierForm command) throws Exception {
        SupplierService service = BeanFactory.getBean(SupplierService.class);
        return service.deleteSupplier(command.getId());
    }

}
