package com.chengzi.apiunion.procurement.admin.web.pojo.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chengzi.apiunion.common.module.image.annotation.ImageDecorater;
import com.chengzi.apiunion.common.module.image.enums.ImageBizType;
import com.chengzi.common.data.beans.JsonPojo;

import java.util.List;

/**
 * @author 月汐
 * @date 2019/1/31 11:37
 */
public class ConfigBO extends JsonPojo {

    private int    channel;

    private String channelName;

    private String account;

    private String password;

    private String sign;

    @ImageDecorater(ImageBizType.ADMIN_QQ_QRCODE)
    private String qrCode;

    private String onlineTime;

    private int    type  = 0;
    private int    alive = 1;

    private String configValue;

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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(String onlineTime) {
        this.onlineTime = onlineTime;
    }

    public static ConfigBO parse(String json) {
        return JSONObject.parseObject(json, ConfigBO.class);
    }

    public static List<ConfigBO> parseArray(String json) {
        return JSONArray.parseArray(json, ConfigBO.class);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getAlive() {
        return alive;
    }

    public void setAlive(int alive) {
        this.alive = alive;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }
}
