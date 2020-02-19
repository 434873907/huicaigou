package com.chengzi.apiunion.procurement.admin.web.controllers.user;

import com.chengzi.apiunion.account.service.UserLevelRuleService;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.user.UpdateUserLevelRuleStatusForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 更新用户等级规则启用状态
 *
 * @author 月汐
 * @date 2019/1/30 11:38
 */
public class UpdateUserLevelRuleStatusController extends AbstractApiController<UpdateUserLevelRuleStatusForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, UpdateUserLevelRuleStatusForm command) throws Exception {
        UserLevelRuleService userLevelRuleService = BeanFactory.getBean(UserLevelRuleService.class);

        return userLevelRuleService.updateStatus(command.getId(), command.getStatus());
    }
}
