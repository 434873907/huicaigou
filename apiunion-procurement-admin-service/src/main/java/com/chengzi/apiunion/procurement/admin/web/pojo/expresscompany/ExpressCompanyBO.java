package com.chengzi.apiunion.procurement.admin.web.pojo.expresscompany;

import com.chengzi.apiunion.expresscompany.enums.ExpressCompanyTypeEnum;
import com.chengzi.common.data.beans.JsonPojo;

/**
 * @author 行者
 */
public class ExpressCompanyBO extends JsonPojo {

    private long id;

    /**
     * 物流公司名字
     */
    private String  companyName;

    private String  icon;

    /**
     * 官网地址
     */
    private String officialUrl;

    /**
     * 物流公司类型
     * @see ExpressCompanyTypeEnum
     */
    private int    type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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
}
