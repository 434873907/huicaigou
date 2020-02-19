package com.chengzi.apiunion.procurement.admin.web.formbean.manageuser;

import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;
import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;

/**
 * @author 月汐
 * @date 2018/10/17 20:10
 */
public class UpdateSelfPasswordForm extends BaseForm {

    @Min(1)
    private Long id;

    @NotBlankAndNull(message = "请输入原始密码")
    private String originalPassword;

    @NotBlankAndNull(message = "请输入新密码")
    private String newPassword;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginalPassword() {
        return originalPassword;
    }

    public void setOriginalPassword(String originalPassword) {
        this.originalPassword = originalPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
