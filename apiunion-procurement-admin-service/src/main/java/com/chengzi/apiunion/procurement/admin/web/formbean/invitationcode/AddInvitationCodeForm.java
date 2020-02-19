package com.chengzi.apiunion.procurement.admin.web.formbean.invitationcode;

import com.chengzi.apiunion.invitationcode.enums.InvitationCodeMatchTypeEnum;
import com.chengzi.apiunion.invitationcode.enums.InvitationCodeTypeEnum;
import com.chengzi.common.web.formbean.BaseForm;

/**
 * @author 月汐
 * @date 2019/12/06 11:02
 */
public class AddInvitationCodeForm extends BaseForm {

    private InvitationCodeTypeEnum      type;

    private InvitationCodeMatchTypeEnum matchType;

    private int                         availableNum;

    private int                         generateNum;

    private int                         status;

    private String                      desc;

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

    public int getAvailableNum() {
        return availableNum;
    }

    public void setAvailableNum(int availableNum) {
        this.availableNum = availableNum;
    }

    public int getGenerateNum() {
        return generateNum;
    }

    public void setGenerateNum(int generateNum) {
        this.generateNum = generateNum;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
