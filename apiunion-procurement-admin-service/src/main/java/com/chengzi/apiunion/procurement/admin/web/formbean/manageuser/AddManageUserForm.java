package com.chengzi.apiunion.procurement.admin.web.formbean.manageuser;

import com.chengzi.apiunion.procurement.admin.manageuser.pojo.ManageUserDO;
import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;
import com.chengzi.common.web.formbean.BaseForm;
import org.apache.commons.lang3.StringUtils;
import org.summercool.util.MD5Util;

/**
 * @author 月汐
 * @date 2018/10/18 10:38
 */
public class AddManageUserForm extends BaseForm {

    @NotBlankAndNull(message = "用户名不能为空")
    private String account;

    private String nickName;

    @NotBlankAndNull(message = "密码不能为空")
    private String password;

    private String logoUrl;

    private Long parentId;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public static ManageUserDO convert(AddManageUserForm form) {
        ManageUserDO manageUserDO = new ManageUserDO();
        manageUserDO.setAccount(form.getAccount());
        if (StringUtils.isNotBlank(form.getNickName())) {
            manageUserDO.setNickName(form.getNickName());
        } else {
            manageUserDO.setNickName(form.getAccount());
        }
        manageUserDO.setPassword(MD5Util.getMD5Format(form.getPassword()));
        manageUserDO.setParentId(form.getParentId());
        manageUserDO.setLogoUrl(form.getLogoUrl());
        return manageUserDO;
    }
}
