package com.chengzi.apiunion.procurement.admin.web.formbean.express;

import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;
import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;

/**
 * @author 月汐
 * @date 2019/4/1 16:12
 */
public class UpdateOrderExpressForm extends BaseForm {

    @Min(value = 1, message = "请输入有效的物流ID")
    private long id;

    @Min(value = 1, message = "请输入有效的用户ID")
    private long userId;

    @NotBlankAndNull(message = "物流公司不能为空")
    private Long expressCompany;

    private String trackingNum;

    @NotBlankAndNull(message = "物流单号不能为空")
    private String expressNum;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Long getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(Long expressCompany) {
        this.expressCompany = expressCompany;
    }

    public String getTrackingNum() {
        return trackingNum;
    }

    public void setTrackingNum(String trackingNum) {
        this.trackingNum = trackingNum;
    }

    public String getExpressNum() {
        return expressNum;
    }

    public void setExpressNum(String expressNum) {
        this.expressNum = expressNum;
    }
}
