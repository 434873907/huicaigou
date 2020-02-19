package com.chengzi.apiunion.procurement.admin.web.controllers.manageuser;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.manageuser.service.ManageUserService;
import com.chengzi.apiunion.procurement.admin.web.formbean.manageuser.DeleteManageUserListForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 月汐
 * @date 2018/10/18 11:05
 */
public class DeleteManageUserListController extends AbstractApiController<DeleteManageUserListForm> {

    @Override
    protected Result<Void> doBiz(HttpServletRequest request, HttpServletResponse response, DeleteManageUserListForm command) throws Exception {
        ManageUserService service = BeanFactory.getBean(ManageUserService.class);
        service.deleteManagerUserList(command.getIds());
        return Result.buildSuccessResult(null);
    }

}
