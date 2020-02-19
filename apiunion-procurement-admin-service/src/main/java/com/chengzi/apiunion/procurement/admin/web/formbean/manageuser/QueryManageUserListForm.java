package com.chengzi.apiunion.procurement.admin.web.formbean.manageuser;

import com.chengzi.common.web.formbean.BasePageForm;

/**
 * @author 月汐
 * @date 2018/10/18 9:59
 */
public class QueryManageUserListForm extends BasePageForm {

    private String nickName;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
