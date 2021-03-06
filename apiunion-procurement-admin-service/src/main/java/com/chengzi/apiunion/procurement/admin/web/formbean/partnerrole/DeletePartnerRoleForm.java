package com.chengzi.apiunion.procurement.admin.web.formbean.partnerrole;

import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;

/**
 * @author 月汐
 * @date 2018/11/5 15:39
 */
public class DeletePartnerRoleForm extends BaseForm {

    @Min(value = 1, message = "请选择正确的角色")
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
