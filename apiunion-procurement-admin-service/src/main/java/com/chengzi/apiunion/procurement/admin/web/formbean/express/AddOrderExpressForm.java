package com.chengzi.apiunion.procurement.admin.web.formbean.express;

import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;
import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;

/**
 * @author 月汐
 * @date 2018/10/30 11:58
 */
public class AddOrderExpressForm extends BaseForm {

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
    @NotBlankAndNull(message = "包裹号不能为空")
    private String pkgNo;

    /**
     * 物流段号
     */
    @Min(value = 1, message = "请输入正确的物流段号")
    private int type;

    private Long parentId;

    @NotBlankAndNull(message = "物流公司不能为空")
    private Long expressCompany;

    @NotBlankAndNull(message = "物流单号不能为空")
    private String expressNum;

    private String trackingNum;

    /**
     * 真实物流公司ID
     */
    private String   realExpressCompanyName;

    /**
     * 真实物流单号
     */
    private String realExpressNum;

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

    public String getPkgNo() {
        return pkgNo;
    }

    public void setPkgNo(String pkgNo) {
        this.pkgNo = pkgNo;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(Long expressCompany) {
        this.expressCompany = expressCompany;
    }

    public String getExpressNum() {
        return expressNum;
    }

    public void setExpressNum(String expressNum) {
        this.expressNum = expressNum;
    }

    public String getTrackingNum() {
        return trackingNum;
    }

    public void setTrackingNum(String trackingNum) {
        this.trackingNum = trackingNum;
    }

    public String getRealExpressCompanyName() {
        return realExpressCompanyName;
    }

    public void setRealExpressCompanyName(String realExpressCompanyName) {
        this.realExpressCompanyName = realExpressCompanyName;
    }

    public String getRealExpressNum() {
        return realExpressNum;
    }

    public void setRealExpressNum(String realExpressNum) {
        this.realExpressNum = realExpressNum;
    }
}
