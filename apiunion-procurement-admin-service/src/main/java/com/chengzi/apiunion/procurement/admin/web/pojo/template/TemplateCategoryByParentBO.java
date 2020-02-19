package com.chengzi.apiunion.procurement.admin.web.pojo.template;

import com.chengzi.apiunion.supplier.common.template.pojo.SupplierTemplateCategoryDO;
import com.chengzi.common.data.beans.JsonPojo;

/**
 * @author 行者
 */
public class TemplateCategoryByParentBO extends JsonPojo {

    private Long id;

    private String name;

    private Long count;

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

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public static TemplateCategoryByParentBO convert(SupplierTemplateCategoryDO templateCategoryDO, long count) {
        TemplateCategoryByParentBO bo = new TemplateCategoryByParentBO();
        bo.setId(templateCategoryDO.getId());
        bo.setName(templateCategoryDO.getCateName());
        bo.setCount(count);
        return bo;
    }

}
