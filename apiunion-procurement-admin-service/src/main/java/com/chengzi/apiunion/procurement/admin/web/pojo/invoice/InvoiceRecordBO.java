package com.chengzi.apiunion.procurement.admin.web.pojo.invoice;

import com.chengzi.apiunion.invoice.enums.InvoiceHeadType;
import com.chengzi.apiunion.invoice.enums.InvoiceTypeEnum;
import com.chengzi.apiunion.invoice.pojo.InvoiceRecordDO;
import com.chengzi.apiunion.order.pojo.OrderDeliveryInfo;
import com.chengzi.common.util.EnumUtil;

/**
 * @author 苏子
 * @date 2019年2月18日
 */
public class InvoiceRecordBO {
    /**
     * 父订单号
     */
    private String orderNum;
    /**
     * 发票类型
     * {@link InvoiceTypeEnum}
     */
    private String invoiceTypeStr;
    /**
     * 抬头
     * {@link InvoiceHeadType}
     */
    private String invoiceHeadStr;
    /**
     * 单位名称
     */
    private String companyName;
    /**
     * 税号
     */
    private String taxNumber;
    /**
     * 地址
     */
    private String registeredAddress;
    /**
     * 电话
     */
    private String registeredPhone;
    /**
     * 开户行
     */
    private String bank;
    /**
     * 开户行账号
     */
    private String bankAccount;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 发票邮寄地址
     * {@link OrderDeliveryInfo}
     */
    private String deliveryInfo;
    /**
     * 发票代码
     */
    private String invoiceCode;
    /**
     * 发票号码
     */
    private String invoiceNum;

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getInvoiceTypeStr() {
        return invoiceTypeStr;
    }

    public void setInvoiceTypeStr(String invoiceTypeStr) {
        this.invoiceTypeStr = invoiceTypeStr;
    }

    public String getInvoiceHeadStr() {
        return invoiceHeadStr;
    }

    public void setInvoiceHeadStr(String invoiceHeadStr) {
        this.invoiceHeadStr = invoiceHeadStr;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getRegisteredAddress() {
        return registeredAddress;
    }

    public void setRegisteredAddress(String registeredAddress) {
        this.registeredAddress = registeredAddress;
    }

    public String getRegisteredPhone() {
        return registeredPhone;
    }

    public void setRegisteredPhone(String registeredPhone) {
        this.registeredPhone = registeredPhone;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(String deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public String getInvoiceNum() {
        return invoiceNum;
    }

    public void setInvoiceNum(String invoiceNum) {
        this.invoiceNum = invoiceNum;
    }

    public static InvoiceRecordBO convert(InvoiceRecordDO invoiceRecord) {
        if (invoiceRecord == null) {
            return null;
        }
        InvoiceRecordBO bo = new InvoiceRecordBO();
        bo.setOrderNum(invoiceRecord.getOrderNum());
        bo.setInvoiceTypeStr(EnumUtil.parse(InvoiceTypeEnum.class, invoiceRecord.getInvoiceType()).getAbbreviation());
        bo.setInvoiceHeadStr(EnumUtil.parse(InvoiceHeadType.class, invoiceRecord.getInvoiceHead()).getRemark());
        bo.setCompanyName(invoiceRecord.getCompanyName());
        bo.setTaxNumber(invoiceRecord.getTaxNumber());
        bo.setRegisteredAddress(invoiceRecord.getRegisteredAddress());
        bo.setRegisteredPhone(invoiceRecord.getRegisteredPhone());
        bo.setBank(invoiceRecord.getBank());
        bo.setBankAccount(invoiceRecord.getBankAccount());
        bo.setPhone(invoiceRecord.getPhone());
        bo.setInvoiceCode(invoiceRecord.getInvoiceCode());
        bo.setInvoiceNum(invoiceRecord.getInvoiceNum());
        OrderDeliveryInfo deliveryInfo = OrderDeliveryInfo.parse(invoiceRecord.getDeliveryInfo());
        if (deliveryInfo != null) {
            bo.setDeliveryInfo(deliveryInfo.getAddressNoUserInfo());
        }
        return bo;
    }
    
}
