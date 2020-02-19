/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.formbean.template;

import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;

/**
 * @author zhiyuan
 */
public class DeleteItemSkuCheckForm extends BaseForm {

    @Min(value = 1, message = "商品ID未填写")
    private long templateItemId;
    @Min(value = 1, message = "商品SkuID未填写")
    private long templateItemSkuId;


    public long getTemplateItemId() {
        return templateItemId;
    }

    public void setTemplateItemId(long templateItemId) {
        this.templateItemId = templateItemId;
    }

    public long getTemplateItemSkuId() {
        return templateItemSkuId;
    }

    public void setTemplateItemSkuId(long templateItemSkuId) {
        this.templateItemSkuId = templateItemSkuId;
    }
}
