package com.chengzi.apiunion.procurement.admin.web.formbean.user;

import net.sf.oval.constraint.Min;

/**
 * @author 月汐
 * @date 2019/1/30 11:36
 */
public class UpdateUserLevelRuleForm extends AddUserLevelRuleForm {

    @Min(value = 1, message = "请选择需要修改的规则")
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
