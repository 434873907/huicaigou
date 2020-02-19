/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.formbean.item;

import java.util.List;
import java.util.Set;

import org.summercool.web.annotation.JsonField;

import com.chengzi.apiunion.common.data.enums.CurrencyEnum;
import com.chengzi.apiunion.item.enums.ItemStatusEnum;
import com.chengzi.apiunion.item.pojo.ItemImageInfo;
import com.chengzi.apiunion.item.pojo.ItemSkuInfo;
import com.chengzi.apiunion.item.pojo.ItemSkuNameValues;
import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;
import com.chengzi.common.web.formbean.BaseForm;

import net.sf.oval.constraint.MaxLength;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.MinSize;
import net.sf.oval.constraint.NotNull;

/**
 * @author Kolor
 *
 */
public class AddItemForm extends BaseForm {

    /**
    * 商品名称
    */
    @NotBlankAndNull(message = "商品名称未填写")
    @MaxLength(value = 255, message = "商品名称不能超过255字符")
    private String                  name;

    /**
     * 商品外文名称
     */
    @MaxLength(value = 255, message = "外文名称不能超过255字符")
    private String                  originalName;

    /**
    * 商品品牌
    */
    @Min(value = 1, message = "品牌未选择")
    private long                    brandId;
    /**
     * 店铺
     */
    @Min(value = 1, message = "店铺未选择")
    private long                    shopId;

    /**
    * 商品描述
    */
    @MaxLength(value = 65535, message = "商品描述不能超过65535字符")
    private String                  desc;

    /**
    * 温馨提示
    */
    @MaxLength(value = 255, message = "商品描述不能超过255字符")
    private String                  info;

    /**
    * 图文详情
    */
    @MaxLength(value = 65535, message = "图文详情不能超过65535字符")
    private String                  richDesc;

    /**
    * 品类编号
    */
    //    @Min(value = 1, message = "类目未设置")
    private long                    categoryId;

    /**
    * 商品规格名称及可选值
    */
    // @MinSize(value = 1, message = "商品规格名称未设置")
    // @NotNull(message = "商品规格名称未设置")
    @JsonField
    private List<ItemSkuNameValues> skuNameValues;

    /**
     * 货币
     */
    @NotNull(message = "货币未选择")
    private CurrencyEnum            currency;

    /**
     * 图片
     */
    @JsonField
    @MinSize(value = 1, message = "请至少为商品添加一张图片")
    @NotNull(message = "图片未设置")
    private Set<ItemImageInfo>      addImages;

    /**
     * 上架状态（1：上架，2：下架）
     * {@link ItemStatusEnum}
     */
    private ItemStatusEnum          status = ItemStatusEnum.ONLINE;

    @MinSize(value = 1, message = "规格未设置")
    @NotNull(message = "规格未设置")
    @JsonField
    private List<ItemSkuInfo>       skuList;

    /**
     * 是否为赠品
     */
    private boolean                 isGift;

    /**
     * 是否预售
     */
    private boolean                 presell;

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

    /**
     * 商品名标签
     */
    private String                  nameLabels;

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getRichDesc() {
        return richDesc;
    }

    public void setRichDesc(String richDesc) {
        this.richDesc = richDesc;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public List<ItemSkuNameValues> getSkuNameValues() {
        return skuNameValues;
    }

    public void setSkuNameValues(List<ItemSkuNameValues> skuNameValues) {
        this.skuNameValues = skuNameValues;
    }

    public CurrencyEnum getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyEnum currency) {
        this.currency = currency;
    }

    public Set<ItemImageInfo> getAddImages() {
        return addImages;
    }

    public void setAddImages(Set<ItemImageInfo> addImages) {
        this.addImages = addImages;
    }

    public ItemStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ItemStatusEnum status) {
        this.status = status;
    }

    public List<ItemSkuInfo> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<ItemSkuInfo> skuList) {
        this.skuList = skuList;
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

    public String getNameLabels() {
        return nameLabels;
    }

    public void setNameLabels(String nameLabels) {
        this.nameLabels = nameLabels;
    }
}
