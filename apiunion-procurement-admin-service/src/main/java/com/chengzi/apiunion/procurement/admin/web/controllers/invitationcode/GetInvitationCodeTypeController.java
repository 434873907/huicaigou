package com.chengzi.apiunion.procurement.admin.web.controllers.invitationcode;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.invitationcode.enums.InvitationCodeTypeEnum;
import com.chengzi.apiunion.procurement.admin.web.pojo.invitationcode.InvitationCodeTypeBO;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.EmptyForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 月汐
 * @date 2019/12/09 09:48
 */
public class GetInvitationCodeTypeController extends AbstractApiController<EmptyForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {
        List<InvitationCodeTypeBO> boList = new ArrayList<>();

        for (InvitationCodeTypeEnum invitationCodeType : InvitationCodeTypeEnum.values()) {
            boList.add(new InvitationCodeTypeBO(invitationCodeType.getCode(), invitationCodeType.getRemark()));
        }

        return Result.buildSuccessResult(boList);
    }
}
