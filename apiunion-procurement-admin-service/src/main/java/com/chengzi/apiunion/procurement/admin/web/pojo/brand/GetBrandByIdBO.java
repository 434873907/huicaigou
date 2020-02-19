package com.chengzi.apiunion.procurement.admin.web.pojo.brand;

import com.chengzi.apiunion.brand.pojo.BrandDO;
import com.chengzi.apiunion.common.module.image.annotation.ImageDecorater;
import com.chengzi.apiunion.common.module.image.enums.ImageBizType;
import com.chengzi.apiunion.common.module.image.util.ImageUtil;
import com.chengzi.common.data.beans.JsonPojo;

/**
 * @author 月汐
 * @date 2018/10/15 17:04
 */
public class GetBrandByIdBO extends JsonPojo {

    private Long    id;

    /**
     * 品牌名
     */
    private String  name;

    /**
     * 品牌英文名
     */
    private String  originalName;

    /**
     * logo链接地址
     */
    @ImageDecorater(ImageBizType.ADMIN_BRAND_LOGO)
    private String  logoUrl;

    /**
     * 海报链接
     */
    @ImageDecorater(ImageBizType.ADMIN_BRAND_POSTER)
    private String  posterUrl;

    /**
     * 品牌描述
     */
    private String  desc;

    private Integer status;

    /**
     * 品牌别名
     */
    private String  alias;

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

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
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

    public static GetBrandByIdBO convert(BrandDO brandDO) {
        GetBrandByIdBO bo = new GetBrandByIdBO();
        bo.setId(brandDO.getId());
        bo.setName(brandDO.getName());
        bo.setOriginalName(brandDO.getOriginalName());
        bo.setLogoUrl(brandDO.getLogoUrl());
        bo.setPosterUrl(brandDO.getPosterUrl());
        bo.setDesc(brandDO.getDesc());
        bo.setStatus(brandDO.getStatus());
        bo.setAlias(brandDO.getAlias());
        return bo;
    }

}
