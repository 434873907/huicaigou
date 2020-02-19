/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.formbean.template;

import com.chengzi.apiunion.item.pojo.ItemSkuNameValues;
import com.chengzi.apiunion.supplier.common.data.beans.SupplierItemImageInfo;
import com.chengzi.apiunion.supplier.common.data.beans.SupplierItemSkuInfo;
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
public class DeleteTemplateItemForm extends BaseForm {

    @Min(value = 1, message = "商品ID未填写")
    private long templateItemId;


    public long getTemplateItemId() {
        return templateItemId;
    }

    public void setTemplateItemId(long templateItemId) {
        this.templateItemId = templateItemId;
    }
}
