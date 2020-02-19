package com.chengzi.apiunion.procurement.admin.web.pojo.pcconfig.tab;

import java.util.Date;
import java.util.List;

import com.chengzi.common.data.beans.JsonPojo;

/**
 * @author 苏子
 * @date 2019年1月16日
 */
public class TabConfigListBO extends JsonPojo {

    private long         id;
    /**
     * 标题
     */
    private String       title;
    /**
     * 条件【官网：X,XX】【品牌：X,XX】..
     */
    private List<String> conditionList;
    /**
     * 开始时间
     */
    private Date         startTime;
    /**
     * 结束时间
     */
    private Date         endTime;
    /**
     * 状态
     */
    private int          status;
    /**
     * 状态
     */
    private String       statusStr;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getConditionList() {
        return conditionList;
    }

    public void setConditionList(List<String> conditionList) {
        this.conditionList = conditionList;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }
}
