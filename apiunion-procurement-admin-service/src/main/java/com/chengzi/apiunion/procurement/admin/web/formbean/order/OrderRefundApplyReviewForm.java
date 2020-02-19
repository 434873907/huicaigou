package com.chengzi.apiunion.procurement.admin.web.formbean.order;

import com.chengzi.common.web.formbean.BaseForm;

/**
 * <p>
 * </p>
 * User: 摩天
 * Date: 2019-01-29 17:56
 */
public class OrderRefundApplyReviewForm extends BaseForm {

    /**
     * 用户id
     */
    private long userId;
    /**
     * 退款记录id
     */
    private long orderRefundId;

    /**
     * 审核 1通过/0拒绝
     */
    private int  isPass;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getIsPass() {
        return isPass;
    }

    public void setIsPass(int isPass) {
        this.isPass = isPass;
    }

    public long getOrderRefundId() {
        return orderRefundId;
    }

    public void setOrderRefundId(long orderRefundId) {
        this.orderRefundId = orderRefundId;
    }
}
