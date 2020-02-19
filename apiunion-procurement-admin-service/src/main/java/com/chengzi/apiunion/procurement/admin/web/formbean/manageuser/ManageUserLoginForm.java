package com.chengzi.apiunion.procurement.admin.web.formbean.manageuser;

import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;
import com.chengzi.common.web.formbean.BaseForm;

/**
 * @author 月汐
 * @date 2018/10/17 17:10
 */
public class ManageUserLoginForm extends BaseForm {

    @NotBlankAndNull(message = "账号不能为空")
    private String account;

    @NotBlankAndNull(message = "密码不能为空")
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
