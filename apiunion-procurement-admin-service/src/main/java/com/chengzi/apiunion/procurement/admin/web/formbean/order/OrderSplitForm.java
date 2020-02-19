package com.chengzi.apiunion.procurement.admin.web.formbean.order;

import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;
import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;

/**
 * @author 月汐
 * @date 2018/12/10 12:13
 */
public class OrderSplitForm extends BaseForm {

    @Min(value = 1, message = "请输入正确的订单ID")
    private long id;

    private String pkgNo;

    @Min(value = 1, message = "请填写正确的数量")
    private int num;

    @Min(value = 1, message = "请输入正确的订单创建者")
    private long orderUserId;

    @NotBlankAndNull(message = "请输入订单号")
    private String orderNum;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPkgNo() {
        return pkgNo;
    }

    public void setPkgNo(String pkgNo) {
        this.pkgNo = pkgNo;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public long getOrderUserId() {
        return orderUserId;
    }

    public void setOrderUserId(long orderUserId) {
        this.orderUserId = orderUserId;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }
}
