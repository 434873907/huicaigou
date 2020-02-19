package com.chengzi.apiunion.procurement.admin.web.formbean.partnerrole;

import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.MinSize;

import java.util.List;

/**
 * @author 月汐
 * @date 2018/11/5 17:07
 */
public class BatchDeletePartnerRoleForm extends BaseForm {

    @MinSize(value = 1, message = "请选择需要删除的角色")
    private List<Long> ids;

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}
