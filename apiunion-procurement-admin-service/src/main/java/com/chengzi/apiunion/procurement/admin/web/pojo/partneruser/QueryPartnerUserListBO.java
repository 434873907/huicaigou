package com.chengzi.apiunion.procurement.admin.web.pojo.partneruser;

import com.chengzi.apiunion.procurement.admin.partneruser.pojo.PartnerUserRichDO;
import com.chengzi.common.data.beans.JsonPojo;

/**
 * @author 月汐
 * @date 2018/10/18 16:02
 */
public class QueryPartnerUserListBO extends JsonPojo {

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
     * 用户角色
     */
    private String roleNames;

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

    public String getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
    }

    public static QueryPartnerUserListBO convert(PartnerUserRichDO userRichDO) {
        QueryPartnerUserListBO bo = new QueryPartnerUserListBO();
        bo.setId(userRichDO.getId());
        bo.setAccount(userRichDO.getAccount());
        bo.setNickName(userRichDO.getNickName());
        bo.setLogoUrl(userRichDO.getLogoUrl());
        bo.setEmail(userRichDO.getEmail());
        bo.setPhone(userRichDO.getPhone());
        bo.setContacts(userRichDO.getContacts());
        bo.setRoleNames(userRichDO.getRoleNames());
        return bo;
    }
}
