package com.chengzi.apiunion.procurement.admin.web.formbean.order;

import com.chengzi.common.web.formbean.BaseForm;

/**
 * <p>
 * </p>
 * User: 摩天
 * Date: 2019-01-31 10:43
 */
public class OrderRefundApplyCheckForm extends BaseForm {
    /**
     * 用户id
     */
    private long   userId;
    /**
     * 退款记录id
     */
    private String   orderNum;

    /**
     * 审核 1通过/0拒绝
     */
    private int    isPass;

    /**
     * 备注
     */
    private String remark;

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

    public int getIsPass() {
        return isPass;
    }

    public void setIsPass(int isPass) {
        this.isPass = isPass;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
