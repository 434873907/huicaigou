package com.chengzi.apiunion.procurement.admin.web.formbean.pcconfig.tab;

import java.util.Date;
import java.util.List;

import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;
import org.summercool.web.annotation.JsonField;

import com.chengzi.apiunion.pcconfig.tab.pojo.Condition;
import com.chengzi.common.web.formbean.BaseForm;

/**
 * @author 苏子
 * @date 2019年1月18日
 */
public class AddTabConfigForm extends BaseForm {
    /**
     * 标题
     */
    private String          title;
    /**
     * 条件
     */
    @JsonField
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
}
