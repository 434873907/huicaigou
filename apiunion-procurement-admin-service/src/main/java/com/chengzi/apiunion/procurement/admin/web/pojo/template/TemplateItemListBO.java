/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.pojo.template;

import com.chengzi.apiunion.common.data.enums.CurrencyEnum;
import com.chengzi.apiunion.common.module.image.annotation.ImageDecorater;
import com.chengzi.apiunion.common.module.image.enums.ImageBizType;
import com.chengzi.apiunion.item.enums.ItemStatusEnum;
import com.chengzi.common.data.beans.JsonPojo;

import java.util.Date;
import java.util.Set;

/**
 * @author zhiyuan
 * A端 模板商品列表Bean
 */
public class TemplateItemListBO extends JsonPojo {
    private long        id;
    private Date        modifyTime;
    /**
     * 商品标题
     */
    private String      name;
    /**
     * 品牌ID
     */
    private long        brandId;
    /**
     * 品牌名称
     */
    private String      brandName;

    private long cateId;
    private String cateName;
    /**
     * 一级类目
     */
//    private long        cateId1;
//    private String      cateName1;

    /**
     * 二级类目
     */
//    private long        cateId2;
//    private String      cateName2;

    /**
     * 三级类目
     */
//    private long        cateId3;
//    private String      cateName3;

    /**
     * 状态，0：初始，1：上架，2：下上架
     * {@link ItemStatusEnum}
     */
    private int         status;

    /**
     * 库存状态
     */
    private int stockStatus;
    /**
     * 货币
     * {@link CurrencyEnum}
     */
//    private int         currency;

    /**
     * 主图
     */
    @ImageDecorater(ImageBizType.ADMIN_ITEM_LIST)
    private String      mainImageUrl;

//    private Set<String> skuList;

    private String      price;

    /**
     * 发货渠道
     */
    private Set<String> channels;

    /**
     * 供应商数量
     */
    private int supplierNum;

//    private long        groupId;

    /**
     * 组装规格值
     */
    private String skuListStr;

    private Date        onlineTime;

    private Date        offlineTime;

    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public void setMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
    }

//    public String getCateName1() {
//        return cateName1;
//    }

//    public void setCateName1(String cateName1) {
//        this.cateName1 = cateName1;
//    }

//    public String getCateName2() {
//        return cateName2;
//    }

//    public void setCateName2(String cateName2) {
//        this.cateName2 = cateName2;
//    }

//    public String getCateName3() {
//        return cateName3;
//    }

//    public void setCateName3(String cateName3) {
//        this.cateName3 = cateName3;
//    }

//    public Set<String> getSkuList() {
//        return skuList;
//    }

    public String getSkuListStr() {
        return skuListStr;
    }

    public void setSkuListStr(String skuListStr) {
        this.skuListStr = skuListStr;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCateId() {
        return cateId;
    }

    public void setCateId(long cateId) {
        this.cateId = cateId;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Set<String> getChannels() {
        return channels;
    }

    public void setChannels(Set<String> channels) {
        this.channels = channels;
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


    public int getStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(int stockStatus) {
        this.stockStatus = stockStatus;
    }

    public int getSupplierNum() {
        return supplierNum;
    }

    public void setSupplierNum(int supplierNum) {
        this.supplierNum = supplierNum;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
