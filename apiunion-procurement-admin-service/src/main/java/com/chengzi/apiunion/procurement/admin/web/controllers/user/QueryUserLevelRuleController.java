package com.chengzi.apiunion.procurement.admin.web.controllers.user;

import com.chengzi.apiunion.account.enums.TimeIntervalTypeEnum;
import com.chengzi.apiunion.account.pojo.LevelRule;
import com.chengzi.apiunion.account.pojo.UserLevelRuleDO;
import com.chengzi.apiunion.account.service.UserLevelRuleService;
import com.chengzi.apiunion.common.data.beans.RouteQuery;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.pojo.user.UserLevelRuleBO;
import com.chengzi.common.data.beans.PageDataList;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.BasePageForm;
import org.apache.commons.lang3.StringUtils;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 查询用户等级规则列表
 *
 * @author 月汐
 * @date 2019/1/30 11:57
 */
public class QueryUserLevelRuleController extends AbstractApiController<BasePageForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, BasePageForm command) throws Exception {
        UserLevelRuleService userLevelRuleService = BeanFactory.getBean(UserLevelRuleService.class);

        RouteQuery query = new RouteQuery();
        query.setLimit(command.getLimit());
        query.setOffset(command.getOffset());

        List<UserLevelRuleDO> userLevelRuleDOList = userLevelRuleService.query(query);

        List<UserLevelRuleBO> boList = new ArrayList<>();
        for (UserLevelRuleDO userLevelRuleDO : userLevelRuleDOList) {
            UserLevelRuleBO bo = new UserLevelRuleBO();
            bo.setId(userLevelRuleDO.getId());
            bo.setRuleName(userLevelRuleDO.getRuleName());
            bo.setTimeLimit(userLevelRuleDO.getTimeInterval() + TimeIntervalTypeEnum.parse(userLevelRuleDO.getTimeIntervalType()).getRemark());
            if (StringUtils.isNotBlank(userLevelRuleDO.getLevelRules()) && !userLevelRuleDO.getLevelRules().equals("null")) {
                bo.setLevelString(genLevelString(userLevelRuleDO.getLevelRules()));
            }
            bo.setStatus(userLevelRuleDO.getStatus());
            boList.add(bo);
        }

        long total = userLevelRuleService.countByQuery();

        return Result.buildSuccessResult(new PageDataList<>(total, command.getPage(), command.getLimit(), boList));
    }

    private String genLevelString(String levelString) {
        StringBuilder result = new StringBuilder();
        List<LevelRule> levelRules = LevelRule.parseArray(levelString);

        for (LevelRule levelRule : levelRules) {
            result.append(levelRule.getName()).append(":");
            result.append(levelRule.getLimitValue().toString()).append("元以上;");
        }

        return result.toString();
    }
}
