package com.chengzi.apiunion.procurement.admin.web.formbean.manageuser;

import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;

/**
 * @author 月汐
 * @date 2018/10/17 17:41
 */
public class UpdateInitPasswordForm extends BaseForm {

    @Min(1)
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
