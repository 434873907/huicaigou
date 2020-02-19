package com.chengzi.apiunion.procurement.admin.web.controllers.invitationcode;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.invitationcode.enums.InvitationCodeMatchTypeEnum;
import com.chengzi.apiunion.procurement.admin.web.pojo.invitationcode.InvitationCodeMatchTypeBO;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.EmptyForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 月汐
 * @date 2019/12/09 10:23
 */
public class GetInvitationCodeMatchTypeController extends AbstractApiController<EmptyForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {
        List<InvitationCodeMatchTypeBO> boList = new ArrayList<>();
        for (InvitationCodeMatchTypeEnum matchType : InvitationCodeMatchTypeEnum.values()) {
            boList.add(new InvitationCodeMatchTypeBO(matchType.getCode(), matchType.getRemark()));
        }
        return Result.buildSuccessResult(boList);
    }
}
