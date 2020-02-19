package com.chengzi.apiunion.procurement.admin.web.controllers.manageuser;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.manageuser.constant.ManageUserConstant;
import com.chengzi.apiunion.procurement.admin.manageuser.service.ManageUserService;
import com.chengzi.apiunion.procurement.admin.web.formbean.manageuser.UpdateInitPasswordForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 月汐
 * @date 2018/10/17 17:41
 */
public class UpdateInitPasswordController extends AbstractApiController<UpdateInitPasswordForm> {

    @Override
    protected Result<String> doBiz(HttpServletRequest request, HttpServletResponse response, UpdateInitPasswordForm command) throws Exception {
        ManageUserService service = BeanFactory.getBean(ManageUserService.class);
        return service.updateInitPassword(command.getUserId());
    }

}
