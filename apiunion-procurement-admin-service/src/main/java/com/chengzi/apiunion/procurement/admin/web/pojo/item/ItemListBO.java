/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.pojo.item;

import java.util.Date;
import java.util.Set;

import com.chengzi.apiunion.common.data.enums.CurrencyEnum;
import com.chengzi.apiunion.common.module.image.annotation.ImageDecorater;
import com.chengzi.apiunion.common.module.image.enums.ImageBizType;
import com.chengzi.apiunion.item.enums.ItemStatusEnum;
import com.chengzi.common.data.beans.JsonPojo;

/**
 * @author Kolor
 */
public class ItemListBO extends JsonPojo {
    private long        id;
    private Date        createTime;
    private Date        modifyTime;
    private boolean     deleted;

    /**
     * 商品标题
     */
    private String      name;

    /**
     * 商品原始标题
     */
    private String      originalName;

    /**
     * 店铺ID
     */
    private long        shopId;

    /**
     * 品牌ID
     */
    private long        brandId;
    /**
     * 品牌名称
     */
    private String      brandName;

    /**
     * 一级类目
     */
    private long        cateId1;
    private String      cateName1;

    /**
     * 二级类目
     */
    private long        cateId2;
    private String      cateName2;

    /**
     * 三级类目
     */
    private long        cateId3;
    private String      cateName3;

    /**
     * 状态，0：初始，1：上架，2：下上架
     * {@link ItemStatusEnum}
     */
    private int         status;

    /**
     * 货币
     * {@link CurrencyEnum}
     */
    private int         currency;

    /**
     * 创建者，-1表示系统添加
     */
    private String      createPartnerName;

    /**
     * 主图
     */
    @ImageDecorater(ImageBizType.ADMIN_ITEM_LIST)
    private String      mainImageUrl;

    private Set<String> skuList;

    private String      price;

    private Set<String> channels;

    private long        groupId;

    private String skuListStr;

    private Date        onlineTime;

    private Date        offlineTime;

    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public void setMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
    }

    public String getCateName1() {
        return cateName1;
    }

    public void setCateName1(String cateName1) {
        this.cateName1 = cateName1;
    }

    public String getCateName2() {
        return cateName2;
    }

    public void setCateName2(String cateName2) {
        this.cateName2 = cateName2;
    }

    public String getCateName3() {
        return cateName3;
    }

    public void setCateName3(String cateName3) {
        this.cateName3 = cateName3;
    }

    public Set<String> getSkuList() {
        return skuList;
    }

    public String getSkuListStr() {
        return skuListStr;
    }

    public void setSkuListStr(String skuListStr) {
        this.skuListStr = skuListStr;
    }

    public void setSkuList(Set<String> skuList) {
        this.skuList = skuList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public long getBrandId() {
        return brandId;
    }

    public void setBrandId(long brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public long getCateId1() {
        return cateId1;
    }

    public void setCateId1(long cateId1) {
        this.cateId1 = cateId1;
    }

    public long getCateId2() {
        return cateId2;
    }

    public void setCateId2(long cateId2) {
        this.cateId2 = cateId2;
    }

    public long getCateId3() {
        return cateId3;
    }

    public void setCateId3(long cateId3) {
        this.cateId3 = cateId3;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public String getCreatePartnerName() {
        return createPartnerName;
    }

    public void setCreatePartnerName(String createPartnerName) {
        this.createPartnerName = createPartnerName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Set<String> getChannels() {
        return channels;
    }

    public void setChannels(Set<String> channels) {
        this.channels = channels;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public Date getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(Date onlineTime) {
        this.onlineTime = onlineTime;
    }

    public Date getOfflineTime() {
        return offlineTime;
    }

    public void setOfflineTime(Date offlineTime) {
        this.offlineTime = offlineTime;
    }
}
