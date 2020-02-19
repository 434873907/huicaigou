package com.chengzi.apiunion.procurement.admin.web.formbean.partneruser;

import com.chengzi.apiunion.procurement.admin.partneruser.pojo.PartnerUserDO;
import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;
import com.chengzi.common.web.formbean.BaseForm;
import org.summercool.util.MD5Util;

/**
 * @author 月汐
 * @date 2018/10/18 14:10
 */
public class AddPartnerUserForm extends BaseForm {

    @NotBlankAndNull(message = "账号不能为空")
    private String account;

    @NotBlankAndNull(message = "密码不能为空")
    private String password;

    @NotBlankAndNull(message = "昵称不能为空")
    private String nickName;

    private String logoUrl;

    private String email;

    private String phone;

    /**
     * 联系人
     */
    private String contacts;

    /**
     * 角色ID
     */
    private String roleIds;

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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContacts() {
        return contacts;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public static PartnerUserDO convert(AddPartnerUserForm form) {
        PartnerUserDO partnerUserDO = new PartnerUserDO();
        partnerUserDO.setAccount(form.getAccount());
        partnerUserDO.setPassword(MD5Util.getMD5Format(form.getPassword()));
        partnerUserDO.setNickName(form.getNickName());
        partnerUserDO.setLogoUrl(form.getLogoUrl());
        partnerUserDO.setEmail(form.getEmail());
        partnerUserDO.setPhone(form.getPhone());
        partnerUserDO.setContacts(form.getContacts());
        return partnerUserDO;
    }
}
