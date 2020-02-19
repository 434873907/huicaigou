package com.chengzi.apiunion.procurement.admin.web.formbean.invitationcode;

import com.chengzi.common.web.formbean.BaseForm;

/**
 * @author 月汐
 * @date 2019/12/06 11:28
 */
public class UpdateInvitationStatusForm extends BaseForm {

    private long id;

    private int  status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
