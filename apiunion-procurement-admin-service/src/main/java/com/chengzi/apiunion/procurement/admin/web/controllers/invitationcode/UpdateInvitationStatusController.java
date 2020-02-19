package com.chengzi.apiunion.procurement.admin.web.controllers.invitationcode;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.invitationcode.service.InvitationCodeService;
import com.chengzi.apiunion.procurement.admin.web.formbean.invitationcode.UpdateInvitationStatusForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 月汐
 * @date 2019/12/06 11:27
 */
public class UpdateInvitationStatusController extends AbstractApiController<UpdateInvitationStatusForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, UpdateInvitationStatusForm command) throws Exception {
        InvitationCodeService invitationCodeService = BeanFactory.getBean(InvitationCodeService.class);
        return invitationCodeService.updateStatus(command.getId(), command.getStatus());
    }
}
