package com.chengzi.apiunion.procurement.admin.web.formbean.item;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.summercool.web.annotation.JsonField;

import com.chengzi.apiunion.item.enums.ItemApproveTypeEnum;
import com.chengzi.common.web.formbean.BaseForm;

import net.sf.oval.constraint.Min;

/**
 * @author 行者
 */
public class GetItemForFastApprovedForm extends BaseForm {

    private long                                 itemId;

    @JsonField
    private List<Long>                           exceptItemId;

    /**
     * 方向，0上一个，1下一个
     */
    private int                                  direction;

    @JsonField
    private List<Integer>                        statusList;

    private Long                                 cateId;

    private Set<Long>                            supplierIds;

    private Set<Long>                            shopIds;

    private Set<Long>                            brandIds;

    private Set<Integer>                         itemChannelTypes;

    private String                               keyword;
    /**
     * 关键字类型，1：商品ID，2：商品名称，3：UPC
     */
    private ItemListForm.ItemListKeywordTypeEnum keywordType;

    /**
     * 价格区间
     */
    @Min(value = 0, message = "请输入正确金额")
    private BigDecimal                           minPrice;

    @Min(value = 0, message = "请输入正确金额")
    private BigDecimal                           maxPrice;

    /**
     * 供应商下架类型
     */
    private int                                  supplierOfflineType;

    /**
     * 审核类型
     * @see ItemApproveTypeEnum
     */
    private int                                  approveType;

    /**
     * 用于排序的字段
     */
    private Date                                 sortTime;

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public List<Integer> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<Integer> statusList) {
        this.statusList = statusList;
    }

    public Long getCateId() {
        return cateId;
    }

    public void setCateId(Long cateId) {
        this.cateId = cateId;
    }

    public Set<Long> getSupplierIds() {
        return supplierIds;
    }

    public void setSupplierIds(Set<Long> supplierIds) {
        this.supplierIds = supplierIds;
    }

    public Set<Long> getShopIds() {
        return shopIds;
    }

    public void setShopIds(Set<Long> shopIds) {
        this.shopIds = shopIds;
    }

    public Set<Long> getBrandIds() {
        return brandIds;
    }

    public void setBrandIds(Set<Long> brandIds) {
        this.brandIds = brandIds;
    }

    public Set<Integer> getItemChannelTypes() {
        return itemChannelTypes;
    }

    public void setItemChannelTypes(Set<Integer> itemChannelTypes) {
        this.itemChannelTypes = itemChannelTypes;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public ItemListForm.ItemListKeywordTypeEnum getKeywordType() {
        return keywordType;
    }

    public void setKeywordType(ItemListForm.ItemListKeywordTypeEnum keywordType) {
        this.keywordType = keywordType;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public int getSupplierOfflineType() {
        return supplierOfflineType;
    }

    public void setSupplierOfflineType(int supplierOfflineType) {
        this.supplierOfflineType = supplierOfflineType;
    }

    public int getApproveType() {
        return approveType;
    }

    public void setApproveType(int approveType) {
        this.approveType = approveType;
    }

    public Date getSortTime() {
        return sortTime;
    }

    public void setSortTime(Date sortTime) {
        this.sortTime = sortTime;
    }

    public List<Long> getExceptItemId() {
        return exceptItemId;
    }

    public void setExceptItemId(List<Long> exceptItemId) {
        this.exceptItemId = exceptItemId;
    }
}
