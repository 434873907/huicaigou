package com.chengzi.apiunion.procurement.admin.web.pojo.order;

import com.chengzi.common.data.beans.JsonPojo;

/**
 * @author 月汐
 * @date 2019/1/4 14:14
 */
public class GetPayPlatformBO extends JsonPojo {

    private int code;

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
