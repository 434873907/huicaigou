package com.chengzi.apiunion.procurement.admin.web.formbean.pcconfig.tab;

import com.chengzi.common.web.formbean.BaseForm;

import net.sf.oval.constraint.Min;

/**
 * @author 苏子
 * @date 2019年1月18日
 */
public class UpdateTabConfigStatusForm extends BaseForm {
    @Min(value = 1, message = "数据不存在")
    private long id;

    private int  status = 0;

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
