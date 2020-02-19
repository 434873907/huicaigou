package com.chengzi.apiunion.procurement.admin.web.formbean.partneruser;

import com.chengzi.common.web.formbean.BasePageForm;

/**
 * @author 月汐
 * @date 2018/10/18 14:12
 */
public class QueryPartnerUserListForm extends BasePageForm {

    private String nickName;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
