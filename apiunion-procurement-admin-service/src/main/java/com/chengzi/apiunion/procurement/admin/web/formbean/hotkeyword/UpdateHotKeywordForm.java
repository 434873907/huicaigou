package com.chengzi.apiunion.procurement.admin.web.formbean.hotkeyword;

import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;
import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.NotNull;

/**
 * @author 月汐
 * @date 2018/12/4 14:40
 */
public class UpdateHotKeywordForm extends BaseForm {

    @Min(value = 1, message = "请选择正确的热门关键词")
    private long id;

    @NotBlankAndNull(message = "请输入关键词名称")
    private String name;

    @NotNull(message = "请选择跳转类型")
    private Integer jumpType;

    @NotBlankAndNull(message = "请输入跳转链接")
    private String jumpValue;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getJumpType() {
        return jumpType;
    }

    public void setJumpType(Integer jumpType) {
        this.jumpType = jumpType;
    }

    public String getJumpValue() {
        return jumpValue;
    }

    public void setJumpValue(String jumpValue) {
        this.jumpValue = jumpValue;
    }
}
