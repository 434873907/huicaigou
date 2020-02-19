package com.chengzi.apiunion.procurement.admin.web.pojo.invoice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.chengzi.apiunion.invoice.enums.InvoiceHeadType;
import com.chengzi.apiunion.invoice.enums.InvoiceTypeEnum;
import com.chengzi.apiunion.invoice.pojo.InvoiceRecordDO;
import com.chengzi.apiunion.order.enums.OrderStatusEnum;
import com.chengzi.common.util.CollectionUtil;
import com.chengzi.common.util.EnumUtil;

/**
 * @author 苏子
 * @date 2019年1月30日
 */
public class InvoiceRecordListBO {

    private long       id;
    /**
     * 父订单号
     */
    private String     orderNum;
    /**
     * 用户登录账号
     */
    private String     account;
    /**
     * 昵称
     */
    private String     nickName;
    /**
     * 订单金额
     */
    private BigDecimal orderAmount;
    /**
     * 订单状态
     */
    private String     orderStruts;
    /**
     * 发票类型
     * {@link InvoiceTypeEnum}
     */
    private String     invoiceTypeStr;
    /**
     * 抬头
     * {@link InvoiceHeadType}
     */
    private String     invoiceHeadStr;
    /**
     * 单位名称
     */
    private String     companyName;
    /**
     * 税号
     */
    private String     taxNumber;
    /**
     * 发票代码
     */
    private String            invoiceCode;
    /**
     * 发票号码
     */
    private String            invoiceNum;
    /**
     * 电子发票照片
     */
    private String            image;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public BigDecimal getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(BigDecimal orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getOrderStruts() {
        return orderStruts;
    }

    public void setOrderStruts(String orderStruts) {
        this.orderStruts = orderStruts;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public static List<InvoiceRecordListBO> convert(List<InvoiceRecordDO> invoiceRecordList) {
        if (CollectionUtil.isEmpty(invoiceRecordList)) {
            return null;
        }

        List<InvoiceRecordListBO> list = new ArrayList<>();
        for (InvoiceRecordDO invoiceRecord : invoiceRecordList) {
            InvoiceRecordListBO bo = new InvoiceRecordListBO();
            bo.setId(invoiceRecord.getId());
            bo.setOrderNum(invoiceRecord.getOrderNum());
            bo.setAccount(invoiceRecord.getAccount());
            bo.setNickName(invoiceRecord.getNickName());
            bo.setOrderAmount(invoiceRecord.getOrderAmount());
            
            OrderStatusEnum orderStatus = EnumUtil.parse(OrderStatusEnum.class, invoiceRecord.getOrderStruts());
            if (orderStatus != null) {
                bo.setOrderStruts(orderStatus.getRemark());
            }
            InvoiceTypeEnum invoiceType = EnumUtil.parse(InvoiceTypeEnum.class, invoiceRecord.getInvoiceType());
            if (invoiceType != null) {
                bo.setInvoiceTypeStr(invoiceType.getAbbreviation());
            }
            InvoiceHeadType invoiceHeadType = EnumUtil.parse(InvoiceHeadType.class, invoiceRecord.getInvoiceHead());
            if (invoiceHeadType != null) {
                bo.setInvoiceHeadStr(invoiceHeadType.getRemark());
            }
            bo.setCompanyName(invoiceRecord.getCompanyName());
            bo.setTaxNumber(invoiceRecord.getTaxNumber());
            bo.setInvoiceCode(invoiceRecord.getInvoiceCode());
            bo.setInvoiceNum(invoiceRecord.getInvoiceNum());
            bo.setImage(invoiceRecord.getImage());
            list.add(bo);
        }
        return list;
    }
}
