package com.chengzi.apiunion.procurement.admin.web.controllers.run.form;

import com.chengzi.common.web.formbean.BaseForm;

import java.util.List;

/**
 * @author 行者
 */
public class UpdateActivityItemByThirdSkuIdForm extends BaseForm {

    private long activityId;

    private List<String> thirdSkuIds;

    public long getActivityId() {
        return activityId;
    }

    public void setActivityId(long activityId) {
        this.activityId = activityId;
    }

    public List<String> getThirdSkuIds() {
        return thirdSkuIds;
    }

    public void setThirdSkuIds(List<String> thirdSkuIds) {
        this.thirdSkuIds = thirdSkuIds;
    }
}
