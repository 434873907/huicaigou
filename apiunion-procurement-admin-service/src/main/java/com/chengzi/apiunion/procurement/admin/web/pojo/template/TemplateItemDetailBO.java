/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.pojo.template;

import com.chengzi.apiunion.item.pojo.ItemSkuNameValues;
import com.chengzi.apiunion.supplier.common.data.beans.SupplierItemImageInfo;
import com.chengzi.apiunion.supplier.common.data.beans.SupplierItemSkuInfo;
import com.chengzi.common.data.beans.JsonPojo;

import java.util.Date;
import java.util.List;

/**
 * @author zhiyuan
 *
 */
public class TemplateItemDetailBO extends JsonPojo {
    private long                      id;
    private Date                      createTime;
    private Date                      modifyTime;
    private boolean                   deleted;
//    private long                      supplierId;

    private String                    name;
    private String                    originalName;

    private long                      brandId;
    private String                    brandName;

    //商品型号
    private String  itemModel;

    private long                      cateId;
    private String                    cateName;
//    private long                      cateId1;
//    private String                    cateName1;
//    private long                      cateId2;
//    private String                    cateName2;
//    private long                      cateId3;
//    private String                    cateName3;

    private String                    desc;

    private String                    richDesc;

    /**
     * @see com.chengzi.apiunion.item.enums.ItemStatusEnum
     */
    private int                       status;

    private List<SupplierItemImageInfo>       imageList;

    /**
    * 商品规格名称及可选值
    */
    private List<ItemSkuNameValues>   skuNameValues;
    private String unit;
    /**
     * 货币单位
     */
    private int                       currency;

    private List<SupplierItemSkuInfo> skuList;

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<SupplierItemImageInfo> getImageList() {
        return imageList;
    }

    public void setImageList(List<SupplierItemImageInfo> imageList) {
        this.imageList = imageList;
    }

    public List<ItemSkuNameValues> getSkuNameValues() {
        return skuNameValues;
    }

    public void setSkuNameValues(List<ItemSkuNameValues> skuNameValues) {
        this.skuNameValues = skuNameValues;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public List<SupplierItemSkuInfo> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<SupplierItemSkuInfo> skuList) {
        this.skuList = skuList;
    }

    public String getItemModel() {
        return itemModel;
    }

    public void setItemModel(String itemModel) {
        this.itemModel = itemModel;
    }
}
