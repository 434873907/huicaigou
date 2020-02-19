package com.chengzi.apiunion.procurement.admin.web.controllers.staticresources;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.staticresource.AddStaticResourcesConfigForm;
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
 * @date 2017/5/18
 */
public class AddStaticResourcesConfigController extends AbstractApiController<AddStaticResourcesConfigForm> {

    @Override
    protected Result doBiz(HttpServletRequest request, HttpServletResponse response, AddStaticResourcesConfigForm command)
            throws Exception {

        StaticResourcesConfigDO configDO = createConfigDO(command);
        StaticResourcesConfigService staticResourcesConfigService = BeanFactory.getBean(StaticResourcesConfigService.class);
        Result result = staticResourcesConfigService.checkAndSaveStaticResourcesConfig(configDO);
        return result;
    }

    private StaticResourcesConfigDO createConfigDO(AddStaticResourcesConfigForm command) {
        StaticResourcesConfigDO configDO = new StaticResourcesConfigDO();
        configDO.setKey(command.getKey());
        configDO.setDesc(command.getDesc());
        configDO.setValue(command.getValue());
        configDO.setPlatform(command.getPlatform());
        configDO.setMinAppV(command.getMinAppV());
        configDO.setMaxAppV(command.getMaxAppV());
        configDO.setMinApiV(command.getMinApiV());
        configDO.setMaxApiV(command.getMaxApiV());
        configDO.setStartTime(command.getStartTime());
        configDO.setEndTime(command.getEndTime());
        configDO.setAutoConfig(command.getAutoConfig());
        configDO.setValueType(command.getValueType());
        configDO.setTplId(command.getTplId());
        configDO.setShow(command.getShow());
        return configDO;
    }
}
