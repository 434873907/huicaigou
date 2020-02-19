package com.chengzi.apiunion.procurement.admin.web.pojo.activity;

import com.alibaba.fastjson.JSON;
import com.chengzi.apiunion.common.data.enums.CurrencyEnum;
import com.chengzi.apiunion.common.module.currency.util.AmountUtil;
import com.chengzi.apiunion.item.pojo.ItemSkuInfo;
import com.chengzi.apiunion.item.pojo.cache.ItemCO;
import com.chengzi.apiunion.sales.pojo.FlashSaleActivityDO;
import com.chengzi.apiunion.sales.pojo.ParticipateItem;
import com.chengzi.common.util.CollectionUtil;

import java.util.*;

import static org.jsoup.helper.StringUtil.join;

/**
 * <p>
 * </p>
 * User: 摩天
 * Date: 2019-02-20 11:18
 */
public class FlashSaleActivityBO {
    private long                   activityId;

    private Date                   startTime;

    private Date                   endTime;

    /**
     * 货币
     * {@link CurrencyEnum}
     */
    private int                    currency;

    private String                 currencySymbol;

    private int                    isDisableCoupon;

    private int                    itemAmount;

    private List<ActivityItemInfo> activityItems;

    public long getActivityId() {
        return activityId;
    }

    public void setActivityId(long activityId) {
        this.activityId = activityId;
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

    public int getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(int itemAmount) {
        this.itemAmount = itemAmount;
    }

    public List<ActivityItemInfo> getActivityItems() {
        return activityItems;
    }

    public void setActivityItems(List<ActivityItemInfo> activityItems) {
        this.activityItems = activityItems;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public int getIsDisableCoupon() {
        return isDisableCoupon;
    }

    public void setIsDisableCoupon(int isDisableCoupon) {
        this.isDisableCoupon = isDisableCoupon;
    }

    public void addActivityItem(ActivityItemInfo activityItem) {
        if (CollectionUtil.isEmpty(this.activityItems)) {
            this.activityItems = new ArrayList<>();
        }
        this.activityItems.add(activityItem);
    }

    public static FlashSaleActivityBO convert(FlashSaleActivityDO flashSaleActivityDO, Map<Long, ItemCO> itemDOMap,
            Map<Long, ItemSkuInfo> skuInfoMap) {
        FlashSaleActivityBO flashSaleActivityBO = new FlashSaleActivityBO();
        flashSaleActivityBO.setActivityId(flashSaleActivityDO.getId());
        flashSaleActivityBO.setStartTime(flashSaleActivityDO.getStartTime());
        flashSaleActivityBO.setIsDisableCoupon(flashSaleActivityDO.getIsDisableCoupon());
        flashSaleActivityBO.setEndTime(flashSaleActivityDO.getEndTime());
        flashSaleActivityBO.setCurrency(flashSaleActivityDO.getCurrency());
        flashSaleActivityBO.setCurrencySymbol(CurrencyEnum.getSymbol(flashSaleActivityDO.getCurrency()));
        List<ParticipateItem> participateItems = JSON.parseArray(flashSaleActivityDO.getParticipateItemNum(), ParticipateItem.class);
        flashSaleActivityBO.setItemAmount(participateItems.size());
        Collections.sort(participateItems);
        participateItems.forEach(
                x -> flashSaleActivityBO.addActivityItem(ActivityItemInfo.convert(x, itemDOMap.get(x.getItemId()), skuInfoMap)));

        return flashSaleActivityBO;
    }
}

class ActivityItemInfo implements Comparable<ActivityItemInfo> {
    private int        index;

    private long       itemId;

    private long       skuId;

    private long       channelType;

    private String     channelName;

    private String     itemImg;

    private String     itemTitle;

    private String     spec;

    private int        num;

    private int        stock;

    private String     oriPrice;

    private double     flashSalePrice;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getItemImg() {
        return itemImg;
    }

    public void setItemImg(String itemImg) {
        this.itemImg = itemImg;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public long getSkuId() {
        return skuId;
    }

    public void setSkuId(long skuId) {
        this.skuId = skuId;
    }

    public long getChannelType() {
        return channelType;
    }

    public void setChannelType(long channelType) {
        this.channelType = channelType;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getOriPrice() {
        return oriPrice;
    }

    public void setOriPrice(String oriPrice) {
        this.oriPrice = oriPrice;
    }

    public double getFlashSalePrice() {
        return flashSalePrice;
    }

    public void setFlashSalePrice(double flashSalePrice) {
        this.flashSalePrice = flashSalePrice;
    }

    public static ActivityItemInfo convert(ParticipateItem participateItem, ItemCO itemCO, Map<Long, ItemSkuInfo> skuInfoMap) {
        ActivityItemInfo activityItemInfo = new ActivityItemInfo();
        activityItemInfo.setIndex(participateItem.getIndex());
        activityItemInfo.setItemId(participateItem.getItemId());
        activityItemInfo.setSkuId(participateItem.getSkuId());
        activityItemInfo.setItemImg(itemCO.getMainImageUrl());
        activityItemInfo.setItemTitle(itemCO.getName());

        activityItemInfo.setNum(participateItem.getNum());
        ItemSkuInfo itemSkuInfo = skuInfoMap.get(participateItem.getSkuId());
        if (itemSkuInfo == null) {
            return activityItemInfo;
        }
        activityItemInfo.setSpec(itemSkuInfo.getSkuValues().buildDesc());

        if (CollectionUtil.isNotEmpty(itemSkuInfo.getSkuChannels())) {
            for (ItemSkuInfo.SkuChannelInfo skuChannel : itemSkuInfo.getSkuChannels()) {
                if (skuChannel.getChannelType() == participateItem.getChannelType()) {
                    activityItemInfo.setChannelName(skuChannel.getChannelName());
                    activityItemInfo.setStock(skuChannel.getTotalStock());
                    activityItemInfo.setOriPrice(AmountUtil.getDisplayPriceStartWithSymbol(itemCO.getCurrency(), skuChannel.getPrice()));
                    break;
                }
            }
        }
        activityItemInfo.setFlashSalePrice(participateItem.getFlashSalePrice());
        activityItemInfo.setChannelType(participateItem.getChannelType());

        return activityItemInfo;
    }

    @Override
    public int compareTo(ActivityItemInfo o) {
        if (this.index > o.index) {
            return 1;
        } else if (this.index < o.index) {
            return -1;
        }
        return 0;
    }
}
