package com.chengzi.apiunion.procurement.admin.web.controllers.user;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.user.UpdateUserRemarkForm;
import com.chengzi.apiunion.user.service.UserService;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 月汐
 * @date 2019/10/30 20:58
 */
public class UpdateUserRemarkController extends AbstractApiController<UpdateUserRemarkForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, UpdateUserRemarkForm command) throws Exception {
        UserService userService = BeanFactory.getBean(UserService.class);

        return userService.updateRemark(command.getUserId(), command.getRemark());
    }
}
