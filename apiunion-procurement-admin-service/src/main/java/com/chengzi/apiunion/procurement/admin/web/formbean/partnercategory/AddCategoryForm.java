package com.chengzi.apiunion.procurement.admin.web.formbean.partnercategory;

import com.chengzi.apiunion.partnercategory.pojo.PartnerCategoryDO;
import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;
import com.chengzi.common.web.formbean.BaseForm;

/**
 * @author 月汐
 * @date 2018/10/12 14:52
 */
public class AddCategoryForm extends BaseForm {

    private Long parentId;

    @NotBlankAndNull(message = "请输入类目名")
    private String name;

    @NotBlankAndNull(message = "请选择类目层级")
    private Integer depth;

    private String logoUrl;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
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

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public static PartnerCategoryDO convert(AddCategoryForm form) {
        PartnerCategoryDO partnerCategoryDO = new PartnerCategoryDO();
        partnerCategoryDO.setParentId(form.getParentId());
        partnerCategoryDO.setName(form.getName());
        partnerCategoryDO.setLogoUrl(form.getLogoUrl());
        partnerCategoryDO.setDepth(form.getDepth());
        return partnerCategoryDO;
    }

}
