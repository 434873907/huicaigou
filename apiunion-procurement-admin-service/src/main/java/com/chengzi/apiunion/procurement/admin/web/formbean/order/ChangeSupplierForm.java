package com.chengzi.apiunion.procurement.admin.web.formbean.order;

import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;

/**
 * <p>
 * </p>
 * User: 摩天
 * Date: 2018-11-15 11:31
 */
public class ChangeSupplierForm extends BaseForm {

    /**
     * 用户id
     */
    @Min(value = 1 ,message = "请输入正确的userId")
    private long userId;

    /**
     * 供应商名字
     */
    private String supplierName;

    /**
     * 子订单ID
     */
    @Min(1)
    private long orderItemId;

    /**
     * 供应商ID
     */
    @Min(1)
    private long supplierId;

    /**
     * 是否需要拆包
     */
    private boolean needSplit;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(long supplierId) {
        this.supplierId = supplierId;
    }

    public boolean getNeedSplit() {
        return needSplit;
    }

    public void setNeedSplit(boolean needSplit) {
        this.needSplit = needSplit;
    }
}
