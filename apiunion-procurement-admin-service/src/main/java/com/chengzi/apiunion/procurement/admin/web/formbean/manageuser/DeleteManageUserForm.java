package com.chengzi.apiunion.procurement.admin.web.formbean.manageuser;

import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;

/**
 * @author 月汐
 * @date 2018/10/18 10:55
 */
public class DeleteManageUserForm extends BaseForm {

    @Min(1)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
