package com.chengzi.apiunion.procurement.admin.web.controllers.partneruser;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.partneruser.pojo.PartnerUserDO;
import com.chengzi.apiunion.procurement.admin.partneruser.pojo.PartnerUserQuery;
import com.chengzi.apiunion.procurement.admin.partneruser.pojo.PartnerUserRichDO;
import com.chengzi.apiunion.procurement.admin.partneruser.service.PartnerUserService;
import com.chengzi.apiunion.procurement.admin.web.formbean.partneruser.QueryPartnerUserListForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.partneruser.QueryPartnerUserListBO;
import com.chengzi.common.data.beans.PageDataList;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取用户列表
 *
 * @author 月汐
 * @date 2018/10/18 14:05
 */
public class QueryPartnerUserListController extends AbstractApiController<QueryPartnerUserListForm> {

    @Override
    protected Result<PageDataList<QueryPartnerUserListBO>> doBiz(HttpServletRequest request, HttpServletResponse response, QueryPartnerUserListForm command) throws Exception {
        PartnerUserService service = BeanFactory.getBean(PartnerUserService.class);

        PartnerUserQuery query = new PartnerUserQuery();
        query.setNickName(command.getNickName());
        query.setOffset(command.getOffset());
        query.setLimit(command.getLimit());

        List<PartnerUserRichDO> list = service.queryPartnerUsers(query);
        List<QueryPartnerUserListBO> resultList = new ArrayList<>();
        for (PartnerUserRichDO userRichDO : list) {
            resultList.add(QueryPartnerUserListBO.convert(userRichDO));
        }
        long total = service.countPartnerUsers(query);

        return Result.buildSuccessResult(new PageDataList<>(total, command.getPage(), command.getLimit(), resultList));
    }

}
