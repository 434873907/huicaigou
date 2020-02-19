package com.chengzi.apiunion.procurement.admin.web.formbean.order;

import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;
import com.chengzi.common.web.formbean.BasePageForm;
import net.sf.oval.constraint.Min;

/**
 * 订单操作记录请求参数
 */
public class OrderOperateListForm extends BasePageForm {


    @Min(value = 0,message = "用户id不能为空")
    private long userId;

    @NotBlankAndNull(message = "订单号不能为空")
    private String orderNum;

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
}
