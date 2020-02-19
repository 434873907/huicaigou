package com.chengzi.apiunion.procurement.admin.web.formbean.item;

import com.chengzi.apiunion.item.enums.ItemLabelTypeEnum;
import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.NotNull;

/**
 * @author 月汐
 * @date 2019/12/19 15:43
 */
public class GetItemLabelsByTypeForm extends BaseForm {

    @NotNull(message = "请选择标签类型")
    private ItemLabelTypeEnum labelType;

    public ItemLabelTypeEnum getLabelType() {
        return labelType;
    }

    public void setLabelType(ItemLabelTypeEnum labelType) {
        this.labelType = labelType;
    }
}
