package com.chengzi.apiunion.procurement.admin.web.formbean.activity;

import com.chengzi.common.web.formbean.BaseForm;

import net.sf.oval.constraint.Min;

/**
 * @author 苏子
 * @date 2018年11月21日
 */
public class UpdateActivityStatusForm extends BaseForm {

    @Min(value=1,message="请选择正确的活动")
    private long id;
    private int  status;

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
