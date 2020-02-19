package com.chengzi.apiunion.procurement.admin.web.pojo.invitationcode;

import com.chengzi.apiunion.invitationcode.enums.InvitationCodeMatchTypeEnum;
import com.chengzi.apiunion.invitationcode.enums.InvitationCodeTypeEnum;
import com.chengzi.apiunion.invitationcode.pojo.InvitationCodeDO;
import com.chengzi.common.data.beans.JsonPojo;
import com.chengzi.common.util.DateUtil;

/**
 * @author 月汐
 * @date 2019/12/06 12:00
 */
public class InvitationCodeBO extends JsonPojo {

    private long   id;

    private String code;

    private String desc;

    private int    type;

    private String typeDesc;

    private int    matchType;

    private String matchTypeDesc;

    private int    num;

    private int    usedNum;

    private int    status;

    private String createTime;

    private String createUser;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    public int getMatchType() {
        return matchType;
    }

    public void setMatchType(int matchType) {
        this.matchType = matchType;
    }

    public String getMatchTypeDesc() {
        return matchTypeDesc;
    }

    public void setMatchTypeDesc(String matchTypeDesc) {
        this.matchTypeDesc = matchTypeDesc;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getUsedNum() {
        return usedNum;
    }

    public void setUsedNum(int usedNum) {
        this.usedNum = usedNum;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public static InvitationCodeBO convert(InvitationCodeDO invitationCodeDO) {
        InvitationCodeBO bo = new InvitationCodeBO();
        bo.setId(invitationCodeDO.getId());
        bo.setCode(invitationCodeDO.getCode());
        bo.setDesc(invitationCodeDO.getDesc());
        bo.setNum(invitationCodeDO.getNum());
        bo.setUsedNum(invitationCodeDO.getUsedNum());

        InvitationCodeTypeEnum type = InvitationCodeTypeEnum.parse(invitationCodeDO.getType());
        bo.setType(type.getCode());
        bo.setTypeDesc(type.getRemark());

        InvitationCodeMatchTypeEnum matchType = InvitationCodeMatchTypeEnum.parse(invitationCodeDO.getMatchType());
        bo.setMatchType(matchType.getCode());
        bo.setMatchTypeDesc(matchType.getRemark());

        bo.setStatus(invitationCodeDO.getStatus());
        bo.setCreateTime(DateUtil.formatDate(invitationCodeDO.getCreateTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
        bo.setCreateUser(invitationCodeDO.getUserName());

        return bo;
    }
}
