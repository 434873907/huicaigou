package com.chengzi.apiunion.procurement.admin.web.controllers.run.form;

import com.chengzi.common.web.formbean.BaseForm;

public class CompleteOrderRunForm extends BaseForm {


    private String orderNum;

    private long userId;


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
}
