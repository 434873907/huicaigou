package com.chengzi.apiunion.procurement.admin.web.pojo.user;

import com.chengzi.common.data.beans.JsonPojo;

/**
 * @author 月汐
 * @date 2019/1/30 11:50
 */
public class UserLevelRuleDetailBO extends JsonPojo {

    private long id;

    private String ruleName;

    private int timeInterval;

    private int timeIntervalType;

    private String levelRules;

    private int status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public int getTimeIntervalType() {
        return timeIntervalType;
    }

    public void setTimeIntervalType(int timeIntervalType) {
        this.timeIntervalType = timeIntervalType;
    }

    public String getLevelRules() {
        return levelRules;
    }

    public void setLevelRules(String levelRules) {
        this.levelRules = levelRules;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
