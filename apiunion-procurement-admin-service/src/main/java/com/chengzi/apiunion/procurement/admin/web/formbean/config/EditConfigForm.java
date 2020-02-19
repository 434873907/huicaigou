package com.chengzi.apiunion.procurement.admin.web.formbean.config;

import com.chengzi.apiunion.common.module.config.enums.ConfigKeyEnums;
import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;
import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.NotNull;

/**
 * @author 月汐
 * @date 2019/1/31 10:40
 */
public class EditConfigForm extends BaseForm {

    @NotNull(message = "请选择需要修改的配置类型")
    private ConfigKeyEnums configType;

    @NotBlankAndNull(message = "请输入配置")
    private String configContent;

    public ConfigKeyEnums getConfigType() {
        return configType;
    }

    public void setConfigType(ConfigKeyEnums configType) {
        this.configType = configType;
    }

    public String getConfigContent() {
        return configContent;
    }

    public void setConfigContent(String configContent) {
        this.configContent = configContent;
    }
}
