package com.chengzi.apiunion.procurement.admin.web.formbean.order;

import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;
import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;

/**
 * @author 月汐
 * @date 2019/1/14 15:23
 */
public class RefuseOrderForm extends BaseForm {

    @NotBlankAndNull(message = "请输入订单号")
    private String orderNum;

    @Min(value = 1, message = "请输入正确的用户编号")
    private long userId;

    @NotBlankAndNull(message = "请输入操作员名字")
    private String operator;

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

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
