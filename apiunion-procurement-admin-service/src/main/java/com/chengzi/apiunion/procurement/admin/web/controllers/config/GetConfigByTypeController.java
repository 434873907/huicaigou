package com.chengzi.apiunion.procurement.admin.web.controllers.config;

import com.chengzi.apiunion.common.module.config.enums.ConfigKeyEnums;
import com.chengzi.apiunion.common.module.config.enums.SmsChannelEnum;
import com.chengzi.apiunion.common.module.config.service.ConfigService;
import com.chengzi.apiunion.common.module.image.enums.ImageBizType;
import com.chengzi.apiunion.common.module.image.util.ImageUtil;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.config.GetConfigByTypeForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.config.ConfigBO;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.EnumUtil;
import org.apache.commons.lang3.StringUtils;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * 获取配置
 *
 * @author 月汐
 * @date 2019/1/31 11:32
 */
public class GetConfigByTypeController extends AbstractApiController<GetConfigByTypeForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, GetConfigByTypeForm command) throws Exception {
        ConfigService configService = BeanFactory.getBean(ConfigService.class);

        String configValue = configService.getConfigValueFromDB(command.getConfigType());
        ConfigBO bo = null;
        if (command.getConfigType() == ConfigKeyEnums.SERVICE_QQ_ACCOUNT) {//在线客服配置新的需求，做特殊处理
            //得到所有配置
            try {
                List<ConfigBO> configBOS = ConfigBO.parseArray(configValue);
                return Result.buildSuccessResult(configBOS);
            } catch (Exception e) {
                logger.error("Json Array parse error");
                bo = ConfigBO.parse(configValue);
                List<ConfigBO> configBOS = Arrays.asList(bo);
                return Result.buildSuccessResult(configBOS);
            }
        } else {
            bo = ConfigBO.parse(configValue);
        }
        if (StringUtils.isNotBlank(configValue)
                && command.getConfigType() == ConfigKeyEnums.SMS_ACCOUNT
                && bo.getChannel() > 0) {
            bo.setChannelName(EnumUtil.parse(SmsChannelEnum.class, bo.getChannel()).name());
        }

        if (StringUtils.isNotBlank(configValue) && command.getConfigType() == ConfigKeyEnums.SES_ACCOUNT) {

        }

        if (bo != null && StringUtils.isNotBlank(bo.getQrCode())) {
            bo.setQrCode(bo.getQrCode());
        }

        bo.setConfigValue(configValue);

        return Result.buildSuccessResult(bo);
    }
}
