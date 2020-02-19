package com.chengzi.apiunion.procurement.admin.web.formbean.order;

import com.chengzi.common.web.formbean.BaseForm;

/**
 * 同步物流信息请求
 */
public class SyncOrderExpressForm extends BaseForm {

    private Long userId;

    private String orderNum;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }
}
