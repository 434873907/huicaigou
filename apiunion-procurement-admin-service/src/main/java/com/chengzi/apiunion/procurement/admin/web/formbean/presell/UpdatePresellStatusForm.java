package com.chengzi.apiunion.procurement.admin.web.formbean.presell;

import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;

/**
 * @author 月汐
 * @date 2019/2/25 20:50
 */
public class UpdatePresellStatusForm extends BaseForm {

    @Min(value = 1, message = "请选择需要修改状态的预售")
    private long id;

    @Min(value = 0, message = "请选择合适的状态")
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
