package com.chengzi.apiunion.procurement.admin.web.formbean.order;

import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;
import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;

/**
 * <p>
 * </p>
 * User: 摩天
 * Date: 2018-10-30 11:06
 */
public class OrderInfoForm extends BaseForm {
    /**
     * 用户Id
     */
    @NotBlankAndNull(message = "用户Id不能为空")
    protected Long userId;

    /**
     * 订单编号
     */
    @NotBlankAndNull(message = "订单编号不能为空")
    protected String orderNum;

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
