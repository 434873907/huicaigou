package com.chengzi.apiunion.procurement.admin.web.controllers.config;

import com.chengzi.apiunion.common.module.config.enums.ConfigKeyEnums;
import com.chengzi.apiunion.common.module.config.pojo.HelpConfig;
import com.chengzi.apiunion.common.module.config.service.ConfigService;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.pojo.config.HelpConfigBO;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.EmptyForm;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 月汐
 * @date 2019/05/14 10:56
 */
public class GetHelpConfigController extends AbstractApiController<EmptyForm> {
    @Override
    protected Result<HelpConfigBO> doBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {
        ConfigService configService = BeanFactory.getBean(ConfigService.class);

        HelpConfigBO bo = new HelpConfigBO();
        HelpConfig helpConfig = HelpConfig.parse(configService.getConfigValueFromDB(ConfigKeyEnums.HELP_CONFIG));
        bo.setHelpConfig(helpConfig);

        return Result.buildSuccessResult(bo);
    }
}
