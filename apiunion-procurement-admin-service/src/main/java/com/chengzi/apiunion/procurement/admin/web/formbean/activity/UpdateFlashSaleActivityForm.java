package com.chengzi.apiunion.procurement.admin.web.formbean.activity;

import net.sf.oval.constraint.Min;

/**
 * <p>
 * </p>
 * User: 摩天
 * Date: 2019-02-20 16:18
 */
public class UpdateFlashSaleActivityForm extends AddFlashSaleActivityForm{
    @Min(value = 0,message = "id不能为空")
    private long activityId;

    public long getActivityId() {
        return activityId;
    }

    public void setActivityId(long id) {
        this.activityId = id;
    }
}
