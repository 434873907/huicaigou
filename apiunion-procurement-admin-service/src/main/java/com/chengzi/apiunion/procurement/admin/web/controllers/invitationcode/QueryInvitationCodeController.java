package com.chengzi.apiunion.procurement.admin.web.controllers.invitationcode;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.invitationcode.pojo.InvitationCodeDO;
import com.chengzi.apiunion.invitationcode.pojo.InvitationCodeQuery;
import com.chengzi.apiunion.invitationcode.service.InvitationCodeService;
import com.chengzi.apiunion.procurement.admin.web.formbean.invitationcode.QueryInvitationCodeForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.invitationcode.InvitationCodeBO;
import com.chengzi.common.data.beans.PageDataList;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 月汐
 * @date 2019/12/06 11:31
 */
public class QueryInvitationCodeController extends AbstractApiController<QueryInvitationCodeForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, QueryInvitationCodeForm command) throws Exception {
        InvitationCodeService invitationCodeService = BeanFactory.getBean(InvitationCodeService.class);

        InvitationCodeQuery query = new InvitationCodeQuery();
        query.setKeyword(command.getKeyword());
        if (command.getMatchType() != null) {
            query.setMatchType(command.getMatchType().getCode());
        }
        if (command.getType() != null) {
            query.setType(command.getType().getCode());
        }
        query.setStatus(command.getStatus());
        query.setLimit(command.getLimit());
        query.setOffset(command.getOffset());

        long count = invitationCodeService.countByQuery(query);
        List<InvitationCodeDO> invitationCodeList = invitationCodeService.getInvitationCodeList(query);

        List<InvitationCodeBO> boList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(invitationCodeList)) {
            invitationCodeList.forEach(invitationCodeDO -> boList.add(InvitationCodeBO.convert(invitationCodeDO)));
        }

        return Result.buildSuccessResult(new PageDataList<>(count, command.getPage(), command.getLimit(), boList));
    }
}
