package com.chengzi.apiunion.procurement.admin.web.pojo.partnercategory;

import com.chengzi.apiunion.partnercategory.pojo.PartnerCategoryDO;
import com.chengzi.common.data.beans.JsonPojo;

/**
 * @author 月汐
 * @date 2018/10/15 10:13
 */
public class GetCategoryByIdBO extends JsonPojo {

    private Long id;

    private Long firstClassCategoryId;

    private Long secondClassCategoryId;

    private String name;

    private String logoUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFirstClassCategoryId() {
        return firstClassCategoryId;
    }

    public void setFirstClassCategoryId(Long firstClassCategoryId) {
        this.firstClassCategoryId = firstClassCategoryId;
    }

    public Long getSecondClassCategoryId() {
        return secondClassCategoryId;
    }

    public void setSecondClassCategoryId(Long secondClassCategoryId) {
        this.secondClassCategoryId = secondClassCategoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public static GetCategoryByIdBO convert(PartnerCategoryDO partnerCategoryDO, Long firstClassCategoryId, Long secondClassCategoryId) {
        GetCategoryByIdBO bo = new GetCategoryByIdBO();
        bo.setFirstClassCategoryId(firstClassCategoryId);
        bo.setSecondClassCategoryId(secondClassCategoryId);
        bo.setId(partnerCategoryDO.getId());
        bo.setName(partnerCategoryDO.getName());
        bo.setLogoUrl(partnerCategoryDO.getLogoUrl());
        return bo;
    }

}
