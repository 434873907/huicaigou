package com.chengzi.apiunion.procurement.admin.web.formbean.supplier.announcement;

import com.chengzi.common.web.formbean.BasePageForm;

import java.util.Date;

/**
 * @author 月汐
 * @date 2020/01/16 14:02
 */
public class QueryAnnouncementListForm extends BasePageForm {

    /**
     * 关键词
     */
    private String keyword;

    /**
     * 起始日期
     */
    private Date   startTime;

    /**
     * 截止日期
     */
    private Date   endTime;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
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
}
