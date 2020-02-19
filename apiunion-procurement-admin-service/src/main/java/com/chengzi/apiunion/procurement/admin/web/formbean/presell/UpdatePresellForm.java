package com.chengzi.apiunion.procurement.admin.web.formbean.presell;

import net.sf.oval.constraint.Min;

/**
 * @author 月汐
 * @date 2019/2/25 20:54
 */
public class UpdatePresellForm extends AddPresellForm {

    @Min(value = 1, message = "请选择需要修改的预售活动")
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
