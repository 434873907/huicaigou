package com.chengzi.apiunion.procurement.admin.web.formbean.inventory;

import com.chengzi.common.web.formbean.BasePageForm;

import java.util.Set;

/**
 * @author 月汐
 * @date 2018/12/18 16:49
 */
public class QueryInventoryForm extends BasePageForm {

    private Set<Long> supplierIds;

    private Set<Long> brandIds;

    /**
     * {@link com.chengzi.apiunion.inventory.enums.InventoryKeywordTypeEnum}
     */
    private int keywordType;

    private String keyword;

    private Integer channelType;

    private int status;

    public Set<Long> getSupplierIds() {
        return supplierIds;
    }

    public void setSupplierIds(Set<Long> supplierIds) {
        this.supplierIds = supplierIds;
    }

    public Set<Long> getBrandIds() {
        return brandIds;
    }

    public void setBrandIds(Set<Long> brandIds) {
        this.brandIds = brandIds;
    }

    public int getKeywordType() {
        return keywordType;
    }

    public void setKeywordType(int keywordType) {
        this.keywordType = keywordType;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getKeywordValue() {
        return keyword;
    }

    public void setKeywordValue(String keywordValue) {
        this.keyword = keywordValue;
    }

    public Integer getChannelType() {
        return channelType;
    }

    public void setChannelType(Integer channelType) {
        this.channelType = channelType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
