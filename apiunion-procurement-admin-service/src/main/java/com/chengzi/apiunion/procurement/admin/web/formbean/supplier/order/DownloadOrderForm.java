package com.chengzi.apiunion.procurement.admin.web.formbean.supplier.order;

import java.util.Date;

import com.chengzi.apiunion.supplier.common.order.enums.SupplierOrderSearchStatusEnum;
import com.chengzi.common.web.formbean.BaseForm;

/**
 * @author 随风
 * @create 2020-01-17 10:39
 **/
public class DownloadOrderForm extends BaseForm {

    private boolean                         abnormal;

    private SupplierOrderSearchStatusEnum   status;

    private String                          keyword;

    private Long                            supplierId;

    private Date                            startTime;

    private Date                            endTime;

    public boolean isAbnormal() {
        return abnormal;
    }

    public void setAbnormal(boolean abnormal) {
        this.abnormal = abnormal;
    }

    public SupplierOrderSearchStatusEnum getStatus() {
        return status;
    }

    public void setStatus(SupplierOrderSearchStatusEnum status) {
        this.status = status;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
