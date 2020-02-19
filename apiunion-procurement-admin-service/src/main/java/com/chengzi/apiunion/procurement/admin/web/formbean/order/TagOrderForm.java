package com.chengzi.apiunion.procurement.admin.web.formbean.order;

import com.chengzi.common.web.formbean.BaseForm;

/**
 *
 */
public class TagOrderForm extends BaseForm {

    private long userId;

    private String orderNum;

    /**
     * 订单标记状态：0不标记，1标记
     */
    private int tagStatus;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public int getTagStatus() {
        return tagStatus;
    }

    public void setTagStatus(int tagStatus) {
        this.tagStatus = tagStatus;
    }
}
