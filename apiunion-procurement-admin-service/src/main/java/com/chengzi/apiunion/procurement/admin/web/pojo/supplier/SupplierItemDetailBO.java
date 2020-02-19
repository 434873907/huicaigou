/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.pojo.supplier;

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
public class SupplierItemDetailBO extends JsonPojo {
    private long                      id;
    private Date                      createTime;
    private Date                      modifyTime;
    private boolean                   deleted;
    private long                      supplierId;
    private String                    supplierName;

    private String                    name;
    private String                    originalName;

    private long                      brandId;
    private String                    brandName;

    //类目ID
    private long cateId;
    //类目名称
    private String cateName;
    private long                      cateId1;
    private String                    cateName1;
    private long                      cateId2;
    private String                    cateName2;
    private long                      cateId3;
    private String                    cateName3;

    private String                    itemModel;
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
    /**
     * 货币单位
     */
    private int                       currency;
    /**
     * 商品单位
     */
    private String unit;

    private List<SupplierItemSkuInfo> skuList;

    /**
     * 是否来自模板
     */
    private boolean fromTemplate;
    /**
     * 如果来自模板 设置模板值
     */
    private long templateItemId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getItemModel() {
        return itemModel;
    }

    public void setItemModel(String itemModel) {
        this.itemModel = itemModel;
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

    public long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(long supplierId) {
        this.supplierId = supplierId;
    }

    public boolean isFromTemplate() {
        return fromTemplate;
    }

    public void setFromTemplate(boolean fromTemplate) {
        this.fromTemplate = fromTemplate;
    }

    public long getTemplateItemId() {
        return templateItemId;
    }

    public void setTemplateItemId(long templateItemId) {
        this.templateItemId = templateItemId;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
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
}
