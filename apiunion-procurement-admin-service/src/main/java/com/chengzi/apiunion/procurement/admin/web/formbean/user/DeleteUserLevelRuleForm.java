package com.chengzi.apiunion.procurement.admin.web.formbean.user;

import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;

/**
 * @author 月汐
 * @date 2019/1/30 11:23
 */
public class DeleteUserLevelRuleForm extends BaseForm {

    @Min(value = 1, message = "请选择需要删除的规则")
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
