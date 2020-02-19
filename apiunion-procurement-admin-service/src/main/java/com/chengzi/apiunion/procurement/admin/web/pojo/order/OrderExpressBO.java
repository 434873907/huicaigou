package com.chengzi.apiunion.procurement.admin.web.pojo.order;

import com.chengzi.common.data.beans.JsonPojo;

import java.util.List;

/**
 * <p>
 * </p>
 * User: 摩天
 * Date: 2018-10-30 20:50
 */
public class OrderExpressBO extends JsonPojo {

    /**
     * 物流id
     */
    private long         expressId;

    /**
     * 物流公司
     */
    private String       company;

    /**
     * 物流单号
     */
    private String       expressNum;

    /**
     * 物流详情
     */
    private List<String> expressDetail;

    /**
     * 物流名称
     */
    private String       expressName;

    /**
     * 物流公司ID
     */
    private long         companyId;

    public long getExpressId() {
        return expressId;
    }

    public void setExpressId(long expressId) {
        this.expressId = expressId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getExpressNum() {
        return expressNum;
    }

    public void setExpressNum(String expressNum) {
        this.expressNum = expressNum;
    }

    public List<String> getExpressDetail() {
        return expressDetail;
    }

    public void setExpressDetail(List<String> expressDetail) {
        this.expressDetail = expressDetail;
    }

    public String getExpressName() {
        return expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }
}
