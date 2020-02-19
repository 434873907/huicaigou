package com.chengzi.apiunion.procurement.admin.web.controllers.run.form;

import com.chengzi.common.web.formbean.BaseForm;

import java.math.BigDecimal;

public class OrderRefundRunForm extends BaseForm {


    private String orderNum;

    private long userId;

    private BigDecimal refundAmount;

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }
}
