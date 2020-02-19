package com.chengzi.apiunion.procurement.admin.web.pojo.item;

import java.math.BigDecimal;
import java.util.List;

import com.chengzi.common.data.beans.JsonPojo;

/**
 * @author 月汐
 * @date 2019/2/20 15:09
 */
public class ItemForActBO extends JsonPojo {

    private long              itemId;

    private String            itemName;

    private String            mainImg;

    private List<ChannelInfo> channelInfoList;

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

    public List<ChannelInfo> getChannelInfoList() {
        return channelInfoList;
    }

    public void setChannelInfoList(List<ChannelInfo> channelInfoList) {
        this.channelInfoList = channelInfoList;
    }

    public static class ChannelInfo extends JsonPojo {
        private int           channelType;

        private String        channelName;

        private List<SkuInfo> skuInfoList;

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

    public static class SkuInfo extends JsonPojo {
        private long       skuId;

        private String     skuValue;

        private int        stockNum;

        private String price;

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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }

}
