package com.chengzi.apiunion.procurement.admin.web.formbean.template;

import com.chengzi.apiunion.supplier.common.item.pojo.query.SupplierTemplateItemSearchQuery;
import com.chengzi.common.data.support.Range;

import java.util.Date;

/**
 * @author 行者
 */
public class QueryTemplateCategoryCountForm extends QueryTemplateItemListForm {

    private Long parentId;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public SupplierTemplateItemSearchQuery buildQuery() {
        SupplierTemplateItemSearchQuery query = new SupplierTemplateItemSearchQuery();
        query.setStatus(getStatus());
        query.setDateRange(new Range<Date>(getModifyTimeBegin(),getModifyTimeEnd()));
        query.setKeyword(getKeyword());
        return query;
    }
}
