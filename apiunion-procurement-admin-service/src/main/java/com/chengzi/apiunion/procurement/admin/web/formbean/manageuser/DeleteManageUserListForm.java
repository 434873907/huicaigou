package com.chengzi.apiunion.procurement.admin.web.formbean.manageuser;

import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.MinSize;

import java.util.List;

/**
 * @author 月汐
 * @date 2018/10/18 11:06
 */
public class DeleteManageUserListForm extends BaseForm {

    @MinSize(1)
    private List<Long> ids;

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}
