package com.chengzi.apiunion.procurement.admin.web.formbean.activity;

import com.chengzi.common.web.formbean.BaseForm;
import com.chengzi.common.web.formbean.BasePageForm;

import java.util.Date;

/**
 * <p>
 * </p>
 * User: 摩天
 * Date: 2019-02-20 11:08
 */
public class FlashSaleActivityListForm extends BasePageForm {
    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
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
