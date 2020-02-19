/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.pojo.item;

import java.math.BigDecimal;
import java.util.Date;

import com.chengzi.apiunion.common.module.image.annotation.ImageDecorater;
import com.chengzi.apiunion.common.module.image.enums.ImageBizType;
import com.chengzi.apiunion.item.pojo.ItemRefPrice;
import com.chengzi.common.data.beans.JsonPojo;

/**
 * @author Kolor
 */
public class  UnapprovedItemSkuListBO extends JsonPojo {
    /**
     * 记录ID
     */
    private long         id;
    /**
     * 创建时间
     */
    private Date         createTime;
    /**
     * 更新时间
     */
    private Date         modifyTime;
    /**
     * 商品ID
     */
    private long         itemId;
    /**
     * 商品名称
     */
    private String       itemName;

    /**
     * 商品主图
     */
    @ImageDecorater(ImageBizType.ADMIN_ITEM_LIST)
    private String       mainImageUrl;
    /**
     * 品牌ID
     */
    private long         brandId;
    /**
     * 品牌名称
     */
    private String       brandName;
    /**
     * 店铺ID
     */
    private long         shopId;
    /**
     * 店铺名称
     */
    private String       shopName;
    private long         cateId1;
    private String       cateName1;
    private long         cateId2;
    private String       cateName2;
    private long         cateId3;
    private String       cateName3;
    private long         skuId;
    private String       sku;
    private int          channelType;
    private String       channelName;
    private long         supplierId;
    private String       supplierName;
    private int          type;
    private String       info;
    /**
     * 当前价
     */
    private String   price;
    /**
     * 渠道价（供货商新价）
     */
    private BigDecimal   channelPrice;
    /**
     * 原渠道价（供货商旧价）
     */
    private BigDecimal   oldChannelPrice;
    /**
     * 库存
     */
    private int          stock;
    /**
     * 利润率
     */
    private String       profitRate;
    /**
     * 参考价信息
     */
    private ItemRefPrice refPrice;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public BigDecimal getChannelPrice() {
        return channelPrice;
    }

    public void setChannelPrice(BigDecimal channelPrice) {
        this.channelPrice = channelPrice;
    }

    public BigDecimal getOldChannelPrice() {
        return oldChannelPrice;
    }

    public void setOldChannelPrice(BigDecimal oldChannelPrice) {
        this.oldChannelPrice = oldChannelPrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getProfitRate() {
        return profitRate;
    }

    public void setProfitRate(String profitRate) {
        this.profitRate = profitRate;
    }

    public ItemRefPrice getRefPrice() {
        return refPrice;
    }

    public void setRefPrice(ItemRefPrice refPrice) {
        this.refPrice = refPrice;
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

    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public void setMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
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

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public long getCateId1() {
        return cateId1;
    }

    public void setCateId1(long cateId1) {
        this.cateId1 = cateId1;
    }

    public String getCateName1() {
        return cateName1;
    }

    public void setCateName1(String cateName1) {
        this.cateName1 = cateName1;
    }

    public long getCateId2() {
        return cateId2;
    }

    public void setCateId2(long cateId2) {
        this.cateId2 = cateId2;
    }

    public String getCateName2() {
        return cateName2;
    }

    public void setCateName2(String cateName2) {
        this.cateName2 = cateName2;
    }

    public long getCateId3() {
        return cateId3;
    }

    public void setCateId3(long cateId3) {
        this.cateId3 = cateId3;
    }

    public String getCateName3() {
        return cateName3;
    }

    public void setCateName3(String cateName3) {
        this.cateName3 = cateName3;
    }

    public long getSkuId() {
        return skuId;
    }

    public void setSkuId(long skuId) {
        this.skuId = skuId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
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

    public long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

}
