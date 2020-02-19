package com.chengzi.apiunion.procurement.admin.web.formbean.supplier;

import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;

/**
 * @author 月汐
 * @date 2018/11/23 19:53
 */
public class GetSupplierDetailForm extends BaseForm {

    @Min(value = 1, message = "请选择正确的供应商")
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
