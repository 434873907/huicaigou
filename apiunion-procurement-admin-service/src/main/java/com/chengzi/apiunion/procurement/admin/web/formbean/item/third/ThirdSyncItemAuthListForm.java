/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.formbean.item.third;

import com.chengzi.common.web.formbean.BaseForm;

import net.sf.oval.constraint.Min;

/**
 * @author Kolor
 */
public class ThirdSyncItemAuthListForm extends BaseForm {
    @Min(value = 1, message = "供应商ID不能为空")
    private long supplierId;

    public long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(long supplierId) {
        this.supplierId = supplierId;
    }

}
