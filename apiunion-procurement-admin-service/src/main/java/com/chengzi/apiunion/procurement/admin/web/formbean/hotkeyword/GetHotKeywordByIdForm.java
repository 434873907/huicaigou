package com.chengzi.apiunion.procurement.admin.web.formbean.hotkeyword;

import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;

/**
 * @author 月汐
 * @date 2018/12/4 14:49
 */
public class GetHotKeywordByIdForm extends BaseForm {

    @Min(value = 1, message = "请选择正确的关键词")
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
