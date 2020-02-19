package com.chengzi.apiunion.procurement.admin.web.formbean.activity;

import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;

/**
 * <p>
 * </p>
 * User: 摩天
 * Date: 2019-02-20 17:17
 */
public class GetFlashSaleActivityForm extends BaseForm {

    /**
     * 活动id
     */
    @Min(value = 1, message = "活动id不能为空")
    private long activityId;

    public long getActivityId() {
        return activityId;
    }

    public void setActivityId(long activityId) {
        this.activityId = activityId;
    }
}
