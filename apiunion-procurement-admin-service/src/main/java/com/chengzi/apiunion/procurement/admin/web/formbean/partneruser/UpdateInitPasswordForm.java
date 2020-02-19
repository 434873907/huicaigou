package com.chengzi.apiunion.procurement.admin.web.formbean.partneruser;

import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;

/**
 * @author 月汐
 * @date 2018/10/18 14:12
 */
public class UpdateInitPasswordForm extends BaseForm {

    @Min(value = 1, message = "请选择正确的用户")
    private long id;

    private String password = "123456";

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
