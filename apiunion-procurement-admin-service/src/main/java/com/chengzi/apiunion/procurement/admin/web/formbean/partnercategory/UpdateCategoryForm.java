package com.chengzi.apiunion.procurement.admin.web.formbean.partnercategory;

import com.chengzi.apiunion.partnercategory.pojo.PartnerCategoryDO;
import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;
import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;

/**
 * @author 月汐
 * @date 2018/10/16 14:36
 */
public class UpdateCategoryForm extends BaseForm {

    @Min(value = 1, message = "请选择正确的类目")
    private long id;

    private Long parentId;

    @NotBlankAndNull(message = "请输入类目名")
    private String name;

    @NotBlankAndNull(message = "请选择类目层级")
    private Integer depth;

    private String logoUrl;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public static PartnerCategoryDO convert(UpdateCategoryForm form) {
        PartnerCategoryDO partnerCategoryDO = new PartnerCategoryDO();
        partnerCategoryDO.setId(form.getId());
        partnerCategoryDO.setDepth(form.getDepth());
        partnerCategoryDO.setParentId(form.getParentId());
        partnerCategoryDO.setName(form.getName());
        partnerCategoryDO.setLogoUrl(form.getLogoUrl());
        return partnerCategoryDO;
    }

}
