package com.chengzi.apiunion.procurement.admin.web.formbean.supplier.order;

import java.util.Date;

import com.chengzi.apiunion.supplier.common.order.enums.SupplierOrderSearchStatusEnum;
import com.chengzi.common.web.formbean.BasePageForm;

/**
 * @author 随风
 * @create 2020-01-09 20:12
 **/
public class QuerySupplierOrderListForm extends BasePageForm {

    private SupplierOrderSearchStatusEnum   status;

    private String                          keyword;

    private Long                            supplierId;

    private Date                            startTime;

    private Date                            endTime;

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
