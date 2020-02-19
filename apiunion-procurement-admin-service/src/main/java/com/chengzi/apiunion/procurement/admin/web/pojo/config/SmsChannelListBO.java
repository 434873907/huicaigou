package com.chengzi.apiunion.procurement.admin.web.pojo.config;

import com.chengzi.common.data.beans.JsonPojo;

/**
 * @author 月汐
 * @date 2019/3/12 15:56
 */
public class SmsChannelListBO extends JsonPojo {

    private int channel;

    private String channelName;

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
}
