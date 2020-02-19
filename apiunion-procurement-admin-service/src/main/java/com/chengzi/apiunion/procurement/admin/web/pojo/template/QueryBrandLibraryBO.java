package com.chengzi.apiunion.procurement.admin.web.pojo.template;

import com.chengzi.common.data.beans.JsonPojo;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * admin端模板品牌列表Bean
 */
public class QueryBrandLibraryBO extends JsonPojo {


    private long id;
    /**
     * 品牌中文
     */
    private String nameZh;

    /**
     * 品牌英文
     */
    private String nameEn;

    /**
     * 品牌别名(用逗号分隔)
     */
    private List<String> alias;

    /**
     * 品牌国家
     */
    private int countryCode;
    private String countryName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
