/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.formbean.item;

import com.chengzi.apiunion.item.enums.ItemStatusEnum;
import com.chengzi.common.web.formbean.IdsForm;

import net.sf.oval.constraint.NotNull;

/**
 * @author Kolor
 */
public class UpdateItemStatusForm extends IdsForm {
    /**
     * 状态
     */
    @NotNull(message = "商品状态未设置")
    private ItemStatusEnum status;

    public ItemStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ItemStatusEnum status) {
        this.status = status;
    }

}
