package com.chengzi.apiunion.procurement.admin.web.pojo.presell;

import com.chengzi.common.data.beans.JsonPojo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 月汐
 * @date 2019/2/26 11:19
 */
public class PresellBO extends JsonPojo {

    private long id;

    private String name;

    private String depositStartTime;

    private String depositEndTime;

    private String tailStartTime;

    private String tailEndTime;

    private int currency;

    private BigDecimal depositAmount;

    private BigDecimal tailAmount;

    private boolean isLimit;

    private int limitNum;

    private int statusAfterAct;

    private ItemInfo itemInfo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepositStartTime() {
        return depositStartTime;
    }

    public void setDepositStartTime(String depositStartTime) {
        this.depositStartTime = depositStartTime;
    }

    public String getDepositEndTime() {
        return depositEndTime;
    }

    public void setDepositEndTime(String depositEndTime) {
        this.depositEndTime = depositEndTime;
    }

    public String getTailStartTime() {
        return tailStartTime;
    }

    public void setTailStartTime(String tailStartTime) {
        this.tailStartTime = tailStartTime;
    }

    public String getTailEndTime() {
        return tailEndTime;
    }

    public void setTailEndTime(String tailEndTime) {
        this.tailEndTime = tailEndTime;
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

    public int getStatusAfterAct() {
        return statusAfterAct;
    }

    public void setStatusAfterAct(int statusAfterAct) {
        this.statusAfterAct = statusAfterAct;
    }

    public ItemInfo getItemInfo() {
        return itemInfo;
    }

    public void setItemInfo(ItemInfo itemInfo) {
        this.itemInfo = itemInfo;
    }

    public static class ItemInfo {

        private long itemId;

        private String itemName;

        private String mainImg;

        private int channelType;

        private String channelName;

        private List<SkuInfo> skuInfoList;

        public long getItemId() {
            return itemId;
        }

        public void setItemId(long itemId) {
            this.itemId = itemId;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getMainImg() {
            return mainImg;
        }

        public void setMainImg(String mainImg) {
            this.mainImg = mainImg;
        }

        public int getChannelType() {
            return channelType;
        }

        public void setChannelType(int channelType) {
            this.channelType = channelType;
        }

        public String getChannelName() {
            return channelName;
        }

        public void setChannelName(String channelName) {
            this.channelName = channelName;
        }

        public List<SkuInfo> getSkuInfoList() {
            return skuInfoList;
        }

        public void setSkuInfoList(List<SkuInfo> skuInfoList) {
            this.skuInfoList = skuInfoList;
        }
    }

    public static class SkuInfo {

        private long skuId;

        private String skuValue;

        private int stockNum;

        private int limitNum;

        private BigDecimal price;

        public long getSkuId() {
            return skuId;
        }

        public void setSkuId(long skuId) {
            this.skuId = skuId;
        }

        public String getSkuValue() {
            return skuValue;
        }

        public void setSkuValue(String skuValue) {
            this.skuValue = skuValue;
        }

        public int getStockNum() {
            return stockNum;
        }

        public void setStockNum(int stockNum) {
            this.stockNum = stockNum;
        }

        public int getLimitNum() {
            return limitNum;
        }

        public void setLimitNum(int limitNum) {
            this.limitNum = limitNum;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public SkuInfo setPrice(BigDecimal price) {
            this.price = price;
            return this;
        }
    }

}
