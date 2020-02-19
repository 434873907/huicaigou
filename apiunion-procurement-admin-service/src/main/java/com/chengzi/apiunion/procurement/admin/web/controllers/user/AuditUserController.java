package com.chengzi.apiunion.procurement.admin.web.controllers.user;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.user.AuditUserForm;
import com.chengzi.apiunion.user.pojo.UserDO;
import com.chengzi.apiunion.user.service.UserService;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 月汐
 * @date 2019/3/8 17:58
 */
public class AuditUserController extends AbstractApiController<AuditUserForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, AuditUserForm command) throws Exception {
        UserService userService = BeanFactory.getBean(UserService.class);

        UserDO userDO = new UserDO();
        userDO.setId(command.getId());
        userDO.setAuditStatus(command.getAuditStatus());
        userDO.setRemark(command.getRemark());

        return userService.updateAuditStatus(userDO);
    }
}
