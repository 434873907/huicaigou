package com.chengzi.apiunion.procurement.admin.web.pojo.template;

import com.chengzi.common.data.beans.JsonPojo;

import java.util.List;

/**
 * admin端模板品牌列表Bean
 */
public class TemplateCategoryBO extends JsonPojo {


    private Long id;
    /**
     * 类目ID
     */
    private Long cateId;
    /**
     * 类目名称
     */
    private String cateName;

    /**
     * 类目描述
     */
    private String cateDesc;

    /**
     * 类目属性
     */
    private List<String> cateSkuNames;
    /**
     * 父类目ID
     */
    private long parentId;

    private Integer type;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getCateId() {
        return cateId;
    }

    public void setCateId(Long cateId) {
        this.cateId = cateId;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String getCateDesc() {
        return cateDesc;
    }

    public void setCateDesc(String cateDesc) {
        this.cateDesc = cateDesc;
    }

    public List<String> getCateSkuNames() {
        return cateSkuNames;
    }

    public void setCateSkuNames(List<String> cateSkuNames) {
        this.cateSkuNames = cateSkuNames;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
