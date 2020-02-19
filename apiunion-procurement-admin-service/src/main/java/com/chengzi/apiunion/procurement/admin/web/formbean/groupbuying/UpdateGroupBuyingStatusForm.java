package com.chengzi.apiunion.procurement.admin.web.formbean.groupbuying;

import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;

/**
 * @author 月汐
 * @date 2019/2/18 19:30
 */
public class UpdateGroupBuyingStatusForm extends BaseForm {

    @Min(value = 1, message = "请选择需要修改的团购活动")
    private long id;

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
