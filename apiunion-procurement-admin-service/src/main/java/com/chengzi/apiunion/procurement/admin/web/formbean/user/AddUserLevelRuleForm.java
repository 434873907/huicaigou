package com.chengzi.apiunion.procurement.admin.web.formbean.user;

import com.chengzi.apiunion.account.enums.TimeIntervalTypeEnum;
import com.chengzi.apiunion.account.pojo.LevelRule;
import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;
import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;
import org.summercool.web.annotation.JsonField;

import java.util.Comparator;
import java.util.List;

/**
 * @author 月汐
 * @date 2019/1/30 10:50
 */
public class AddUserLevelRuleForm extends BaseForm {

    @NotBlankAndNull(message = "请输入规则名称")
    private String ruleName;

    private int timeInterval;

    private TimeIntervalTypeEnum timeIntervalType;

    @JsonField
    private List<LevelRule> levelRules;

    @Min(value = 0, message = "请选择正确的启用状态")
    private int status;

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public int getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(int timeInterval) {
        this.timeInterval = timeInterval;
    }

    public TimeIntervalTypeEnum getTimeIntervalType() {
        return timeIntervalType;
    }

    public void setTimeIntervalType(TimeIntervalTypeEnum timeIntervalType) {
        this.timeIntervalType = timeIntervalType;
    }

    public List<LevelRule> getLevelRules() {
        return levelRules;
    }

    public void setLevelRules(List<LevelRule> levelRules) {
        this.levelRules = levelRules;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
