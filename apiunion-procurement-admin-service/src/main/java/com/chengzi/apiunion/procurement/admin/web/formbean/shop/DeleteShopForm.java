package com.chengzi.apiunion.procurement.admin.web.formbean.shop;

import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;

/**
 * @author 月汐
 * @date 2018/11/15 17:24
 */
public class DeleteShopForm extends BaseForm {

    @Min(value = 1, message = "请选择正确的店铺")
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
