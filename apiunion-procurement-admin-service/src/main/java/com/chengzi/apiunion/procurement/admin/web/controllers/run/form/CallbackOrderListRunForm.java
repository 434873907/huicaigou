package com.chengzi.apiunion.procurement.admin.web.controllers.run.form;

import com.chengzi.common.web.formbean.BaseForm;

import java.util.List;

/**
 * 推送回调参数
 * 重新推送订单的状态，和物流信息
 */
public class CallbackOrderListRunForm extends BaseForm {


//    private String orderNum;
    private List<String> orderNums;

    private long userId;


//    public String getOrderNum() {
//        return orderNum;
//    }

//    public void setOrderNum(String orderNum) {
//        this.orderNum = orderNum;
//    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public List<String> getOrderNums() {
        return orderNums;
    }

    public void setOrderNums(List<String> orderNums) {
        this.orderNums = orderNums;
    }
}
