/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.formbean.template;

import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;
import com.chengzi.common.web.formbean.BaseForm;

import java.util.List;
import java.util.Set;

/**
 * @author zhiyuan
 */
public class BrandLibraryForm extends BaseForm {
    /**
     * 品牌ID
     */
    private Long brandId;
    /**
     * 品牌中文
     */
    @NotBlankAndNull(message = "品牌中文未填写")
    private String nameZh;

    /**
     * 品牌英文
     */
    @NotBlankAndNull(message = "品牌英文未填写")
    private String nameEn;

    /**
     * 品牌别名(用逗号分隔)
     */
    private List<String> alias;

    /**
     * 品牌国家
     */
    private int countryCode;


    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getNameZh() {
        return nameZh;
    }

    public void setNameZh(String nameZh) {
        this.nameZh = nameZh;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }


    public List<String> getAlias() {
        return alias;
    }

    public void setAlias(List<String> alias) {
        this.alias = alias;
    }

    public int getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(int countryCode) {
        this.countryCode = countryCode;
    }
}
