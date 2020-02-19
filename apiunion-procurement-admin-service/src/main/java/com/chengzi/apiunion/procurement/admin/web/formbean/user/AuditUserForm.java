package com.chengzi.apiunion.procurement.admin.web.formbean.user;

import com.chengzi.common.web.formbean.BaseForm;

/**
 * @author 月汐
 * @date 2019/3/8 17:58
 */
public class AuditUserForm extends BaseForm {

    private long id;

    private int  auditStatus;

    private String  remark;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(int auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
