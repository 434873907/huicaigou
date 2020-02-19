package com.chengzi.apiunion.procurement.admin.web.pojo.partnercategory;

import com.chengzi.apiunion.partnercategory.pojo.PartnerCategoryDO;

import java.util.List;
import java.util.Map;

/**
 * @author 月汐
 * @date 2018/11/6 10:22
 */
public class PartnerCategoryBO {

    private long id;

    private long systemCategoryId;

    private String name;

    private String logoUrl;

    private int propStatus;

    private int status;

    private int depth;

    private long parentId;

    private long count;

    private List<PartnerCategoryBO> children;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSystemCategoryId() {
        return systemCategoryId;
    }

    public void setSystemCategoryId(long systemCategoryId) {
        this.systemCategoryId = systemCategoryId;
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

    public int getPropStatus() {
        return propStatus;
    }

    public void setPropStatus(int propStatus) {
        this.propStatus = propStatus;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<PartnerCategoryBO> getChildren() {
        return children;
    }

    public void setChildren(List<PartnerCategoryBO> children) {
        this.children = children;
    }

    public static PartnerCategoryBO convert(PartnerCategoryDO partnerCategoryDO, List<PartnerCategoryBO> children, boolean hasCount, Map<Long, Long> categoryIdCountMap) {
        PartnerCategoryBO bo = new PartnerCategoryBO();
        bo.setId(partnerCategoryDO.getId());
        bo.setDepth(partnerCategoryDO.getDepth());
        bo.setLogoUrl(partnerCategoryDO.getLogoUrl());
        bo.setName(partnerCategoryDO.getName());
        bo.setParentId(partnerCategoryDO.getParentId());
        bo.setPropStatus(partnerCategoryDO.getPropStatus());
        bo.setStatus(partnerCategoryDO.getStatus());
        bo.setChildren(children);
        if (hasCount) {
            bo.setCount(categoryIdCountMap.get(partnerCategoryDO.getId()) == null ? 0 : categoryIdCountMap.get(partnerCategoryDO.getId()));
        } else {
            bo.setCount(0);
        }
        return bo;
    }

}
