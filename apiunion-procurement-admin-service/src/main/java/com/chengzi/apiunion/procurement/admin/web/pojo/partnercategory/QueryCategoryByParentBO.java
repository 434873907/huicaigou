package com.chengzi.apiunion.procurement.admin.web.pojo.partnercategory;

import com.chengzi.apiunion.partnercategory.constant.PartnerCategoryConstant;
import com.chengzi.apiunion.partnercategory.pojo.PartnerCategoryDO;
import com.chengzi.common.data.beans.JsonPojo;

/**
 * @author 月汐
 * @date 2018/10/15 11:14
 */
public class QueryCategoryByParentBO extends JsonPojo {

    private Long id;

    private String name;

    private Integer depth;

    private Long count;

    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static QueryCategoryByParentBO convert(PartnerCategoryDO partnerCategoryDO, long count) {
        QueryCategoryByParentBO bo = new QueryCategoryByParentBO();
        bo.setId(partnerCategoryDO.getId());
        bo.setDepth(partnerCategoryDO.getDepth());
        bo.setName(partnerCategoryDO.getName());
        bo.setCount(count);
        if (PartnerCategoryConstant.STATUS_NOT_IN_USE == partnerCategoryDO.getStatus()) {
            bo.setStatus("已禁用");
        }
        return bo;
    }

}
