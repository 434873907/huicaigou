package com.chengzi.apiunion.procurement.admin.web.formbean.brand;

import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;

/**
 * @author 月汐
 * @date 2018/10/15 16:57
 */
public class GetBrandByIdForm extends BaseForm {

    @Min(value = 1, message = "请选择正确的品牌")
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
