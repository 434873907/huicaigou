package com.chengzi.apiunion.procurement.admin.web.formbean.invoice;

import com.chengzi.common.web.formbean.BasePageForm;

/**
 * @author 苏子
 * @date 2019年1月29日
 */
public class QueryInvoiceRecordForm extends BasePageForm {

    /**
     * 开票状态，0：待开，1：已开
     */
    private int invoiceStatus;

    /**
     * 订单号
     */
    private String orderNum;

    public int getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(int invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }
}
