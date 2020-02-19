package com.chengzi.apiunion.procurement.admin.manageuser.pojo;

import com.chengzi.apiunion.common.data.beans.RouteOperate;

/**
 * @author 月汐
 * @date 2018/10/18 11:41
 */
public class ManageUserUpdateOperate extends RouteOperate {

    private Long id;

    private String nickName;

    private String logoUrl;

    private String password;

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

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
