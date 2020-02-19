package com.chengzi.apiunion.procurement.admin.web.pojo.order;

import com.chengzi.common.data.beans.JsonPojo;

import java.util.Date;

/**
 * 订单操作信息
 */
public class OrderOperateListBO extends JsonPojo {

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 操作记录
     */
    private String remark;
    /**
     * 来源描述
     */
    private String sourceDesc;
    /**
     * 操作人
     */
    private String operator;


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSourceDesc() {
        return sourceDesc;
    }

    public void setSourceDesc(String sourceDesc) {
        this.sourceDesc = sourceDesc;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
