package com.chengzi.apiunion.procurement.admin.web.formbean.order;

import com.chengzi.common.web.formbean.BaseForm;

/**
 * <p>
 * </p>
 * User: 摩天
 * Date: 2018-11-19 16:06
 */
public class UploadOrderExpressForm extends BaseForm {
    /**
     * 用户id
     */
    private long   userId;

    /**
     * 订单号
     */
    private String orderNum;

    /**
     * 操作类型
     * 0：补充新物流节点
     * 1：覆盖原物流信息
     */
    private int    op;

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

    public int getOp() {
        return op;
    }

    public void setOp(int op) {
        this.op = op;
    }
}
