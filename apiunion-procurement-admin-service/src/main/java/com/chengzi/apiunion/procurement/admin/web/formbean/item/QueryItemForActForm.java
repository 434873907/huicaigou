package com.chengzi.apiunion.procurement.admin.web.formbean.item;

import com.chengzi.common.web.formbean.BasePageForm;

/**
 * @author 月汐
 * @date 2019/2/20 11:15
 */
public class QueryItemForActForm extends BasePageForm {

    private Long brandId;

    private Long cateId;

    private String keyword;

    private boolean isGift;

    private Boolean presell;

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Long getCateId() {
        return cateId;
    }

    public void setCateId(Long cateId) {
        this.cateId = cateId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public boolean isGift() {
        return isGift;
    }

    public void setGift(boolean gift) {
        isGift = gift;
    }

    public Boolean isPresell() {
        return presell;
    }

    public void setPresell(Boolean presell) {
        this.presell = presell;
    }
}
