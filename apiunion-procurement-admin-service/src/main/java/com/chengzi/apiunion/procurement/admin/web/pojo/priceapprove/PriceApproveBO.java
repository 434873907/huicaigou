package com.chengzi.apiunion.procurement.admin.web.pojo.priceapprove;

import com.chengzi.apiunion.supplier.common.priceapprove.pojo.SkuPriceApproveDO;
import com.chengzi.common.data.beans.JsonPojo;
import net.sf.oval.constraint.NotNull;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 行者
 */
public class PriceApproveBO extends JsonPojo {

    private long        id;

    /**
     * 创建时间
     */
    protected Date      createTime;

    /**
     * 修改时间
     */
    protected Date      modifyTime;

    /**
     * 供应商ID
     */
    private Long        supplierId;

    private String      supplierName;

    /**
     * 商品ID
     */
    private Long        supplierItemId;

    /**
     * skuId
     */
    private Long        supplierSkuId;

    /**
     * 当前的价格
     */
    private BigDecimal  priceCurrent;

    /**
     * 申请的价格
     */
    private BigDecimal  priceApply;

    /**
     * 申请时间
     */
    private Date        applyTime;

    /**
     * 审核状态: 0, 未审核, 1, 已通过, 2, 已驳回, 3, 已撤销
     */
    private Integer     status;

    /**
     * 审核时间
     */
    private Date        approveTime;

    /**
     * 描述: 驳回的原因等
     */
    private String      approveDesc;

    private Integer     channelType;

    private String      channelName;

    /**
     * 审核人
     */
    private long        approveUserId;

    /**
     * 供价变更后的利润率
     */
    private String      profitRate;

    /**
     * 是否需要提醒用户利润率过低
     */
    private int         needRemind;

    /**
     * 企业用户价格
     */
    private BigDecimal  enterprisePrice;

    private BigDecimal  salePrice;

    private String      itemName;

    private String      skuDesc;

    private String      mainImageUrl;

    /**
     *  会订货商品Id
     */
    private long        itemId;

    public long getApproveUserId() {
        return approveUserId;
    }

    public void setApproveUserId(long approveUserId) {
        this.approveUserId = approveUserId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getSupplierItemId() {
        return supplierItemId;
    }

    public void setSupplierItemId(Long supplierItemId) {
        this.supplierItemId = supplierItemId;
    }

    public Long getSupplierSkuId() {
        return supplierSkuId;
    }

    public void setSupplierSkuId(Long supplierSkuId) {
        this.supplierSkuId = supplierSkuId;
    }

    public BigDecimal getPriceCurrent() {
        return priceCurrent;
    }

    public void setPriceCurrent(BigDecimal priceCurrent) {
        this.priceCurrent = priceCurrent;
    }

    public BigDecimal getPriceApply() {
        return priceApply;
    }

    public void setPriceApply(BigDecimal priceApply) {
        this.priceApply = priceApply;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    public String getApproveDesc() {
        return approveDesc;
    }

    public void setApproveDesc(String approveDesc) {
        this.approveDesc = approveDesc;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getProfitRate() {
        return profitRate;
    }

    public void setProfitRate(String profitRate) {
        this.profitRate = profitRate;
    }

    public int getNeedRemind() {
        return needRemind;
    }

    public void setNeedRemind(int needRemind) {
        this.needRemind = needRemind;
    }

    public BigDecimal getEnterprisePrice() {
        return enterprisePrice;
    }

    public void setEnterprisePrice(BigDecimal enterprisePrice) {
        this.enterprisePrice = enterprisePrice;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getSkuDesc() {
        return skuDesc;
    }

    public void setSkuDesc(String skuDesc) {
        this.skuDesc = skuDesc;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public void setMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public Integer getChannelType() {
        return channelType;
    }

    public void setChannelType(Integer channelType) {
        this.channelType = channelType;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public static PriceApproveBO convert(SkuPriceApproveDO approveDO) {
        PriceApproveBO bo = new PriceApproveBO();
        BeanUtils.copyProperties(approveDO,bo);
        return bo;
    }
}