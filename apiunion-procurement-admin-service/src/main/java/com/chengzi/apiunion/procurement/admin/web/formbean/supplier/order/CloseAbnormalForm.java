package com.chengzi.apiunion.procurement.admin.web.formbean.supplier.order;

import com.chengzi.common.data.validate.oval.annonation.NotEmptyAndNull;
import com.chengzi.common.web.formbean.BaseForm;

import net.sf.oval.constraint.Min;

/**
 * @author 随风
 * @create 2020-01-15 20:13
 **/
public class CloseAbnormalForm extends BaseForm {

    @NotEmptyAndNull(message = "订单号不能为空")
    private String          orderNum;

    @Min(value = 1, message = "ID不能为空")
    private long            id;

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
