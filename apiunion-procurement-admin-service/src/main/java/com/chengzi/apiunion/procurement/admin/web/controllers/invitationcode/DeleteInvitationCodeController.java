package com.chengzi.apiunion.procurement.admin.web.controllers.invitationcode;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.invitationcode.service.InvitationCodeService;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.IdForm;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 月汐
 * @date 2019/12/06 11:30
 */
public class DeleteInvitationCodeController extends AbstractApiController<IdForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, IdForm command) throws Exception {
        InvitationCodeService invitationCodeService = BeanFactory.getBean(InvitationCodeService.class);
        return invitationCodeService.delete(command.getId());
    }
}
