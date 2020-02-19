package com.chengzi.apiunion.procurement.admin.web.formbean.config;

import com.chengzi.apiunion.common.module.config.enums.ConfigKeyEnums;
import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.NotNull;

/**
 * @author 月汐
 * @date 2019/1/31 11:33
 */
public class GetConfigByTypeForm extends BaseForm {

    @NotNull(message = "请选择需要查询的配置类型")
    private ConfigKeyEnums configType;

    public ConfigKeyEnums getConfigType() {
        return configType;
    }

    public void setConfigType(ConfigKeyEnums configType) {
        this.configType = configType;
    }
}
