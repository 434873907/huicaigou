/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.formbean.template;

import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;
import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.MaxLength;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.MinSize;
import net.sf.oval.constraint.NotNull;
import org.summercool.web.annotation.JsonField;

import java.util.List;
import java.util.Set;

/**
 * @author zhiyuan
 */
public class AddTemplateCategoryForm extends BaseForm {

    /**
     * 类目ID
     */
    private Long cateId;
    /**
     * 类目名称
     */
    @NotBlankAndNull(message = "类目名称未填写")
    @MaxLength(value = 255, message = "类目名称超过255字符")
    private String cateName;

    /**
     * 类目描述
     */
    private String cateDesc;

    /**
     * 类目属性
     */
    @MinSize(value = 1, message = "规格未设置")
    @NotNull(message = "规格未设置")
    @JsonField
    private List<String> cateSkuNames;
    /**
     * 父类目ID
     */
    private long parentId;
    /**
     * 类目类型：1标品，2非标品
     */
    @NotNull(message = "类目类型未选择")
    @Min(value = 1, message = "类目类型未选择")
    private Integer type;

    public Long getCateId() {
        return cateId;
    }

    public void setCateId(Long cateId) {
        this.cateId = cateId;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String getCateDesc() {
        return cateDesc;
    }

    public void setCateDesc(String cateDesc) {
        this.cateDesc = cateDesc;
    }

    public List<String> getCateSkuNames() {
        return cateSkuNames;
    }

    public void setCateSkuNames(List<String> cateSkuNames) {
        this.cateSkuNames = cateSkuNames;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
