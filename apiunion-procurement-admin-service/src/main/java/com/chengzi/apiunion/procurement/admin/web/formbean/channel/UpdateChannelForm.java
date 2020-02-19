package com.chengzi.apiunion.procurement.admin.web.formbean.channel;

import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;
import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;

/**
 * @author 月汐
 * @date 2019/1/7 15:10
 */
public class UpdateChannelForm extends BaseForm {

    @Min(value = 1, message = "请选择正确的渠道")
    private long id;

    @NotBlankAndNull(message = "请输入渠道名")
    private String channelName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
}
