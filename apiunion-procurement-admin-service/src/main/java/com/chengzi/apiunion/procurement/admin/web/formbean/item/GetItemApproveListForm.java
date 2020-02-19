/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.formbean.item;

import com.chengzi.common.web.formbean.BasePageForm;

import net.sf.oval.constraint.Min;

/**
 * @author Kolor
 */
public class GetItemApproveListForm extends BasePageForm {

    /**
     * 商品ID
     */
    @Min(value = 1, message = "商品ID不能为空")
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
