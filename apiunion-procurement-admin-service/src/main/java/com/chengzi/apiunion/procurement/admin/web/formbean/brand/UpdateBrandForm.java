package com.chengzi.apiunion.procurement.admin.web.formbean.brand;

import com.chengzi.apiunion.brand.pojo.BrandDO;
import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;
import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.NotNull;

/**
 * @author 月汐
 * @date 2018/10/16 10:07
 */
public class UpdateBrandForm extends BaseForm {

    @Min(value = 1, message = "请选择正确的品牌")
    private long id;

    private String name;

    private String originalName;

    /**
     * logo链接地址
     */
    private String logoUrl;

    /**
     * 海报链接
     */
    private String posterUrl;

    /**
     * 描述
     */
    private String desc;

    /**
     * 热门状态
     */
    private Integer propStatus = 2;

    /**
     * 关键词
     */
    private String keywords;

    /**
     * 启用状态
     */
    @NotNull(message = "请选择正确的状态")
    private Integer status;

    /**
     * 品牌别名
     */
    private String alias;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public Integer getPropStatus() {
        return propStatus;
    }

    public void setPropStatus(Integer propStatus) {
        this.propStatus = propStatus;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
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

    public static BrandDO convert(UpdateBrandForm form) {
        BrandDO brandDO = new BrandDO();
        brandDO.setId(form.getId());
        brandDO.setName(form.getName()!=null ? form.getName().trim(): null);
        brandDO.setOriginalName(form.getOriginalName());
        brandDO.setLogoUrl(form.getLogoUrl());
        brandDO.setPosterUrl(form.getPosterUrl());
        brandDO.setDesc(form.getDesc());
        brandDO.setPropStatus(form.getPropStatus());
        brandDO.setStatus(form.getStatus());
        brandDO.setAlias(form.getAlias());
        return brandDO;
    }

}
