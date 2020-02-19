package com.chengzi.apiunion.procurement.admin.web.pojo.user;

import com.chengzi.apiunion.common.module.image.annotation.ImageDecorater;
import com.chengzi.apiunion.common.module.image.enums.ImageBizType;
import com.chengzi.common.data.beans.JsonPojo;

/**
 * @author 月汐
 * @date 2019/3/11 11:48
 */
public class AuditUserListBO extends JsonPojo {
    /**
     * 用户ID
     */
    private long       id;

    /**
     * 用户名称
     */
    private String     name;

    /**
     * 账号
     */
    private String     account;

    /**
     * 注册时间
     */
    private String     registerTime;

    /**
     * 联系人
     */
    private String     contact;

    /**
     * 账号类型
     */
    private String     accountType;

    /**
     * 邮箱
     */
    private String     email;

    /**
     * 证书照片
     */
    private String     certificatePhoto;

    /**
     * 银行账号信息
     */
    private String     bankAccountInfo;

    /**
     * 审核状态
     */
    private int        auditStatus;

    /**
     * 审核状态描述
     */
    private String     auditStatusDesc;

    /**
     * 邀请码
     */
    private String     invitationCode;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCertificatePhoto() {
        return certificatePhoto;
    }

    public void setCertificatePhoto(String certificatePhoto) {
        this.certificatePhoto = certificatePhoto;
    }

    public String getBankAccountInfo() {
        return bankAccountInfo;
    }

    public void setBankAccountInfo(String bankAccountInfo) {
        this.bankAccountInfo = bankAccountInfo;
    }

    public int getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(int auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAuditStatusDesc() {
        return auditStatusDesc;
    }

    public void setAuditStatusDesc(String auditStatusDesc) {
        this.auditStatusDesc = auditStatusDesc;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }
}
