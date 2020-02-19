package com.chengzi.apiunion.procurement.admin.web.controllers.pcconfig.tab;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.pcconfig.tab.pojo.TabConfigDO;
import com.chengzi.apiunion.pcconfig.tab.service.TabConfigService;
import com.chengzi.apiunion.procurement.admin.web.formbean.pcconfig.tab.UpdateTabConfigStatusForm;
import com.chengzi.common.data.validate.Result;

/**
 * @author 苏子
 * @date 2019年1月18日
 */
public class UpdateTabConfigStatusController extends AbstractApiController<UpdateTabConfigStatusForm>{

    @Override
    protected Result<String> doBiz(HttpServletRequest request, HttpServletResponse response, UpdateTabConfigStatusForm command) throws Exception {
        long userId = RequestContext.getUserId();
        long id = command.getId();
        int status = command.getStatus();
        
        TabConfigService tabConfigService = BeanFactory.getBean(TabConfigService.class);
        TabConfigDO tabConfig = tabConfigService.getTabConfig(command.getId());
        if (tabConfig == null) {
            return Result.buildOpFailedResult("数据不存在");
        }
        
        int row = tabConfigService.updateTabConfigStatus(id, status, userId);
        if (row > 0) {
            return Result.buildSuccessMsg("修改成功");
        }
        return Result.buildOpFailedResult("修改失败");
    }

}
