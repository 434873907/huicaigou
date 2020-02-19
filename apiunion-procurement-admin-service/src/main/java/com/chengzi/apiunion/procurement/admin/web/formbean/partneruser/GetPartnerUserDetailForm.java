package com.chengzi.apiunion.procurement.admin.web.formbean.partneruser;

import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;

/**
 * @author 月汐
 * @date 2018/11/9 11:33
 */
public class GetPartnerUserDetailForm extends BaseForm {

    @Min(value = 1, message = "请选择正确的用户信息")
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
