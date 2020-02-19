package com.chengzi.apiunion.procurement.admin.web.formbean.item;

import com.chengzi.apiunion.item.pojo.ItemSkuInfo;
import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.MinSize;
import net.sf.oval.constraint.NotNull;
import org.summercool.web.annotation.JsonField;

import java.util.List;

/**
 * @author 行者
 */
public class FastApprovedForm extends BaseForm {

    private long itemId;

    @MinSize(value = 1, message = "规格未设置")
    @NotNull(message = "规格未设置")
    @JsonField
    private List<ItemSkuInfo> skuList;

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public List<ItemSkuInfo> getSkuList() {
        return skuList;
    }

    public void setSkuList(List<ItemSkuInfo> skuList) {
        this.skuList = skuList;
    }
}
