package com.chengzi.apiunion.procurement.admin.web.controllers.user;

import com.chengzi.apiunion.account.service.UserLevelRuleService;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.user.DeleteUserLevelRuleForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 删除用户等级规则
 *
 * @author 月汐
 * @date 2019/1/30 11:21
 */
public class DeleteUserLevelRuleController extends AbstractApiController<DeleteUserLevelRuleForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, DeleteUserLevelRuleForm command) throws Exception {
        UserLevelRuleService userLevelRuleService = BeanFactory.getBean(UserLevelRuleService.class);

        return userLevelRuleService.delete(command.getId());
    }
}
