package com.chengzi.apiunion.procurement.admin.web.pojo.pcconfig.tab;

import java.util.Date;
import java.util.List;

import com.chengzi.apiunion.pcconfig.tab.pojo.Condition;
import com.chengzi.apiunion.pcconfig.tab.pojo.TabConfigDO;
import com.chengzi.common.data.beans.JsonPojo;

/**
 * @author 苏子
 * @date 2019年1月18日
 */
public class TabConfigBO extends JsonPojo {
    
    private long            id;
    /**
     * 标题
     */
    private String          title;

    private List<Condition> conditionList;
    /**
     * 开始时间
     */
    private Date            startTime;
    /**
     * 结束时间
     */
    private Date            endTime;
    /**
     * 状态，0：禁用，1：启用
     */
    private int             status = 0;

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

    public List<Condition> getConditionList() {
        return conditionList;
    }

    public void setConditionList(List<Condition> conditionList) {
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
    
    public static TabConfigBO convert(TabConfigDO tabConfig){
        if (tabConfig == null) {
            return null;
        }
        TabConfigBO bo = new TabConfigBO();
        bo.setId(tabConfig.getId());
        bo.setTitle(tabConfig.getTitle());
        bo.setConditionList(Condition.parseList(tabConfig.getCondition()));
        bo.setStartTime(tabConfig.getStartTime());
        bo.setEndTime(tabConfig.getEndTime());
        bo.setStatus(tabConfig.getStatus());
        return bo;
    }
}
