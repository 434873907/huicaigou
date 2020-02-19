package com.chengzi.apiunion.procurement.admin.web.controllers.manageuser;

import com.alibaba.fastjson.JSONObject;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.manageuser.pojo.ManageUserDO;
import com.chengzi.apiunion.procurement.admin.manageuser.service.ManageUserService;
import com.chengzi.apiunion.procurement.admin.web.formbean.manageuser.ManageUserLoginForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.manageuser.ManageUserLoginBO;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 月汐
 * @date 2018/10/17 16:56
 */
public class ManageUserLoginController extends AbstractApiController<ManageUserLoginForm> {

    @Override
    protected Result<ManageUserLoginBO> doBiz(HttpServletRequest request, HttpServletResponse response, ManageUserLoginForm command) throws Exception {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if ("manageUser".equals(cookie.getName())) {
                ManageUserLoginBO bo = (ManageUserLoginBO) JSONObject.parse(cookie.getValue());
                return Result.buildSuccessResult(bo);
            }
        }
        ManageUserService service = BeanFactory.getBean(ManageUserService.class);
        ManageUserDO manageUserDO = service.getUserByAccount(command.getAccount(), command.getPassword());
        if (manageUserDO == null) {
            return Result.buildOpFailedResult("用户名或密码错误");
        }
        ManageUserLoginBO bo = ManageUserLoginBO.convert(manageUserDO);
        Cookie cookie = new Cookie("manageUser", bo.toJsonString());
        response.addCookie(cookie);
        return Result.buildSuccessResult(bo);
    }

}
