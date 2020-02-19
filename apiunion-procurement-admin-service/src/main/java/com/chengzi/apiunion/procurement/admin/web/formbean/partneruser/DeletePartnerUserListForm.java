package com.chengzi.apiunion.procurement.admin.web.formbean.partneruser;

import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.MinSize;

import java.util.List;

/**
 * @author 月汐
 * @date 2018/10/18 14:11
 */
public class DeletePartnerUserListForm extends BaseForm {

    @MinSize(value = 1, message = "请选择正确的用户")
    private List<Long> ids;

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}
