package com.chengzi.apiunion.procurement.admin.web.formbean.partneruser;

import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;
import com.chengzi.common.web.formbean.BaseForm;

/**
 * @author 月汐
 * @date 2018/10/18 14:12
 */
public class PartnerUserLoginForm extends BaseForm {

    @NotBlankAndNull(message = "请输入用户名")
    private String account;

    @NotBlankAndNull(message = "请输入密码")
    private String password;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
