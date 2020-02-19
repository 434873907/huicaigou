package com.chengzi.apiunion.procurement.admin.web.controllers.config;

import com.chengzi.apiunion.common.module.config.enums.ConfigKeyEnums;
import com.chengzi.apiunion.common.module.config.pojo.ConfigDO;
import com.chengzi.apiunion.common.module.config.pojo.HelpConfig;
import com.chengzi.apiunion.common.module.config.pojo.HelpInfo;
import com.chengzi.apiunion.common.module.config.service.ConfigService;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.config.EditConfigForm;
import com.chengzi.common.data.validate.Result;
import org.apache.commons.lang3.StringUtils;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 配置编辑
 *
 * @author 月汐
 * @date 2019/1/31 10:37
 */
public class EditConfigController extends AbstractApiController<EditConfigForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, EditConfigForm command) throws Exception {
        ConfigService configService = BeanFactory.getBean(ConfigService.class);

        if (ConfigKeyEnums.HELP_CONFIG.getKey().equals(command.getConfigType().getKey())) {
            HelpConfig helpConfig = HelpConfig.parse(command.getConfigContent());
            for (HelpInfo helpInfo : helpConfig) {
                if (StringUtils.isBlank(helpInfo.getTitle())) {
                    return Result.buildIllegalArgumentResult("请输入帮助标题");
                }
            }
        }

        ConfigDO configDO = new ConfigDO();
        configDO.setKey(command.getConfigType().getKey());
        configDO.setDesc(command.getConfigType().getDesc());
        configDO.setValue(command.getConfigContent());

        String config = configService.getConfigValueFromDB(command.getConfigType());
        if (config == null) {
            configService.insert(configDO);
        } else {
            configService.updateConfig(configDO);
        }

        return Result.buildSuccessResult("修改成功");
    }
}
