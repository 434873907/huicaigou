package com.chengzi.apiunion.procurement.admin.web.controllers.manageuser;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.manageuser.service.ManageUserService;
import com.chengzi.apiunion.procurement.admin.web.formbean.manageuser.DeleteManageUserForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 月汐
 * @date 2018/10/18 10:55
 */
public class DeleteManageUserController extends AbstractApiController<DeleteManageUserForm> {

    @Override
    protected Result<Void> doBiz(HttpServletRequest request, HttpServletResponse response, DeleteManageUserForm command) throws Exception {
        ManageUserService service = BeanFactory.getBean(ManageUserService.class);
        service.deleteOne(command.getId());
        return Result.buildSuccessResult(null);
    }

}
