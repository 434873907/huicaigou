package com.chengzi.apiunion.procurement.admin.web.formbean.supplier.order;

import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;
import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;

/**
 * 查询包裹物流
 */
public class OrderPkgExperssForm extends BaseForm {

    @Min(value = 1, message = "订单ID不能为空")
    private long id;
    @NotBlankAndNull(message = "包裹号不能为空")
    private String pkgNo;

    //供应商订单
    @NotBlankAndNull(message = "供应商订单号不能为空")
    private String orderNum;

    //会订货端的订单( 会订货端订单项的包裹号)
//    @NotBlankAndNull(message = "用户订单号不能为空")
    private String userOrderNum;

    public String getPkgNo() {
        return pkgNo;
    }

    public void setPkgNo(String pkgNo) {
        this.pkgNo = pkgNo;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getUserOrderNum() {
        return userOrderNum;
    }

    public void setUserOrderNum(String userOrderNum) {
        this.userOrderNum = userOrderNum;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
