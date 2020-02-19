package com.chengzi.apiunion.procurement.admin.web.formbean.user;

import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.Range;

/**
 * <p>
 * </p>
 * User: 摩天
 * Date: 2019-03-12 11:27
 */
public class DisableUserForm extends BaseForm {
    @Min(value = 1,message = "用户id不能为空")
    private long id;

    @Range(min = 0,max = 1,message = "disable类型不正确")
    private int  disable;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDisable() {
        return disable;
    }

    public void setDisable(int disable) {
        this.disable = disable;
    }
}
