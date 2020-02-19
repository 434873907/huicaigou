package com.chengzi.apiunion.procurement.admin.web.controllers.user;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.user.DisableUserForm;
import com.chengzi.apiunion.user.service.UserService;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * </p>
 * User: 摩天
 * Date: 2019-03-12 11:26
 */
public class DisableUserController extends AbstractApiController<DisableUserForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, DisableUserForm command) throws Exception {
        UserService userService = BeanFactory.getBean(UserService.class);
        int i = userService.updateDisable(command.getId(), command.getDisable());
        if (i > 0) {
            return Result.buildSuccessMsg("操作成功");
        }
        return Result.buildOpFailedResult("操作失败");
    }
}
