package com.chengzi.apiunion.procurement.admin.web.controllers.manageuser;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.manageuser.pojo.ManageUserDO;
import com.chengzi.apiunion.procurement.admin.manageuser.service.ManageUserService;
import com.chengzi.apiunion.procurement.admin.web.formbean.manageuser.QueryManageUserListForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.manageuser.QueryManageUserListBO;
import com.chengzi.common.data.beans.PageDataList;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 月汐
 * @date 2018/10/18 9:59
 */
public class QueryManageUserListController extends AbstractApiController<QueryManageUserListForm> {

    @Override
    protected Result<PageDataList<QueryManageUserListBO>> doBiz(HttpServletRequest request, HttpServletResponse response, QueryManageUserListForm command) throws Exception {
        ManageUserService service = BeanFactory.getBean(ManageUserService.class);
        List<ManageUserDO> list = service.queryManageUserList(command.getNickName(), command.getOffset(), command.getLimit());
        List<QueryManageUserListBO> resultList = new ArrayList<>();
        for (ManageUserDO manageUserDO : list) {
            resultList.add(QueryManageUserListBO.convert(manageUserDO));
        }
        long total = service.getManageUserListSize(command.getNickName());
        return Result.buildSuccessResult(new PageDataList<>(total, command.getPage(), command.getLimit(), resultList));
    }

}
