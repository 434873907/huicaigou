package com.chengzi.apiunion.procurement.admin.web.formbean.announcement;

import com.chengzi.common.web.formbean.BasePageForm;

import java.util.Date;

/**
 * @author 月汐
 * @date 2018/10/22 20:28
 */
public class QueryAnnouncementForm extends BasePageForm {

    /**
     * 公告标题
     */
//    @NotBlankAndNull(message = "请输入公告标题")
    private String key;

    /**
     * 公告发布时间区间下界
     */
    private Date beginTime;

    /**
     * 公告发布时间区间上界
     */
    private Date endTime;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
