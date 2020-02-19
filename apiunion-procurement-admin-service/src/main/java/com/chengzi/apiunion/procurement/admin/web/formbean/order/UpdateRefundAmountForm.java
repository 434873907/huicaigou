package com.chengzi.apiunion.procurement.admin.web.formbean.order;

import com.chengzi.common.web.formbean.BaseForm;

/**
 * <p>
 * </p>
 * User: 摩天
 * Date: 2019-03-18 23:46
 */
public class UpdateRefundAmountForm extends BaseForm {
    /**
     * refundItemId
     */
    private long   id;

    private double amount;

    private long   userId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
