package com.chengzi.apiunion.procurement.admin.web.formbean.item;

import com.chengzi.common.web.formbean.BaseForm;

/**
 * @author 行者
 */
public class GetSupplierChangesForm extends BaseForm {

    private long itemId;

    private long skuSupplierId;

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public long getSkuSupplierId() {
        return skuSupplierId;
    }

    public void setSkuSupplierId(long skuSupplierId) {
        this.skuSupplierId = skuSupplierId;
    }
}
