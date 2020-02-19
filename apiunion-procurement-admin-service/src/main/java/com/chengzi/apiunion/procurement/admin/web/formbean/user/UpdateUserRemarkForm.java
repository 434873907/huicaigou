package com.chengzi.apiunion.procurement.admin.web.formbean.user;

import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.MaxLength;

/**
 * @author 月汐
 * @date 2019/10/30 20:59
 */
public class UpdateUserRemarkForm extends BaseForm {

    private long   userId;

    @MaxLength(value = 15, message = "备注不能超过15个字")
    private String remark;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
