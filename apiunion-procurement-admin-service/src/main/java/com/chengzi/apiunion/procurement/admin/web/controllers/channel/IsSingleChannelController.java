package com.chengzi.apiunion.procurement.admin.web.controllers.channel;

import com.chengzi.apiunion.common.module.config.enums.ConfigKeyEnums;
import com.chengzi.apiunion.common.module.config.service.ConfigService;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.EmptyForm;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 月汐
 * @date 2019/2/21 15:41
 */
public class IsSingleChannelController extends AbstractApiController<EmptyForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {
        ConfigService configService = BeanFactory.getBean(ConfigService.class);
        return Result.buildSuccessResult(configService.getConfigValue(ConfigKeyEnums.IS_SINGLE_CHANNEL));
    }
}
