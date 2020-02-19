package com.chengzi.apiunion.procurement.admin.manageuser.pojo;

import com.chengzi.apiunion.common.data.beans.RouteQuery;

/**
 * @author 月汐
 * @date 2018/10/18 9:44
 */
public class ManageUserQuery extends RouteQuery {

    private String nickName;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
