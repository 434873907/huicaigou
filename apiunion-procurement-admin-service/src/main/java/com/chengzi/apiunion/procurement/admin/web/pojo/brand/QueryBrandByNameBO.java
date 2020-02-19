package com.chengzi.apiunion.procurement.admin.web.pojo.brand;

import com.chengzi.apiunion.brand.pojo.BrandDO;
import com.chengzi.apiunion.brand.pojo.search.BrandSearchBO;
import com.chengzi.common.data.beans.JsonPojo;

/**
 * @author 月汐
 * @date 2018/10/25 19:25
 */
public class QueryBrandByNameBO extends JsonPojo {

    private Long id;

    /**
     * 品牌名
     */
    private String name;

    /**
     * 品牌英文名
     */
    private String originalName;

    /**
     * logo链接地址
     */
    private String logoUrl;

    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static QueryBrandByNameBO convert(BrandDO brand) {
        QueryBrandByNameBO bo = new QueryBrandByNameBO();
        bo.setId(brand.getId());
        bo.setName(brand.getName());
        bo.setOriginalName(brand.getOriginalName());
        bo.setLogoUrl(brand.getLogoUrl());
        if (brand.getStatus() == 0) {
            bo.setStatus("已禁用");
        }
        return bo;
    }
}
