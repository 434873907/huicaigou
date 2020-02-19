package com.chengzi.apiunion.procurement.admin.web.formbean.user;

import com.chengzi.common.web.formbean.BasePageForm;
import net.sf.oval.constraint.Min;

/**
 * <p>
 * </p>
 * User: 摩天
 * Date: 2019-03-12 16:53
 */
public class TopUpRecordForm extends BasePageForm {
    @Min(value = 1,message = "用户id不能为空")
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
