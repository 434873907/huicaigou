package com.chengzi.apiunion.procurement.admin.web.controllers.manageuser;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.manageuser.service.ManageUserService;
import com.chengzi.apiunion.procurement.admin.web.formbean.manageuser.AddManageUserForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 月汐
 * @date 2018/10/18 10:37
 */
public class AddManageUserController extends AbstractApiController<AddManageUserForm> {

    @Override
    protected Result<Long> doBiz(HttpServletRequest request, HttpServletResponse response, AddManageUserForm command) throws Exception {
        ManageUserService service = BeanFactory.getBean(ManageUserService.class);
        return service.addManageUser(AddManageUserForm.convert(command));
    }

}
