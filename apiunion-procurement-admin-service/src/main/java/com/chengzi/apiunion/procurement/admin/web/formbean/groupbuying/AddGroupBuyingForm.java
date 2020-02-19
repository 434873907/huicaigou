package com.chengzi.apiunion.procurement.admin.web.formbean.groupbuying;

import com.chengzi.apiunion.common.data.enums.CurrencyEnum;
import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;
import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.NotNull;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 月汐
 * @date 2019/2/18 17:02
 */
public class AddGroupBuyingForm extends BaseForm {

    @NotBlankAndNull(message = "请输入团购名")
    private String name;

    @NotNull(message = "请选择开始日期")
    private Date startTime;

    @NotNull(message = "请选择结束时间")
    private Date endTime;

    private String banner;

    private boolean isDisableCoupons;

    @Min(value = 1, message = "请选择团购商品")
    private long itemId;

    @NotBlankAndNull(message = "请选择参加团购的sku")
    private String skuIds;

    @Min(value = 1, message = "请选择渠道")
    private int channelType;

    @NotNull(message = "请输入团购价")
    private BigDecimal groupPrice;

    @Min(value = 1, message = "请输入正确的团购商品数量")
    private int stockNum;

    private boolean isLimit;

    private int limitNum;

    @NotNull(message = "请选择货币")
    private CurrencyEnum currency;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public boolean isDisableCoupons() {
        return isDisableCoupons;
    }

    public void setDisableCoupons(boolean disableCoupons) {
        isDisableCoupons = disableCoupons;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getSkuIds() {
        return skuIds;
    }

    public void setSkuIds(String skuIds) {
        this.skuIds = skuIds;
    }

    public int getChannelType() {
        return channelType;
    }

    public void setChannelType(int channelType) {
        this.channelType = channelType;
    }

    public BigDecimal getGroupPrice() {
        return groupPrice;
    }

    public void setGroupPrice(BigDecimal groupPrice) {
        this.groupPrice = groupPrice;
    }

    public int getStockNum() {
        return stockNum;
    }

    public void setStockNum(int stockNum) {
        this.stockNum = stockNum;
    }

    public boolean isLimit() {
        return isLimit;
    }

    public void setLimit(boolean limit) {
        isLimit = limit;
    }

    public int getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(int limitNum) {
        this.limitNum = limitNum;
    }

    public CurrencyEnum getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyEnum currency) {
        this.currency = currency;
    }
}
