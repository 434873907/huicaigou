package com.chengzi.apiunion.procurement.admin.web.formbean.supplier.order;

import com.chengzi.common.data.validate.oval.annonation.NotEmptyAndNull;
import com.chengzi.common.web.formbean.BaseForm;

/**
 * @author 随风
 * @create 2020-01-10 16:29
 **/
public class GetSupplierOrderDetailForm extends BaseForm {

    @NotEmptyAndNull(message = "订单号不能为空")
    private String  orderNum;

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }
}
