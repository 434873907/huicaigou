package com.chengzi.apiunion.procurement.admin.web.controllers.supplier.order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.supplier.common.order.helper.SupplierOrderHelper;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.EmptyForm;

/**
 * @author 随风
 * @create 2020-01-17 17:10
 **/
public class SupplierOrderAbnormalStatusListController extends AbstractApiController<EmptyForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {
        return Result.buildSuccessResult(SupplierOrderHelper.getOrderStatus(true));
    }
}
