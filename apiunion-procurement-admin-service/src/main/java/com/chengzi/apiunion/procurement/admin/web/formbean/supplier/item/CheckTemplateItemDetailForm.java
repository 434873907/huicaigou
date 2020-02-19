package com.chengzi.apiunion.procurement.admin.web.formbean.supplier.item;

import com.chengzi.common.web.formbean.BasePageForm;
import net.sf.oval.constraint.NotNull;

/**
 * @author 致远
 */
public class CheckTemplateItemDetailForm extends BasePageForm {

    @NotNull(message = "请输入查询的关键字")
    private String keyword;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
