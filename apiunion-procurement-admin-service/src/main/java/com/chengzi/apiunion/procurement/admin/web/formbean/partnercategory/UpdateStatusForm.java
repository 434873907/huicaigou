package com.chengzi.apiunion.procurement.admin.web.formbean.partnercategory;

import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Max;
import net.sf.oval.constraint.Min;

/**
 * @author 月汐
 * @date 2018/10/12 16:46
 */
public class UpdateStatusForm extends BaseForm {

    @Min(value = 1, message = "请选择正确的类目")
    private long id;

    @Min(value = 0, message = "请选择正确的状态")
    @Max(value = 1, message = "请选择正确的状态")
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
