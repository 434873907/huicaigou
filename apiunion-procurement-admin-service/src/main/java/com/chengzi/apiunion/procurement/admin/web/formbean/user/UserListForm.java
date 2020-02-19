package com.chengzi.apiunion.procurement.admin.web.formbean.user;

import com.chengzi.common.web.formbean.BasePageForm;
import org.summercool.web.annotation.JsonField;

import java.util.List;

/**
 * <p>
 * </p>
 * User: 摩天
 * Date: 2018-10-31 16:47
 */
public class UserListForm extends BasePageForm {
    private int           type = -1;

    private String        name;

    @JsonField
    private List<Integer> auditStatusList;

    private String        remark;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getAuditStatusList() {
        return auditStatusList;
    }

    public void setAuditStatusList(List<Integer> auditStatusList) {
        this.auditStatusList = auditStatusList;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
