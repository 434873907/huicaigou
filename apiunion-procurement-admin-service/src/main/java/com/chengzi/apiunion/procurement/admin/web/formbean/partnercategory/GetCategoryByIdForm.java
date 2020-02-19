package com.chengzi.apiunion.procurement.admin.web.formbean.partnercategory;

import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;

/**
 * @author 月汐
 * @date 2018/10/15 10:13
 */
public class GetCategoryByIdForm extends BaseForm {

    @Min(value = 1, message = "请选择正确的类目")
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
