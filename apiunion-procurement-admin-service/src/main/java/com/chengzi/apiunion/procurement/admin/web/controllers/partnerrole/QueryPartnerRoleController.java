package com.chengzi.apiunion.procurement.admin.web.controllers.partnerrole;

import com.chengzi.apiunion.common.data.beans.RouteQuery;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.pojo.partnerrole.QueryPartnerRoleBO;
import com.chengzi.apiunion.procurement.role.partnerrole.pojo.PartnerRoleDO;
import com.chengzi.apiunion.procurement.role.partnerrole.service.PartnerRoleService;
import com.chengzi.common.data.beans.PageDataList;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.BasePageForm;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取角色列表
 *
 * @author 月汐
 * @date 2018/11/5 16:23
 */
public class QueryPartnerRoleController extends AbstractApiController<BasePageForm> {

    @Override
    protected Result<PageDataList<QueryPartnerRoleBO>> doBiz(HttpServletRequest request, HttpServletResponse response, BasePageForm command) throws Exception {
        RouteQuery query = new RouteQuery();
        query.setOffset(command.getOffset());
        query.setLimit(command.getLimit());

        List<QueryPartnerRoleBO> resultList = new ArrayList<>();

        PartnerRoleService service = BeanFactory.getBean(PartnerRoleService.class);
        List<PartnerRoleDO> roleList = service.queryList(query);
        for (PartnerRoleDO role : roleList) {
            String userName = service.getUserNameByRoleId(role.getId());
            resultList.add(QueryPartnerRoleBO.convert(role, userName));
        }

        long total = service.countByQuery(query);

        return Result.buildSuccessResult(new PageDataList<>(total, command.getPage(), command.getLimit(), resultList));
    }

}
