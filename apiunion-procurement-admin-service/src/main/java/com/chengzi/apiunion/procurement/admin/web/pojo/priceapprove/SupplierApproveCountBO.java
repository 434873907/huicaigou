package com.chengzi.apiunion.procurement.admin.web.pojo.priceapprove;

import com.chengzi.apiunion.supplier.common.priceapprove.pojo.SkuPriceApproveDO;
import com.chengzi.common.data.beans.JsonPojo;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 行者
 */
public class SupplierApproveCountBO extends JsonPojo {

    private long        count;

    /**
     * 供应商ID
     */
    private long        supplierId;

    /**
     * 供应商名
     */
    private String      supplierName;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
}