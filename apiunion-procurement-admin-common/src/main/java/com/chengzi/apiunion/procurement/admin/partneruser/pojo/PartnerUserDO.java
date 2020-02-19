package com.chengzi.apiunion.procurement.admin.partneruser.pojo;

import com.chengzi.apiunion.common.data.beans.RouteBaseDO;

import java.util.Date;

/**
 * @author 
 */
public class PartnerUserDO extends RouteBaseDO {

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * logo图片地址
     */
    private String logoUrl;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 电话号码
     */
    private String phone;

    private String contacts;

    /**
     * 应用ID
     */
    private String appId;

    /**
     * 应用码
     */
    private String appSecret;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 最后访问时间
     */
    private Date lastVisitTime;

    /**
     * 最后密码修改时间
     */
    private Date lastPwdChangeTime;

    /**
     * 父账号id
     */
    private Long parentId;

    private static final long serialVersionUID = 1L;

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

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Date getLastVisitTime() {
        return lastVisitTime;
    }

    public void setLastVisitTime(Date lastVisitTime) {
        this.lastVisitTime = lastVisitTime;
    }

    public Date getLastPwdChangeTime() {
        return lastPwdChangeTime;
    }

    public void setLastPwdChangeTime(Date lastPwdChangeTime) {
        this.lastPwdChangeTime = lastPwdChangeTime;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}