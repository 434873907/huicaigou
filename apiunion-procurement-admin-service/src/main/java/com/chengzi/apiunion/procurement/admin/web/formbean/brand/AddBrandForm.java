package com.chengzi.apiunion.procurement.admin.web.formbean.brand;

import com.chengzi.apiunion.brand.pojo.BrandDO;
import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;
import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.NotNull;

/**
 * @author 月汐
 * @date 2018/10/15 15:40
 */
public class AddBrandForm extends BaseForm {

    private String  name;

    private String  originalName;

    /**
     * 品牌logo地址
     */
    private String  logoUrl;

    /**
     * 海报地址
     */
    private String  posterUrl;

    /**
     * 描述
     */
    private String  desc;

    @NotNull(message = "请选择正确的状态")
    private Integer status;

    /**
     * 品牌别名
     */
    private String  alias;

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

    public static BrandDO convert(AddBrandForm form) {
        BrandDO brandDO = new BrandDO();
        brandDO.setName(form.getName());
        brandDO.setOriginalName(form.getOriginalName());
        brandDO.setLogoUrl(form.getLogoUrl());
        brandDO.setPosterUrl(form.getPosterUrl());
        brandDO.setDesc(form.getDesc());
        brandDO.setStatus(form.getStatus());
        brandDO.setAlias(form.getAlias());
        return brandDO;
    }

}
