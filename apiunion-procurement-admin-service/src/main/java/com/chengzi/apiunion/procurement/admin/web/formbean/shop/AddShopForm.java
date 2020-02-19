package com.chengzi.apiunion.procurement.admin.web.formbean.shop;

import com.chengzi.apiunion.common.data.enums.CurrencyEnum;
import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;
import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.MaxLength;
import net.sf.oval.constraint.NotNull;

/**
 * @author 月汐
 * @date 2018/11/15 15:56
 */
public class AddShopForm extends BaseForm {

    @NotBlankAndNull(message = "店铺名不能为空")
    @MaxLength(value = 255, message = "店铺名不能超过255字符")
    private String name;

    private String logoUrl;

    @MaxLength(value = 1024, message = "店铺描述不能超过1024字符")
    private String desc;

    /**
     * 是否自营
     */
    private Integer isSelf;

    /**
     * 预计发货日期
     */
    @NotBlankAndNull(message = "发货日期不能为空")
    private String deliverDays;

    /**
     * 预计到货日期
     */
    @NotBlankAndNull(message = "到货日期不能为空")
    private String arrivalDays;

    @NotNull(message = "货币未选择")
    private CurrencyEnum currency;

    private Integer status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getIsSelf() {
        return isSelf;
    }

    public void setIsSelf(Integer isSelf) {
        this.isSelf = isSelf;
    }

    public String getDeliverDays() {
        return deliverDays;
    }

    public void setDeliverDays(String deliverDays) {
        this.deliverDays = deliverDays;
    }

    public String getArrivalDays() {
        return arrivalDays;
    }

    public void setArrivalDays(String arrivalDays) {
        this.arrivalDays = arrivalDays;
    }

    public CurrencyEnum getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyEnum currency) {
        this.currency = currency;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
