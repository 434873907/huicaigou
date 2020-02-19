package com.chengzi.apiunion.procurement.admin.web.formbean.hotkeyword;

import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;
import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.NotNull;

/**
 * @author 月汐
 * @date 2018/12/4 14:46
 */
public class UpdateHotKeywordStatusForm extends BaseForm {

    @Min(value = 1, message = "请选择正确的热门关键词")
    private long id;

    @NotNull(message = "请选择关键词状态")
    private Integer status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
