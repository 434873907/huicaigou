package com.chengzi.apiunion.procurement.admin.web.controllers.systeminfo;

import com.chengzi.apiunion.common.module.config.enums.ConfigKeyEnums;
import com.chengzi.apiunion.common.module.config.pojo.SystemInfo;
import com.chengzi.apiunion.common.module.config.service.ConfigService;
import com.chengzi.apiunion.common.module.image.enums.ImageBizType;
import com.chengzi.apiunion.common.module.image.util.ImageUtil;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.EmptyForm;
import org.apache.commons.lang3.StringUtils;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 查询系统信息
 */
public class GetSystemInfoController extends AbstractApiController<EmptyForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {
        ConfigService configService = BeanFactory.getBean(ConfigService.class);
        SystemInfo systemInfo = SystemInfo.parse(configService.getConfigValueFromDB(ConfigKeyEnums.SYSTEM_INFO));
        return Result.buildSuccessResult(systemInfo);
    }
}
