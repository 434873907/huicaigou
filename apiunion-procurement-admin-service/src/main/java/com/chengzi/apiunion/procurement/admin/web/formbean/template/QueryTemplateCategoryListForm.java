package com.chengzi.apiunion.procurement.admin.web.formbean.template;

import com.chengzi.common.web.formbean.BasePageForm;

/**
 * @author zhiyuan
 * 模板类目查询
 */
public class QueryTemplateCategoryListForm extends BasePageForm {

    private String cateName;

    //类目类型:1标品，2非标品
    private Integer type;

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
