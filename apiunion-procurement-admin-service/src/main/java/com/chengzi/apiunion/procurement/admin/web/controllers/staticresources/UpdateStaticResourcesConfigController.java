package com.chengzi.apiunion.procurement.admin.web.controllers.staticresources;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.staticresource.UpdateStaticResourcesConfigForm;
import com.chengzi.apiunion.staticresource.pojo.StaticResourcesConfigDO;
import com.chengzi.apiunion.staticresource.service.StaticResourcesConfigService;
import com.chengzi.common.data.validate.ErrorConstants;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * </p>
 *
 * @author 爱夏
 * @date 2017/5/18
 */
public class UpdateStaticResourcesConfigController extends AbstractApiController<UpdateStaticResourcesConfigForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, UpdateStaticResourcesConfigForm command) throws Exception {
        long id = command.getId();

        StaticResourcesConfigService staticResourcesConfigService = BeanFactory.getBean(StaticResourcesConfigService.class);
        StaticResourcesConfigDO config = staticResourcesConfigService.getResourceConfigById(id);

        if (config == null) {
            return Result.buildFailResult(ErrorConstants.ERR_NOT_EXISTED, "配置不存在");
        }
        StaticResourcesConfigDO staticResourcesConfigDO = convert(config,command);

        // 校验并更新
        Result result = staticResourcesConfigService.checkAndUpdateStaticResourcesConfig(staticResourcesConfigDO);
        return result;
    }

    private StaticResourcesConfigDO convert(StaticResourcesConfigDO configDO, UpdateStaticResourcesConfigForm command) {
        configDO.setKey(command.getKey());
        configDO.setDesc(command.getDesc());
        configDO.setAutoConfig(command.getAutoConfig());
        configDO.setStartTime(command.getStartTime());
        configDO.setEndTime(command.getEndTime());
        configDO.setValueType(command.getValueType());
        configDO.setValue(command.getValue());
        return configDO;
    }
}
