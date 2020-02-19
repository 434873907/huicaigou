package com.chengzi.apiunion.procurement.admin.web.formbean.supplier;

import net.sf.oval.constraint.Min;

/**
 * @author 月汐
 * @date 2018/11/23 19:41
 */
public class UpdateSupplierForm extends AddSupplierForm {

    @Min(value = 1, message = "请输入正确的供应商编号")
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
