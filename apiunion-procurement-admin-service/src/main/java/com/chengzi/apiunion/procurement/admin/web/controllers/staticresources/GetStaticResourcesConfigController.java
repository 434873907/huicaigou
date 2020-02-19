package com.chengzi.apiunion.procurement.admin.web.controllers.staticresources;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.staticresource.GetStaticResourcesConfigForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.staticrresources.StaticResourcesConfigListBO;
import com.chengzi.apiunion.staticresource.pojo.StaticResourcesConfigDO;
import com.chengzi.apiunion.staticresource.service.StaticResourcesConfigService;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * </p>
 *
 * @author 爱夏
 * @date: 17/4/18
 */
public class GetStaticResourcesConfigController extends AbstractApiController<GetStaticResourcesConfigForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, GetStaticResourcesConfigForm command) throws Exception {
        StaticResourcesConfigService staticResourcesConfigService = BeanFactory.getBean(StaticResourcesConfigService.class);
        StaticResourcesConfigDO configDO = staticResourcesConfigService.getResourceConfigById(command.getConfigId());
        StaticResourcesConfigListBO configListBO = StaticResourcesConfigListBO.convert(configDO);
        return Result.buildSuccessResult(configListBO);
    }
}
