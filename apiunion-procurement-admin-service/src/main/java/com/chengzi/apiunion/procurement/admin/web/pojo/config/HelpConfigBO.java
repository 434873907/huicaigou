package com.chengzi.apiunion.procurement.admin.web.pojo.config;

import com.chengzi.apiunion.common.module.config.pojo.HelpConfig;
import com.chengzi.common.data.beans.JsonPojo;

/**
 * @author 月汐
 * @date 2019/05/14 10:57
 */
public class HelpConfigBO extends JsonPojo {

    private HelpConfig helpConfig;

    public HelpConfig getHelpConfig() {
        return helpConfig;
    }

    public void setHelpConfig(HelpConfig helpConfig) {
        this.helpConfig = helpConfig;
    }
}
