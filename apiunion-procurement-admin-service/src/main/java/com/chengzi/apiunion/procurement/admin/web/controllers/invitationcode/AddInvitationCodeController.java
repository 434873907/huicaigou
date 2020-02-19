package com.chengzi.apiunion.procurement.admin.web.controllers.invitationcode;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.invitationcode.enums.InvitationCodeMatchTypeEnum;
import com.chengzi.apiunion.invitationcode.pojo.InvitationCodeDO;
import com.chengzi.apiunion.invitationcode.service.InvitationCodeService;
import com.chengzi.apiunion.procurement.admin.web.formbean.invitationcode.AddInvitationCodeForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 月汐
 * @date 2019/12/06 11:00
 */
public class AddInvitationCodeController extends AbstractApiController<AddInvitationCodeForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, AddInvitationCodeForm command) throws Exception {
        InvitationCodeService invitationCodeService = BeanFactory.getBean(InvitationCodeService.class);

        InvitationCodeDO invitationCodeDO = new InvitationCodeDO();
        invitationCodeDO.setMatchType(command.getMatchType().getCode());
        if (command.getMatchType() == InvitationCodeMatchTypeEnum.ONT_TO_ONE) {
            invitationCodeDO.setNum(1);
        } else {
            if (command.getAvailableNum() < 2) {
                return Result.buildIllegalArgumentResult("请输入正确的数量");
            }
            invitationCodeDO.setNum(command.getAvailableNum());
        }
        invitationCodeDO.setStatus(command.getStatus());
        invitationCodeDO.setType(command.getType().getCode());
        invitationCodeDO.setUsedNum(0);
        invitationCodeDO.setUserId(RequestContext.getUserId());
        invitationCodeDO.setUserName(RequestContext.getUserName());
        invitationCodeDO.setDesc(command.getDesc());

        int i;
        for (i = 0; i < command.getGenerateNum(); i++) {
            if (!invitationCodeService.addInvitationCode(invitationCodeDO).isSuccess()) {
                break;
            }
        }
        return Result.buildSuccessResult("添加成功，本次共新增" + i + "个邀请码");
    }
}
