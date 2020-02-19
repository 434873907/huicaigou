package com.chengzi.apiunion.procurement.admin.web.formbean.partnercategory;

import com.chengzi.common.web.formbean.BasePageForm;

/**
 * @author 月汐
 * @date 2018/10/31 16:02
 */
public class QueryThirdCategoryListForm extends BasePageForm {

    private Long firstCategoryId;

    private Long secondCategoryId;

    private String name;

    public Long getFirstCategoryId() {
        return firstCategoryId;
    }

    public void setFirstCategoryId(Long firstCategoryId) {
        this.firstCategoryId = firstCategoryId;
    }

    public Long getSecondCategoryId() {
        return secondCategoryId;
    }

    public void setSecondCategoryId(Long secondCategoryId) {
        this.secondCategoryId = secondCategoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
