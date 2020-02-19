package com.chengzi.apiunion.procurement.admin.web.pojo.priceapprove;

import com.chengzi.common.data.beans.JsonPojo;

/**
 * @author 行者
 */
public class CategoryApproveCountBO extends JsonPojo {

    private long        cateId;

    private long        count;

    private String      cateName;

    public long getCateId() {
        return cateId;
    }

    public void setCateId(long cateId) {
        this.cateId = cateId;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }
}