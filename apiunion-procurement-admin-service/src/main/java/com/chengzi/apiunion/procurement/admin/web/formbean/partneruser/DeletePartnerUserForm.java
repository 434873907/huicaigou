package com.chengzi.apiunion.procurement.admin.web.formbean.partneruser;

import net.sf.oval.constraint.Min;

/**
 * @author 月汐
 * @date 2018/10/18 14:11
 */
public class DeletePartnerUserForm {

    @Min(value = 1, message = "请选择正确的用户")
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
