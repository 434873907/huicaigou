package com.chengzi.apiunion.procurement.admin.web.formbean.channel;

import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;

/**
 * @author 月汐
 * @date 2019/1/7 15:07
 */
public class DeleteChannelForm extends BaseForm {

    @Min(value = 1, message = "请选择正确的渠道")
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
