package com.chengzi.apiunion.procurement.admin.web.formbean.expressfeetemplate;

import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;

/**
 * @author 月汐
 * @date 2018/10/19 14:37
 */
public class GetExpressFeeTemplateByIdForm extends BaseForm {

    @Min(value = 1, message = "请选择正确的物流模板")
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
