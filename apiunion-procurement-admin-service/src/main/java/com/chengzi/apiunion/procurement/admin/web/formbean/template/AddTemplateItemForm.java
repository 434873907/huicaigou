/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.formbean.template;

import com.chengzi.apiunion.item.pojo.ItemSkuNameValues;
import com.chengzi.apiunion.supplier.common.data.beans.SupplierItemImageInfo;
import com.chengzi.apiunion.supplier.common.data.beans.SupplierItemSkuInfo;
import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;
import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.MaxLength;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.MinSize;
import net.sf.oval.constraint.NotNull;
import org.summercool.web.annotation.JsonField;

import java.util.List;
import java.util.Set;

/**
 * @author zhiyuan
 */
public class AddTemplateItemForm extends BaseForm {

    @NotBlankAndNull(message = "商品名称未填写")
    @MaxLength(value = 255, message = "商品名称不能超过255字符")
    private String name;
    /**
     * 商品品牌
     */
    @Min(value = 1, message = "品牌未选择")
    private Integer brandId;

    @NotBlankAndNull(message = "品牌名未填写")
    private String brandName;


    private Integer currency;
    @NotNull(message = "类目ID不能为空")
    @Min(value = 1, message = "类目ID不能为空")
    private Long categoryId;
    /**
     * 公有库商品ID
     */
//    private Long publicItemId;
            private Integer status;
    @NotBlankAndNull(message = "商品单位未填写")
    private String unit;


    private String itemModel;
    /**
     * 图片
     */
    @JsonField
    @MinSize(value = 1, message = "请至少为商品添加一张图片")
    @NotNull(message = "图片未设置")
    private Set<SupplierItemImageInfo> addImages;

    @JsonField
    private List<ItemSkuNameValues> skuNameValues;

    @MinSize(value = 1, message = "规格未设置")
    @NotNull(message = "规格未设置")
    @JsonField
    private List<SupplierItemSkuInfo>       skuList;

    /**
     * 商品描述
     */
    @MaxLength(value = 65535, message = "商品描述不能超过65535字符")
    private String                  desc;

    /**
     * 图文详情
     */
    @MaxLength(value = 65535, message = "图文详情不能超过65535字符")
    private String                  richDesc;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Integer getCurrency() {
        return currency;
    }

    public void setCurrency(Integer currency) {
        this.currency = currency;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    //    public Long getSupplierTemplateItemId() {
//        return publicItemId;
//    }

//    public void setSupplierTemplateItemId(Long publicItemId) {
//        this.publicItemId = publicItemId;
//    }

    public Set<SupplierItemImageInfo> getAddImages() {
        return addImages;
    }

    public void setAddImages(Set<SupplierItemImageInfo> addImages) {
        this.addImages = addImages;
    }

    public List<ItemSkuNameValues> getSkuNameValues() {
        return skuNameValues;
    }

    public void setSkuNameValues(List<ItemSkuNameValues> skuNameValues) {
        this.skuNameValues = skuNameValues;
    }

    public List<SupplierItemSkuInfo> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<SupplierItemSkuInfo> skuList) {
        this.skuList = skuList;
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getItemModel() {
        return itemModel;
    }

    public void setItemModel(String itemModel) {
        this.itemModel = itemModel;
    }
}
