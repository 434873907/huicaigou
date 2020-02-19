package com.chengzi.apiunion.procurement.admin.web.formbean.agreement;

import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;
import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;

/**
 * @author 月汐
 * @date 2019/1/14 9:57
 */
public class AddAgreementForm extends BaseForm {

    /**
     * 协议类型
     * {@link com.chengzi.apiunion.common.module.agreement.enums.AgreementTypeEnum}
     */
    @Min(1)
    private int type;

    @NotBlankAndNull
    private String content;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
