package com.chengzi.apiunion.procurement.admin.web.formbean.activity;

import com.chengzi.common.web.formbean.BasePageForm;

/** 
* @author 苏子 
* @date 2018年10月19日
*/
public class ActivityListForm extends BasePageForm {

    /**
     * 活动类型 {@link ActTypeEnum}
     */
    private int    actType;

    /**
     * 状态 0,全部；1，未开始；2，进行中；3，已结束；
     */
    private int    status;

    /**
     * 关键字
     */
    private String keyword;

    /**
     * 显示端
     * {@link DisplayTerminalEnum}
     */
    private int    displayTerminal;

    public int getActType() {
        return actType;
    }

    public void setActType(int actType) {
        this.actType = actType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getDisplayTerminal() {
        return displayTerminal;
    }

    public void setDisplayTerminal(int displayTerminal) {
        this.displayTerminal = displayTerminal;
    }


}
