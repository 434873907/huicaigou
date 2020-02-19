package com.chengzi.apiunion.procurement.admin.web.controllers.staticresources;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.staticresource.UpdateConfigStatusForm;
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
 * @date 2018/1/16
 */
public class UpdateConfigStatusController extends AbstractApiController<UpdateConfigStatusForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, UpdateConfigStatusForm command) throws Exception {
        StaticResourcesConfigService staticResourcesConfigService = BeanFactory.getBean(StaticResourcesConfigService.class);
        int row = staticResourcesConfigService.updateConfigStatus(command.getConfigId(), command.getConfigStatus());
        if (row > 0) {
            return Result.buildSuccessMsg("执行成功");
        } else {
            return Result.buildSystemErrResult("执行失败");
        }
    }
}
