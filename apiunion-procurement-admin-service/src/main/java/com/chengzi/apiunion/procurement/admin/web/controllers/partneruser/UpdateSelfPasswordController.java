package com.chengzi.apiunion.procurement.admin.web.controllers.partneruser;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.partneruser.service.PartnerUserService;
import com.chengzi.apiunion.procurement.admin.web.formbean.partneruser.UpdateSelfPasswordForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 修改个人密码
 *
 * @author 月汐
 * @date 2018/10/18 14:08
 */
public class UpdateSelfPasswordController extends AbstractApiController<UpdateSelfPasswordForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, UpdateSelfPasswordForm command) throws Exception {
        PartnerUserService service = BeanFactory.getBean(PartnerUserService.class);
        return service.updatePassword(RequestContext.getUserId(), command.getOriginalPassword(), command.getNewPassword());
    }

}
