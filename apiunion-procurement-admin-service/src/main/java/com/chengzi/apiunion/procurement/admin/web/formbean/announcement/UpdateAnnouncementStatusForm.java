package com.chengzi.apiunion.procurement.admin.web.formbean.announcement;

import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Max;
import net.sf.oval.constraint.Min;

/**
 * @author 月汐
 * @date 2018/11/14 14:08
 */
public class UpdateAnnouncementStatusForm extends BaseForm {

    @Min(value = 1, message = "请选择正确的公告")
    private long id;

    @Min(value = 0, message = "请选择正确的公告状态")
    @Max(value = 1, message = "请选择正确的公告状态")
    private int status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
