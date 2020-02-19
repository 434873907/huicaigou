package com.chengzi.apiunion.procurement.admin.web.controllers.partneruser;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.common.web.cookie.CookieUtils;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.EmptyForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户退出
 *
 * @author 月汐
 * @date 2019/1/4 17:53
 */
public class PartnerUserLogoutController extends AbstractApiController<EmptyForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {
        CookieUtils.clearCookie(request);
        return Result.buildSuccessResult("退出成功");
    }
}
