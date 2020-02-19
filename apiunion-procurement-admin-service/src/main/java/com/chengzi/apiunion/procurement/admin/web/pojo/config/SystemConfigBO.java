package com.chengzi.apiunion.procurement.admin.web.pojo.config;

import com.chengzi.common.data.beans.JsonPojo;

/**
 * @author 月汐
 * @date 2019/2/28 16:26
 */
public class SystemConfigBO extends JsonPojo {

    private boolean isSingleChannel;

    private boolean hasWeiNiPrice;

    private boolean hasItemGroup;
    /**
     * 路由KEY，用于标识平台
     */
    private String  key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isSingleChannel() {
        return isSingleChannel;
    }

    public void setSingleChannel(boolean singleChannel) {
        isSingleChannel = singleChannel;
    }

    public boolean isHasWeiNiPrice() {
        return hasWeiNiPrice;
    }

    public SystemConfigBO setHasWeiNiPrice(boolean hasWeiNiPrice) {
        this.hasWeiNiPrice = hasWeiNiPrice;
        return this;
    }

    public boolean isHasItemGroup() {
        return hasItemGroup;
    }

    public SystemConfigBO setHasItemGroup(boolean hasItemGroup) {
        this.hasItemGroup = hasItemGroup;
        return this;
    }
}
