package com.chengzi.apiunion.procurement.admin.web.formbean.presell;

import com.chengzi.apiunion.presell.pojo.PresellSkuLimitDO;
import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;
import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.MinSize;
import net.sf.oval.constraint.NotNull;
import org.summercool.web.annotation.JsonField;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author 月汐
 * @date 2019/2/25 20:20
 */
public class AddPresellForm extends BaseForm {

    @NotBlankAndNull(message = "请输入活动名称")
    private String name;

    @NotNull(message = "请选择定金支付起始日期")
    private Date depositStartTime;

    @NotNull(message = "请选择定金支付截止日期")
    private Date depositEndTime;

    @NotNull(message = "请选择尾款支付起始日期")
    private Date tailStartTime;

    @NotNull(message = "请选择尾款支付截止日期")
    private Date tailEndTime;

    @Min(value = 0, message = "请选择正确的商品")
    private long itemId;

    @JsonField
    @MinSize(value = 1, message = "请至少选择一个sku")
    private List<PresellSkuLimitDO> skuSellLimit;

    @Min(value = 0, message = "请选择合适的渠道")
    private int channelType;

    @Min(value = 1, message = "请选择正确的货币")
    private int currency;

    @NotNull(message = "请输入定金金额")
    private BigDecimal depositAmount;

    @NotNull(message = "请输入尾款金额")
    private BigDecimal tailAmount;

    @NotNull(message = "请选择是否限购")
    private Boolean isLimit;

    private int limitNum;

    /**
     * 活动后商品状态，1：正常销售，0：自动下架
     */
    @Min(value = 0, message = "请选择活动结束后商品状态")
    private int statusAfterAct;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDepositStartTime() {
        return depositStartTime;
    }

    public void setDepositStartTime(Date depositStartTime) {
        this.depositStartTime = depositStartTime;
    }

    public Date getDepositEndTime() {
        return depositEndTime;
    }

    public void setDepositEndTime(Date depositEndTime) {
        this.depositEndTime = depositEndTime;
    }

    public Date getTailStartTime() {
        return tailStartTime;
    }

    public void setTailStartTime(Date tailStartTime) {
        this.tailStartTime = tailStartTime;
    }

    public Date getTailEndTime() {
        return tailEndTime;
    }

    public void setTailEndTime(Date tailEndTime) {
        this.tailEndTime = tailEndTime;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public List<PresellSkuLimitDO> getSkuSellLimit() {
        return skuSellLimit;
    }

    public void setSkuSellLimit(List<PresellSkuLimitDO> skuSellLimit) {
        this.skuSellLimit = skuSellLimit;
    }

    public int getChannelType() {
        return channelType;
    }

    public void setChannelType(int channelType) {
        this.channelType = channelType;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public BigDecimal getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(BigDecimal depositAmount) {
        this.depositAmount = depositAmount;
    }

    public BigDecimal getTailAmount() {
        return tailAmount;
    }

    public void setTailAmount(BigDecimal tailAmount) {
        this.tailAmount = tailAmount;
    }

    public Boolean getLimit() {
        return isLimit;
    }

    public void setLimit(Boolean limit) {
        isLimit = limit;
    }

    public int getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(int limitNum) {
        this.limitNum = limitNum;
    }

    public int getStatusAfterAct() {
        return statusAfterAct;
    }

    public void setStatusAfterAct(int statusAfterAct) {
        this.statusAfterAct = statusAfterAct;
    }
}
