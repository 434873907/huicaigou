package com.chengzi.apiunion.procurement.admin.web.pojo.user;

import com.chengzi.common.data.beans.JsonPojo;

/**
 * @author 月汐
 * @date 2019/1/30 12:00
 */
public class UserLevelRuleBO extends JsonPojo {

    private long id;

    private String ruleName;

    private String timeLimit;

    private String levelString;

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

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getLevelString() {
        return levelString;
    }

    public void setLevelString(String levelString) {
        this.levelString = levelString;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
