package com.chengzi.apiunion.procurement.admin.web.formbean.partnercategory;

import com.chengzi.common.web.formbean.BasePageForm;

/**
 * @author 月汐
 * @date 2018/10/12 13:58
 */
public class QueryCategoryListForm extends BasePageForm {

    private Integer depth;

    private Long parentId;

    private String name;

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
