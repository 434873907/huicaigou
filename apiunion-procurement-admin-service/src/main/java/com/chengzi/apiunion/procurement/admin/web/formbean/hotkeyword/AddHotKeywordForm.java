package com.chengzi.apiunion.procurement.admin.web.formbean.hotkeyword;

import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;
import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.NotNull;

/**
 * @author 月汐
 * @date 2018/12/4 14:21
 */
public class AddHotKeywordForm extends BaseForm {

    @NotBlankAndNull(message = "关键词名称不能为空")
    private String name;

    private Integer jumpType;

    @NotBlankAndNull(message = "跳转链接不能为空")
    private String jumpValue;

    private Integer status;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
