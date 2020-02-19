package com.chengzi.apiunion.procurement.admin.web.controllers.pcconfig.banner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.pcconfig.banner.service.BannerConfigService;
import com.chengzi.apiunion.procurement.admin.web.formbean.pcconfig.banner.UpdateBannerConfigOrderForm;
import com.chengzi.common.data.validate.Result;

/**
 * @author 苏子
 * @date 2019年1月18日
 */
public class UpdateBannerConfigOrderController extends AbstractApiController<UpdateBannerConfigOrderForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, UpdateBannerConfigOrderForm command) throws Exception {
        long userId = RequestContext.getUserId();
        BannerConfigService bannerConfigService = BeanFactory.getBean(BannerConfigService.class);
        bannerConfigService.updateOrder(command.getIds(), userId);
        return Result.buildSuccessMsg("修改成功");
    }

}
