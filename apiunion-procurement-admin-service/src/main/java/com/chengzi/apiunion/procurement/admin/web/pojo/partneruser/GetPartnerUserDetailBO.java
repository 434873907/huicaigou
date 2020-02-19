package com.chengzi.apiunion.procurement.admin.web.pojo.partneruser;

import com.chengzi.apiunion.procurement.admin.partneruser.pojo.PartnerUserDO;
import com.chengzi.common.data.beans.JsonPojo;

/**
 * @author 月汐
 * @date 2018/11/9 11:34
 */
public class GetPartnerUserDetailBO extends JsonPojo {

    private Long id;

    /**
     * 账号
     */
    private String account;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * logo链接
     */
    private String logoUrl;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话
     */
    private String phone;

    /**
     * 联系人
     */
    private String contacts;

    /**
     * 角色ID
     */
    private String roleIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public static GetPartnerUserDetailBO convert(PartnerUserDO user, String roleNames) {
        GetPartnerUserDetailBO bo = new GetPartnerUserDetailBO();
        bo.setId(user.getId());
        bo.setAccount(user.getAccount());
        bo.setNickName(user.getNickName());
        bo.setLogoUrl(user.getLogoUrl());
        bo.setEmail(user.getEmail());
        bo.setPhone(user.getPhone());
        bo.setRoleIds(roleNames);
        return bo;
    }

}
