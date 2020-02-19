package com.chengzi.apiunion.procurement.admin.web.formbean.express;

import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;
import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;

/**
 * 同步订单的物流
 */
public class SyncOrderExpressForm extends BaseForm {

    @Min(value = 1, message = "请输入有效的用户ID")
    private long userId;

    /**
     * 订单号
     */
    @NotBlankAndNull(message = "订单号不能为空")
    private String orderNum;

    /**
     * 包裹号
     */
//    @NotBlankAndNull(message = "包裹号不能为空")
//    private String pkgNo;

    /**
     * 物流段号
     */
//    @Min(value = 1, message = "请输入正确的物流段号")
//    private int type;

//    private Long parentId;
//
//    @NotBlankAndNull(message = "物流公司不能为空")
//    private Long expressCompany;
//
//    @NotBlankAndNull(message = "物流单号不能为空")
//    private String expressNum;
//
//    private String trackingNum;
//
//    /**
//     * 真实物流公司ID
//     */
//    private String   realExpressCompanyName;
//
//    /**
//     * 真实物流单号
//     */
//    private String realExpressNum;

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
