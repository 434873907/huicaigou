package com.chengzi.apiunion.procurement.admin.web.formbean.channel;

import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;
import com.chengzi.common.web.formbean.BaseForm;

/**
 * @author 月汐
 * @date 2019/1/7 14:54
 */
public class AddChannelForm extends BaseForm {

    @NotBlankAndNull(message = "渠道名不能为空")
    private String channelName;

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
}
