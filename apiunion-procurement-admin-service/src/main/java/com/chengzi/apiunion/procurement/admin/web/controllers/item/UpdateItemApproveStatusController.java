/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.controllers.item;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.service.ItemSkuSupplierApproveService;
import com.chengzi.apiunion.procurement.admin.web.formbean.item.UpdateItemApproveStatusForm;
import com.chengzi.common.data.validate.Result;

/**
 * 更新商品审核状态
 * 
 * @author Kolor
 */
public class UpdateItemApproveStatusController extends AbstractApiController<UpdateItemApproveStatusForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, UpdateItemApproveStatusForm command) throws Exception {
        ItemSkuSupplierApproveService itemSkuSupplierApproveService = BeanFactory.getBean(ItemSkuSupplierApproveService.class);
        int ret = itemSkuSupplierApproveService.updateApprovedStatus(command.getApprovedIds(), command.getRefusedIds(), RequestContext.getUserId());
        if (ret > 0) {
            return Result.buildSuccessMsg("更新审核状态成功");
        }
        return Result.buildOpFailedResult("更新审核状态失败");
    }

}
