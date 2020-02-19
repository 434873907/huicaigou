package com.chengzi.apiunion.procurement.admin.web.pojo.channel;

/**
 * @author 月汐
 * @date 2019/1/7 15:22
 */
public class ChannelBO {

    /**
     * 渠道ID
     */
    private long             channelId;

    /**
     * 渠道类型
     */
    private int              channelType;

    /**
     * 渠道名称
     */
    private String           channelTypeName;

    public long getChannelId() {
        return channelId;
    }

    public void setChannelId(long channelId) {
        this.channelId = channelId;
    }

    public int getChannelType() {
        return channelType;
    }

    public void setChannelType(int channelType) {
        this.channelType = channelType;
    }

    public String getChannelTypeName() {
        return channelTypeName;
    }

    public void setChannelTypeName(String channelTypeName) {
        this.channelTypeName = channelTypeName;
    }
}
