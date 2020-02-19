/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.pojo.supplier;

import com.chengzi.apiunion.common.data.enums.CurrencyEnum;
import com.chengzi.apiunion.common.module.image.annotation.ImageDecorater;
import com.chengzi.apiunion.common.module.image.enums.ImageBizType;
import com.chengzi.apiunion.item.enums.ItemStatusEnum;
import com.chengzi.common.data.beans.JsonPojo;

import java.util.Date;
import java.util.Set;

/**
 * @author zhiyuan
 * A端
 */
public class SupplierItemListBO extends JsonPojo {
    private long        id;
//    private Date        createTime;
    private Date        modifyTime;
//    private boolean     deleted;

    /**
     * 商品标题
     */
    private String      name;

    /**
     * 商品原始标题
     */
//    private String      originalName;

    /**
     * 品牌ID
     */
    private long        brandId;
    /**
     * 品牌名称
     */
    private String      brandName;

    /**
     * 类目
     */
    private long        cateId;
    private String      cateName;
    /**
     * 状态，0：初始，1：上架，2：下上架
     * {@link ItemStatusEnum}
     */
    private int         status;

    /**
     * 供应商商品类型：1模板，2自建商品
     */
    private Integer type;
    /**
     * 库存状态
     */
    private Integer stockStatus;
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

//    private String      price;

    /**
     * 发货渠道
     */
    private Set<String> channels;

    /**
     * 供应商名称
     */
    private String supplierName;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public Integer getStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(Integer stockStatus) {
        this.stockStatus = stockStatus;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
}
