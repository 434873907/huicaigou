package com.chengzi.apiunion.procurement.admin.web.controllers.user;

import com.alibaba.fastjson.JSONArray;
import com.chengzi.apiunion.account.enums.TimeIntervalTypeEnum;
import com.chengzi.apiunion.account.pojo.LevelRule;
import com.chengzi.apiunion.account.pojo.UserLevelRuleDO;
import com.chengzi.apiunion.account.service.UserLevelRuleService;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.user.AddUserLevelRuleForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Comparator;
import java.util.List;

/**
 * 添加用户等级规则
 *
 * @author 月汐
 * @date 2019/1/30 10:50
 */
public class AddUserLevelRuleController extends AbstractApiController<AddUserLevelRuleForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, AddUserLevelRuleForm command) throws Exception {
        UserLevelRuleService userLevelRuleService = BeanFactory.getBean(UserLevelRuleService.class);

        UserLevelRuleDO userLevelRuleDO = new UserLevelRuleDO();
        userLevelRuleDO.setStatus(0);
        userLevelRuleDO.setRuleName(command.getRuleName());
        if (command.getTimeInterval() > 0) {
            userLevelRuleDO.setTimeInterval(command.getTimeInterval());
            userLevelRuleDO.setTimeIntervalType(command.getTimeIntervalType().getCode());
        } else {
            userLevelRuleDO.setTimeInterval(0);
            userLevelRuleDO.setTimeIntervalType(TimeIntervalTypeEnum.NONE.getCode());
        }
        userLevelRuleDO.setLevelRules(JSONArray.toJSONString(getCompletedLevelRules(command.getLevelRules())));
        userLevelRuleDO.setStatus(command.getStatus());

        return userLevelRuleService.add(userLevelRuleDO);
    }

    private List<LevelRule> getCompletedLevelRules(List<LevelRule> levelRules) {
        levelRules.sort(Comparator.comparing(LevelRule::getLimitValue));
        int i = 0;
        for (LevelRule levelRule : levelRules) {
            levelRule.setLevel(++i);
        }
        return levelRules;
    }
}
