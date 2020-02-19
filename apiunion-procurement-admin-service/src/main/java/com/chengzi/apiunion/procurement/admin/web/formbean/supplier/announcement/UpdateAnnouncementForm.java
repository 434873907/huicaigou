package com.chengzi.apiunion.procurement.admin.web.formbean.supplier.announcement;

import net.sf.oval.constraint.Min;

/**
 * @author 月汐
 * @date 2020/01/16 16:15
 */
public class UpdateAnnouncementForm extends AddAnnouncementForm {

    @Min(value = 0, message = "请选择需要修改的公告")
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
