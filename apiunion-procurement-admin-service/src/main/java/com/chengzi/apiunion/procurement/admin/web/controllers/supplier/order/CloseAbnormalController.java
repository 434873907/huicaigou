package com.chengzi.apiunion.procurement.admin.web.controllers.supplier.order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.supplier.order.CloseAbnormalForm;
import com.chengzi.apiunion.supplier.common.order.service.SupplierOrderExpressService;
import com.chengzi.common.data.validate.Result;

/**
 * @author 随风
 * @create 2020-01-16 20:11
 **/
public class CloseAbnormalController extends AbstractApiController<CloseAbnormalForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, CloseAbnormalForm command) throws Exception {
        SupplierOrderExpressService expressService = BeanFactory.getBean(SupplierOrderExpressService.class);
        Result<Void> voidResult = expressService.closeAbnormal(command.getId(), command.getOrderNum());
        if (voidResult.isSuccess()) {
            return Result.buildSuccessResult("关闭成功");
        }
        return voidResult;
    }
}
