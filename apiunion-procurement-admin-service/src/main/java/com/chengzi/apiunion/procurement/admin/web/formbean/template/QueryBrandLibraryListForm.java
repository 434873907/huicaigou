package com.chengzi.apiunion.procurement.admin.web.formbean.template;

import com.chengzi.apiunion.brand.pojo.search.query.BrandSearchQuery;
import com.chengzi.apiunion.supplier.common.template.pojo.search.query.SupplierBrandSearchQuery;
import com.chengzi.common.web.formbean.BasePageForm;

import java.util.Set;

/**
 * @author zhiyuan
 * 模板品牌查询：供应商类型，供应商名字 查询
 */
public class QueryBrandLibraryListForm extends BasePageForm {

    /**
     * 品牌名称(中文，英文)
     */
    private String brandName;

    /**
     * 所属国家编码
     */
    private Integer countryCode;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Integer getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(Integer countryCode) {
        this.countryCode = countryCode;
    }


    public SupplierBrandSearchQuery buildQuery() {
        SupplierBrandSearchQuery query = new SupplierBrandSearchQuery();

        query.setLetter(brandName);
        query.setName(brandName);
        query.setCountryCode(countryCode);
        query.setFrom(getOffset());
        query.setSize(getLimit());

        return query;
    }
}
