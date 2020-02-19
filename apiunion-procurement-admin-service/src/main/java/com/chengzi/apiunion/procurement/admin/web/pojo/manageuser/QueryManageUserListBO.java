package com.chengzi.apiunion.procurement.admin.web.pojo.manageuser;

import com.chengzi.apiunion.procurement.admin.manageuser.pojo.ManageUserDO;

import java.io.Serializable;

/**
 * @author 月汐
 * @date 2018/10/18 10:01
 */
public class QueryManageUserListBO implements Serializable {

    private Long id;

    private String nickName;

    private String account;

    private String logoUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public static QueryManageUserListBO convert(ManageUserDO manageUserDO) {
        QueryManageUserListBO bo = new QueryManageUserListBO();
        bo.setId(manageUserDO.getId());
        bo.setAccount(manageUserDO.getAccount());
        bo.setNickName(manageUserDO.getNickName());
        bo.setLogoUrl(manageUserDO.getLogoUrl());
        return bo;
    }
}
