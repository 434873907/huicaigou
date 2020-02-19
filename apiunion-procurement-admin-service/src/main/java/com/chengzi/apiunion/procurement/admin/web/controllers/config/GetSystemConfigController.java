package com.chengzi.apiunion.procurement.admin.web.controllers.config;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.module.config.enums.ConfigKeyEnums;
import com.chengzi.apiunion.common.module.config.pojo.SystemFunctionConfig;
import com.chengzi.apiunion.common.module.config.service.ConfigService;
import com.chengzi.apiunion.common.module.config.util.DataSourceRouteUtil;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.pojo.config.SystemConfigBO;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.EmptyForm;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 月汐
 * @date 2019/2/28 16:25
 */
public class GetSystemConfigController extends AbstractApiController<EmptyForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {
        ConfigService configService = BeanFactory.getBean(ConfigService.class);

        SystemFunctionConfig systemFunctionConfig = configService.getAndParseConfigValue(ConfigKeyEnums.SYSTEM_FUNCTION_CONFIG);

        SystemConfigBO bo = new SystemConfigBO();
        bo.setSingleChannel(Boolean.parseBoolean(configService.getConfigValue(ConfigKeyEnums.IS_SINGLE_CHANNEL)));
        bo.setHasItemGroup(systemFunctionConfig.isHasItemGroup());
        bo.setHasWeiNiPrice(systemFunctionConfig.isHasWeiNiPrice());

        bo.setKey(DataSourceRouteUtil.getKeyByRouteId(RequestContext.getRouteId()));
        
        return Result.buildSuccessResult(bo);
    }
}
