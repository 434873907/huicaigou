package com.chengzi.apiunion.procurement.admin.web.controllers.pcconfig.banner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.pcconfig.banner.service.BannerConfigService;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.IdForm;

/**
 * @author 苏子
 * @date 2019年1月17日
 */
public class DeleteBannerConfigController extends AbstractApiController<IdForm>{

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, IdForm command) throws Exception {
        long userId = RequestContext.getUserId();
        
        BannerConfigService bannerConfigService = BeanFactory.getBean(BannerConfigService.class);
        int row = bannerConfigService.deleteBannerConfig(command.getId(), userId);
        
        if (row > 0) {
            return Result.buildSuccessMsg("删除成功");
        }
        return Result.buildOpFailedResult("修改失败");
    }

}
