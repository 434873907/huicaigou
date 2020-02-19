package com.chengzi.apiunion.procurement.admin.web.formbean.supplier.order;

import com.chengzi.common.web.formbean.BaseForm;

import java.util.Date;

/**
 * @author 月汐
 * @date 2019/3/15 14:37
 */
public class StatisticByDayForm extends BaseForm {

    private Date startTime;

    private Date endTime;

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
