package com.chengzi.apiunion.procurement.admin.web.controllers.manageuser;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.manageuser.service.ManageUserService;
import com.chengzi.apiunion.procurement.admin.web.formbean.manageuser.UpdateSelfPasswordForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 月汐
 * @date 2018/10/17 20:10
 */
public class UpdateSelfPasswordController extends AbstractApiController<UpdateSelfPasswordForm> {

    @Override
    protected Result<Void> doBiz(HttpServletRequest request, HttpServletResponse response, UpdateSelfPasswordForm command) throws Exception {
        ManageUserService service = BeanFactory.getBean(ManageUserService.class);
        return service.updatePasswordById(command.getId(), command.getOriginalPassword(), command.getNewPassword());
    }

}
