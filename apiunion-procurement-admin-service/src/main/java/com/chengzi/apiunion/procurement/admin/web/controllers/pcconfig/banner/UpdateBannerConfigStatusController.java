package com.chengzi.apiunion.procurement.admin.web.controllers.pcconfig.banner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.pcconfig.banner.pojo.BannerConfigDO;
import com.chengzi.apiunion.pcconfig.banner.service.BannerConfigService;
import com.chengzi.apiunion.procurement.admin.web.formbean.pcconfig.banner.UpdateBannerConfigStatusForm;
import com.chengzi.common.data.validate.Result;

/**
 * @author 苏子
 * @date 2019年1月17日
 */
public class UpdateBannerConfigStatusController extends AbstractApiController<UpdateBannerConfigStatusForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, UpdateBannerConfigStatusForm command) throws Exception {
        long userId = RequestContext.getUserId();
        long id = command.getId();
        int status = command.getStatus();
        
        BannerConfigService bannerConfigService = BeanFactory.getBean(BannerConfigService.class);
        BannerConfigDO bannerConfig = bannerConfigService.getBannerConfig(command.getId());
        if (bannerConfig == null) {
            return Result.buildOpFailedResult("数据不存在");
        }
        int row = bannerConfigService.updateBannerConfigStatus(id, status,userId);
        if (row > 0) {
            return Result.buildSuccessMsg("修改成功");
        }
        return Result.buildOpFailedResult("修改失败");
    }

}
