package com.chengzi.apiunion.procurement.admin.web.controllers.supplier.order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.supplier.order.GetSupplierOrderDetailForm;
import com.chengzi.apiunion.supplier.common.order.helper.SupplierOrderHelper;
import com.chengzi.common.data.validate.Result;

/**
 * @author 随风
 * @create 2020-01-10 16:29
 **/
public class GetSupplierOrderDetailController extends AbstractApiController<GetSupplierOrderDetailForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, GetSupplierOrderDetailForm command) throws Exception {

        return Result.buildSuccessResult(SupplierOrderHelper.getSupplierOrderDetail(command.getOrderNum()));
    }
}
