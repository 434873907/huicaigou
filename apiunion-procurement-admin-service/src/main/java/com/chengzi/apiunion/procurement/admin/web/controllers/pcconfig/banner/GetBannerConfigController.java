package com.chengzi.apiunion.procurement.admin.web.controllers.pcconfig.banner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.pcconfig.banner.pojo.BannerConfigDO;
import com.chengzi.apiunion.pcconfig.banner.service.BannerConfigService;
import com.chengzi.apiunion.procurement.admin.web.pojo.pcconfig.banner.BannerConfigBO;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.IdForm;

/**
 * @author 苏子
 * @date 2019年1月17日
 */
public class GetBannerConfigController extends AbstractApiController<IdForm>{

    @Override
    protected Result<BannerConfigBO> doBiz(HttpServletRequest request, HttpServletResponse response, IdForm command) throws Exception {
        BannerConfigService bannerConfigService = BeanFactory.getBean(BannerConfigService.class);
        BannerConfigDO bannerConfig = bannerConfigService.getBannerConfig(command.getId());
        BannerConfigBO bo = BannerConfigBO.convert(bannerConfig);
        return Result.buildSuccessResult(bo);
    }

}
