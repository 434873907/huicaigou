package com.chengzi.apiunion.procurement.admin.web.formbean.invitationcode;

import com.chengzi.apiunion.invitationcode.enums.InvitationCodeMatchTypeEnum;
import com.chengzi.apiunion.invitationcode.enums.InvitationCodeTypeEnum;
import com.chengzi.common.web.formbean.BasePageForm;

/**
 * @author 月汐
 * @date 2019/12/06 11:31
 */
public class QueryInvitationCodeForm extends BasePageForm {

    private String                      keyword;

    private InvitationCodeTypeEnum      type;

    private InvitationCodeMatchTypeEnum matchType;

    private Integer                     status;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public InvitationCodeTypeEnum getType() {
        return type;
    }

    public void setType(InvitationCodeTypeEnum type) {
        this.type = type;
    }

    public InvitationCodeMatchTypeEnum getMatchType() {
        return matchType;
    }

    public void setMatchType(InvitationCodeMatchTypeEnum matchType) {
        this.matchType = matchType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
