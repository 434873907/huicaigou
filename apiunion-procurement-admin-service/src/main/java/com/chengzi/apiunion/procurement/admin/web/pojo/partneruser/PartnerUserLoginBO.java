package com.chengzi.apiunion.procurement.admin.web.pojo.partneruser;

import java.util.List;

import com.chengzi.apiunion.common.data.cipher.token.TokenGenerator;
import com.chengzi.apiunion.procurement.admin.partneruser.pojo.PartnerUserDO;
import com.chengzi.apiunion.procurement.role.partnerrole.pojo.PartnerRoleDO;
import com.chengzi.common.data.beans.JsonPojo;
import com.chengzi.common.data.enums.PlatformEnum;
import com.chengzi.common.util.CollectionUtil;

/**
 * @author 月汐
 * @date 2018/10/18 15:37
 */
public class PartnerUserLoginBO extends JsonPojo {

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

    private Long roleId;

    private String roleName;

    /**
     * 令牌
     */
    private String token;

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

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static PartnerUserLoginBO convert(PartnerUserDO partnerUserDO, List<PartnerRoleDO> roleDOS) {
        PartnerUserLoginBO bo = new PartnerUserLoginBO();
        bo.setId(partnerUserDO.getId());
        bo.setAccount(partnerUserDO.getAccount());
        bo.setNickName(partnerUserDO.getNickName());
        bo.setLogoUrl(partnerUserDO.getLogoUrl());
        bo.setEmail(partnerUserDO.getEmail());
        bo.setPhone(partnerUserDO.getPhone());
        bo.setContacts(partnerUserDO.getContacts());
        if (CollectionUtil.isNotEmpty(roleDOS)) {
            bo.setRoleId(roleDOS.get(0).getId());
            bo.setRoleName(roleDOS.get(0).getRoleName());
        }
        bo.setToken(TokenGenerator.genLoginToken(partnerUserDO.getId(), (byte) 2, PlatformEnum.PROCUREMENT_ADMIN));
        return bo;
    }

}
