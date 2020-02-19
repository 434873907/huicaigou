package com.chengzi.apiunion.procurement.admin.web.controllers.supplier.announcement;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.supplier.common.announcement.service.SupplierAnnouncementService;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.IdForm;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 月汐
 * @date 2020/01/17 15:31
 */
public class DeleteAnnouncementController extends AbstractApiController<IdForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, IdForm command) throws Exception {
        SupplierAnnouncementService supplierAnnouncementService = BeanFactory.getBean(SupplierAnnouncementService.class);

        Result<Void> result = supplierAnnouncementService.delete(command.getId());

        if (result.isSuccess()) {
            return Result.buildSuccessResult("删除成功");
        } else {
            return result;
        }
    }
}
