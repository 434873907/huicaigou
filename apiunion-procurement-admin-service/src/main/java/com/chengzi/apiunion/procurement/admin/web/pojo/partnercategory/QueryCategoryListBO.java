package com.chengzi.apiunion.procurement.admin.web.pojo.partnercategory;

import com.chengzi.apiunion.common.module.image.annotation.ImageDecorater;
import com.chengzi.apiunion.common.module.image.enums.ImageBizType;
import com.chengzi.apiunion.common.module.image.util.ImageUtil;
import com.chengzi.apiunion.partnercategory.constant.PartnerCategoryConstant;
import com.chengzi.apiunion.partnercategory.pojo.PartnerCategoryDO;
import com.chengzi.common.data.beans.JsonPojo;

import java.util.List;

/**
 * @author 月汐
 * @date 2018/10/12 14:12
 */
public class QueryCategoryListBO extends JsonPojo {

    private Long id;

    private String name;

    private Long parentId;

    private String parentName;

    private Integer status;

    @ImageDecorater(ImageBizType.ADMIN_CATE)
    private String logoUrl;

    private String children;

    private Integer childrenNum;

    private String hierarchy;

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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
    }

    public Integer getChildrenNum() {
        return childrenNum;
    }

    public void setChildrenNum(Integer childrenNum) {
        this.childrenNum = childrenNum;
    }

    public String getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(String hierarchy) {
        this.hierarchy = hierarchy;
    }

    public static QueryCategoryListBO convert(PartnerCategoryDO self, List<PartnerCategoryDO> childrenList,
                                              PartnerCategoryDO parent, String hierarchy) {
        QueryCategoryListBO categoryByDepthBO = new QueryCategoryListBO();
        categoryByDepthBO.setId(self.getId());
        categoryByDepthBO.setName(self.getName());
        categoryByDepthBO.setParentId(self.getParentId());
        categoryByDepthBO.setStatus(self.getStatus());
        categoryByDepthBO.setLogoUrl(self.getLogoUrl());

        if (childrenList != null && childrenList.size() > 0) {
            StringBuilder sb = new StringBuilder();
            for (PartnerCategoryDO partnerCategoryDO : childrenList) {
                sb.append(partnerCategoryDO.getName() + ",");
            }
            if (sb.length() > 0) {
                categoryByDepthBO.setChildren(sb.deleteCharAt(sb.length() - 1).toString());
            }
            categoryByDepthBO.setChildrenNum(childrenList.size());
        }

        if (parent != null) {
            categoryByDepthBO.setParentName(parent.getName());
            categoryByDepthBO.setHierarchy(hierarchy);
        }
        return categoryByDepthBO;
    }

}
