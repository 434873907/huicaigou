package com.chengzi.apiunion.procurement.admin.web.controllers.user;

import com.chengzi.apiunion.account.pojo.UserAccountFlowDO;
import com.chengzi.apiunion.account.query.UserAccountFlowQuery;
import com.chengzi.apiunion.account.service.UserAccountService;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.user.TopUpRecordForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.user.TopUpRecordBO;
import com.chengzi.common.data.beans.PageDataList;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static com.chengzi.apiunion.account.enums.AccountFlowTypeEnum.ACCOUNT_FLOW_TYPE_RECHARGE;

/**
 * <p>
 * </p>
 * User: 摩天
 * Date: 2019-03-12 16:53
 */
public class UserTopUpRecordController extends AbstractApiController<TopUpRecordForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, TopUpRecordForm command) throws Exception {
        UserAccountService userAccountService = BeanFactory.getBean(UserAccountService.class);
        UserAccountFlowQuery query = new UserAccountFlowQuery();
        query.setOffset(command.getOffset());
        query.setLimit(command.getLimit());
        query.setUserId(command.getId());
//        query.setFlowType(ACCOUNT_FLOW_TYPE_RECHARGE.getCode());
        int total = userAccountService.selectUserAccountFlowCount(query);
        List<UserAccountFlowDO> userAccountFlowDOS = new ArrayList<>();
        if (total > 0) {
            userAccountFlowDOS = userAccountService.queryUserAccountFlow(query);
        }
        List<TopUpRecordBO> topUpRecordBOS = new ArrayList<>();
        userAccountFlowDOS.forEach(x -> topUpRecordBOS.add(TopUpRecordBO.convert(x)));
        return Result.buildSuccessResult(new PageDataList(total, command.getPage(), command.getLimit(), topUpRecordBOS));

    }
}
