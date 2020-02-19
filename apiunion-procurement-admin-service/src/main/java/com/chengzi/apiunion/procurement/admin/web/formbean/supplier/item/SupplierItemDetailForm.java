package com.chengzi.apiunion.procurement.admin.web.formbean.supplier.item;

import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;

/**
 * @author 致远
 */
public class SupplierItemDetailForm extends BaseForm {

    @Min(value = 1, message = "商品ID不正确")
    private long itemId;

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }
}
