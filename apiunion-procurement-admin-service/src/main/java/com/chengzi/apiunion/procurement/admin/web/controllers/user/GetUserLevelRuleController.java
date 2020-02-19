package com.chengzi.apiunion.procurement.admin.web.controllers.user;

import com.chengzi.apiunion.account.pojo.UserLevelRuleDO;
import com.chengzi.apiunion.account.service.UserLevelRuleService;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.user.GetUserLevelRuleForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.user.UserLevelRuleDetailBO;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 获取用户等级规则详情
 *
 * @author 月汐
 * @date 2019/1/30 11:48
 */
public class GetUserLevelRuleController extends AbstractApiController<GetUserLevelRuleForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, GetUserLevelRuleForm command) throws Exception {
        UserLevelRuleService userLevelRuleService = BeanFactory.getBean(UserLevelRuleService.class);

        UserLevelRuleDO userLevelRuleDO = userLevelRuleService.getById(command.getId());

        UserLevelRuleDetailBO bo = new UserLevelRuleDetailBO();
        bo.setId(userLevelRuleDO.getId());
        bo.setTimeInterval(userLevelRuleDO.getTimeInterval());
        bo.setTimeIntervalType(userLevelRuleDO.getTimeIntervalType());
        bo.setLevelRules(userLevelRuleDO.getLevelRules());
        bo.setStatus(userLevelRuleDO.getStatus());
        bo.setRuleName(userLevelRuleDO.getRuleName());

        return Result.buildSuccessResult(bo);
    }
}
