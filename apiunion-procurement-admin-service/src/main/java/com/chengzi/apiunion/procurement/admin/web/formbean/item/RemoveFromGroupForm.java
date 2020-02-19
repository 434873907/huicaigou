package com.chengzi.apiunion.procurement.admin.web.formbean.item;

import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.MinSize;
import org.summercool.web.annotation.JsonField;

import java.util.List;

/**
 * @author 月汐
 * @date 2019/07/11 11:22
 */
public class RemoveFromGroupForm extends BaseForm {

    @JsonField
    @MinSize(value = 1, message = "请选择移除的商品")
    private List<Long> itemIds;

    public List<Long> getItemIds() {
        return itemIds;
    }

    public void setItemIds(List<Long> itemIds) {
        this.itemIds = itemIds;
    }
}
