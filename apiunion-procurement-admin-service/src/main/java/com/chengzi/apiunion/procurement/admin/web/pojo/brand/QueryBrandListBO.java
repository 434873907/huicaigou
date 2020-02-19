package com.chengzi.apiunion.procurement.admin.web.pojo.brand;

import com.chengzi.apiunion.brand.pojo.BrandDO;
import com.chengzi.apiunion.brand.pojo.search.BrandSearchBO;
import com.chengzi.apiunion.common.data.enums.StatusEnum;
import com.chengzi.apiunion.common.module.image.annotation.ImageDecorater;
import com.chengzi.apiunion.common.module.image.enums.ImageBizType;
import com.chengzi.common.data.beans.JsonPojo;

/**
 * @author 月汐
 * @date 2018/10/15 15:13
 */
public class QueryBrandListBO extends JsonPojo {

    private Long    id;

    private String  name;

    private String  originalName;

    private Integer onShelfNum;

    private Integer offShelfNum;

    private String  desc;

    private Integer status;

    private String  alias;
    /**
     * logo链接地址
     */
    @ImageDecorater(ImageBizType.ADMIN_BRAND_LOGO)
    private String  logoUrl;

    /**
     * 是否热门
     */
    private Integer propStatus;

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

    public Integer getOnShelfNum() {
        return onShelfNum;
    }

    public void setOnShelfNum(Integer onShelfNum) {
        this.onShelfNum = onShelfNum;
    }

    public Integer getOffShelfNum() {
        return offShelfNum;
    }

    public void setOffShelfNum(Integer offShelfNum) {
        this.offShelfNum = offShelfNum;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public Integer getPropStatus() {
        return propStatus;
    }

    public void setPropStatus(Integer propStatus) {
        this.propStatus = propStatus;
    }

    public static QueryBrandListBO convert(BrandDO brandDO, int onShelfNum, int offShelfNum) {
        QueryBrandListBO bo = new QueryBrandListBO();
        bo.setId(brandDO.getId());
        bo.setName(brandDO.getName());
        bo.setOriginalName(brandDO.getOriginalName());
        bo.setStatus(brandDO.getStatus());
        bo.setDesc(brandDO.getDesc());
        bo.setOnShelfNum(onShelfNum);
        bo.setOffShelfNum(offShelfNum);
        bo.setAlias(brandDO.getAlias());
        bo.setLogoUrl(brandDO.getLogoUrl());
        bo.setPropStatus(brandDO.getPropStatus() == null ? StatusEnum.TWO.getCode() : brandDO.getPropStatus());
        return bo;
    }

}
