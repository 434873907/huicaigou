package com.chengzi.apiunion.procurement.admin.web.formbean.item;

import com.chengzi.common.web.formbean.BaseForm;
import org.summercool.web.annotation.JsonField;

import java.util.List;
import java.util.Set;

/**
 * @author 月汐
 * @date 2019/07/10 17:26
 */
public class MergeItemGroupForm extends BaseForm {

    @JsonField
    private List<Long> groupIds;

    @JsonField
    private Set<Long> itemIds;

    public List<Long> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(List<Long> groupIds) {
        this.groupIds = groupIds;
    }

    public Set<Long> getItemIds() {
        return itemIds;
    }

    public void setItemIds(Set<Long> itemIds) {
        this.itemIds = itemIds;
    }
}
