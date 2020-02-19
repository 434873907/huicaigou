package com.chengzi.apiunion.procurement.admin.web.formbean.expressfeetemplate;

import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;

/**
 * @author 月汐
 * @date 2018/10/19 16:53
 */
public class QueryTemplateListBySupplierForm extends BaseForm {

    @Min(value = 1, message = "请选择正确的供应商")
    private long supplierId;

    public long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(long supplierId) {
        this.supplierId = supplierId;
    }
}
