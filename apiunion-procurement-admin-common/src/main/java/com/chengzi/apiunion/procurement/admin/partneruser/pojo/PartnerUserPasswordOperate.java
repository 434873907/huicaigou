package com.chengzi.apiunion.procurement.admin.partneruser.pojo;

import com.chengzi.apiunion.common.data.beans.RouteOperate;

/**
 * @author 月汐
 * @date 2018/10/18 16:21
 */
public class PartnerUserPasswordOperate extends RouteOperate {

    private Long id;

    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
