package com.chengzi.apiunion.procurement.admin.web.formbean.agreement;

import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Max;
import net.sf.oval.constraint.Min;

/**
 * @author 月汐
 * @date 2019/1/14 11:26
 */
public class GetAgreementForm extends BaseForm {

    @Min(value = 1, message = "请选择正确的协议类型")
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
