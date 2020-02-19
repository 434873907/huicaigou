package com.chengzi.apiunion.procurement.admin.web.formbean.supplier;

import com.chengzi.common.web.formbean.BaseForm;

import net.sf.oval.constraint.Min;

/**
 * @author 随风
 * @create 2020-01-08 14:10
 **/
public class ResetSupplierPasswordForm extends BaseForm {

    @Min(value = 1, message = "供应商不存在")
    private long    supplierId;

    public long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(long supplierId) {
        this.supplierId = supplierId;
    }

}
