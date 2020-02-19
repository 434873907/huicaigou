package com.chengzi.apiunion.procurement.admin.web.pojo.invitationcode;

import com.chengzi.common.data.beans.JsonPojo;

/**
 * @author 月汐
 * @date 2019/12/09 10:23
 */
public class InvitationCodeMatchTypeBO extends JsonPojo {

    private int    type;

    private String remark;

    public InvitationCodeMatchTypeBO() {
    }

    public InvitationCodeMatchTypeBO(int type, String remark) {
        this.type = type;
        this.remark = remark;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
