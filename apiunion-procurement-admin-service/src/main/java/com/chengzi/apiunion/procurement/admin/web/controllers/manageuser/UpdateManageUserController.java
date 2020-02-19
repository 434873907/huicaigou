package com.chengzi.apiunion.procurement.admin.web.controllers.manageuser;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.manageuser.service.ManageUserService;
import com.chengzi.apiunion.procurement.admin.web.formbean.manageuser.UpdateManageUserForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 月汐
 * @date 2018/10/18 11:44
 */
public class UpdateManageUserController extends AbstractApiController<UpdateManageUserForm> {
    @Override
    protected Result<Long> doBiz(HttpServletRequest request, HttpServletResponse response, UpdateManageUserForm command) throws Exception {
        ManageUserService service = BeanFactory.getBean(ManageUserService.class);
        return service.updateById(command.getId(), command.getNickName(), command.getLogoUrl(), command.getPassword());
    }
}
