package com.chengzi.apiunion.procurement.admin.web.formbean.groupbuying;

import net.sf.oval.constraint.Min;

/**
 * @author 月汐
 * @date 2019/2/18 19:23
 */
public class UpdateGroupBuyingForm extends AddGroupBuyingForm {

    @Min(value = 1, message = "请选择需要修改的团购")
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
