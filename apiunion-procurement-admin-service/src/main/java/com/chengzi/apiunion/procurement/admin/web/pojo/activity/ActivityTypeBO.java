package com.chengzi.apiunion.procurement.admin.web.pojo.activity;

import com.chengzi.common.data.beans.JsonPojo;

/**
 * @author 苏子
 * @date 2018年11月13日
 */
public class ActivityTypeBO extends JsonPojo{

    private int    code;
    private String remark;
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
}