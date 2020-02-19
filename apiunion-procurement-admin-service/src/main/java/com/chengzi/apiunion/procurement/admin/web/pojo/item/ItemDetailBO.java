/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.pojo.item;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.chengzi.apiunion.common.module.image.annotation.HtmlImageDecorater;
import com.chengzi.apiunion.common.module.image.annotation.ImageDecorater;
import com.chengzi.apiunion.common.module.image.enums.ImageBizType;
import com.chengzi.apiunion.item.pojo.ItemImageInfo;
import com.chengzi.apiunion.item.pojo.ItemSkuInfo;
import com.chengzi.apiunion.item.pojo.ItemSkuNameValues;
import com.chengzi.common.data.beans.JsonPojo;

/**
 * @author Kolor
 *
 */
public class ItemDetailBO extends JsonPojo {
    private long                    id;
    private Date                    createTime;
    private Date                    modifyTime;
    private boolean                 deleted;

    private String                  name;
    private String                  originalName;

    private long                    brandId;
    private String                  brandName;

    private long                    shopId;
    private String                  shopName;

    private long                    cateId1;
    private String                  cateName1;
    private long                    cateId2;
    private String                  cateName2;
    private long                    cateId3;
    private String                  cateName3;

    private String                  info;
    private String                  desc;
    // 后台不压缩图文详情 By Kolor
    //    @HtmlImageDecorater(value = ImageBizType.ADMIN_ITEM_DETAIL)
    private String                  richDesc;
    private boolean                 isGift;
    private boolean                 presell;

    private int                     status;

    @ImageDecorater(ImageBizType.ADMIN_ITEM)
    private List<ItemImageInfo>     imageList;

    /**
    * 商品规格名称及可选值
    */
    private List<ItemSkuNameValues> skuNameValues;
    /**
     * 货币单位
     */
    private int                     currency;

    private List<ItemSkuInfo>       skuList;

    /**
     * 单位
     */
    private String                  unit;

    /**
     * 单位是否可修改，0不可修改，1可修改
     */
    private int                     unitCanChange;

    /**
     * 型号
     */
    private String                  modelNo;

    private String                  priceRange;

    private List<Long>              nameLabels = new ArrayList<>();

    private Date                    sortTime;

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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRichDesc() {
        return richDesc;
    }

    public void setRichDesc(String richDesc) {
        this.richDesc = richDesc;
    }

    public boolean isGift() {
        return isGift;
    }

    public void setGift(boolean gift) {
        isGift = gift;
    }

    public boolean isPresell() {
        return presell;
    }

    public void setPresell(boolean presell) {
        this.presell = presell;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ItemImageInfo> getImageList() {
        return imageList;
    }

    public void setImageList(List<ItemImageInfo> imageList) {
        this.imageList = imageList;
    }

    public List<ItemSkuNameValues> getSkuNameValues() {
        return skuNameValues;
    }

    public void setSkuNameValues(List<ItemSkuNameValues> skuNameValues) {
        this.skuNameValues = skuNameValues;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public List<ItemSkuInfo> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<ItemSkuInfo> skuList) {
        this.skuList = skuList;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getUnitCanChange() {
        return unitCanChange;
    }

    public void setUnitCanChange(int unitCanChange) {
        this.unitCanChange = unitCanChange;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }

    public List<Long> getNameLabels() {
        return nameLabels;
    }

    public void setNameLabels(List<Long> nameLabels) {
        this.nameLabels = nameLabels;
    }

    public Date getSortTime() {
        return sortTime;
    }

    public void setSortTime(Date sortTime) {
        this.sortTime = sortTime;
    }
}
