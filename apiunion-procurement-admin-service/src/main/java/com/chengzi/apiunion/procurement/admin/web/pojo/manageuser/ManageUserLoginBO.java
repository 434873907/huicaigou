package com.chengzi.apiunion.procurement.admin.web.pojo.manageuser;

import com.chengzi.apiunion.procurement.admin.manageuser.pojo.ManageUserDO;
import com.chengzi.common.data.beans.JsonPojo;

/**
 * @author 月汐
 * @date 2018/10/17 17:02
 */
public class ManageUserLoginBO extends JsonPojo {

    private Long id;

    private String account;

    private String nickName;

    private String logoUrl;

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

    public static ManageUserLoginBO convert(ManageUserDO manageUserDO) {
        ManageUserLoginBO bo = new ManageUserLoginBO();
        bo.setId(manageUserDO.getId());
        bo.setAccount(manageUserDO.getAccount());
        bo.setNickName(manageUserDO.getNickName());
        bo.setLogoUrl(manageUserDO.getLogoUrl());
        return bo;
    }
}
