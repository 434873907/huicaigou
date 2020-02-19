package com.chengzi.apiunion.procurement.admin.web.formbean.expresscompany;

import com.chengzi.apiunion.expresscompany.enums.ExpressCompanyTypeEnum;
import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.MaxLength;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.NotNull;

public class AddExpressCompanyForm extends BaseForm {

    /**
     * 物流公司名称
     */
    @MaxLength(value = 10, message = "物流公司名称过长")
    @NotNull(message = "物流公司名称不能为空")
    private String companyName;

    /**
     * 官网地址
     */
    private String officialUrl;

    /**
     * 物流公司类型
     * @see ExpressCompanyTypeEnum
     */
    @Min(value = 1)
    private int    type;

    /**
     * 图标
     */
    private String icon;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getOfficialUrl() {
        return officialUrl;
    }

    public void setOfficialUrl(String officialUrl) {
        this.officialUrl = officialUrl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
