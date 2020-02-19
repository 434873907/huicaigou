package com.chengzi.apiunion.procurement.admin.web.controllers.systeminfo;

import com.chengzi.apiunion.common.module.config.enums.ConfigKeyEnums;
import com.chengzi.apiunion.common.module.config.pojo.ConfigDO;
import com.chengzi.apiunion.common.module.config.pojo.SystemInfo;
import com.chengzi.apiunion.common.module.config.service.ConfigService;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 更新系统信息：描述 关键词 备案信息
 */
public class UpdateSystemInfoController extends AbstractApiController<SystemInfo> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, SystemInfo command) throws Exception {
        ConfigService configService = BeanFactory.getBean(ConfigService.class);
        String valueFromDB = configService.getConfigValueFromDB(ConfigKeyEnums.SYSTEM_INFO);
        String necessaryValue = command.setNecessaryValue(valueFromDB, command);
        ConfigDO configDO = new ConfigDO();
        configDO.setKey(ConfigKeyEnums.SYSTEM_INFO.getKey());
        configDO.setValue(necessaryValue);
        configDO.setDesc(ConfigKeyEnums.SYSTEM_INFO.getDesc());
        boolean b = configService.updateConfig(configDO);
        if (b) {
            return Result.buildSuccessResult("修改成功");
        }
        return Result.buildOpFailedResult("修改失败");
    }
}
