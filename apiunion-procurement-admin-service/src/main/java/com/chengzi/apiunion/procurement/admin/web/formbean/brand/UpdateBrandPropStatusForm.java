package com.chengzi.apiunion.procurement.admin.web.formbean.brand;

import com.chengzi.common.web.formbean.BaseForm;

/**
 * @author 月汐
 * @date 2019/11/05 15:11
 */
public class UpdateBrandPropStatusForm extends BaseForm {

    private long id;

    private int propStatus;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPropStatus() {
        return propStatus;
    }

    public void setPropStatus(int propStatus) {
        this.propStatus = propStatus;
    }
}
