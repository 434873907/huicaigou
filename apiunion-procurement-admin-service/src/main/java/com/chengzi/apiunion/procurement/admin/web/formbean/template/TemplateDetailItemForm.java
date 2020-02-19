/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.formbean.template;

import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.MinSize;
import net.sf.oval.constraint.NotNull;

import java.util.List;

/**
 * @author zhiyuan
 */
public class TemplateDetailItemForm extends BaseForm {

    @Min(value = 1, message = "商品ID未填写")
    private long templateItemId;

    public long getTemplateItemId() {
        return templateItemId;
    }

    public void setTemplateItemId(long templateItemId) {
        this.templateItemId = templateItemId;
    }
}
