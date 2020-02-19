package com.chengzi.apiunion.procurement.admin.web.controllers.pcconfig.tab;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.pcconfig.tab.service.TabConfigService;
import com.chengzi.apiunion.procurement.admin.web.formbean.pcconfig.tab.UpdateTabConfigOrderForm;
import com.chengzi.common.data.validate.Result;

/**
 * @author 苏子
 * @date 2019年1月18日
 */
public class UpdateTabConfigOrderController extends AbstractApiController<UpdateTabConfigOrderForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, UpdateTabConfigOrderForm command) throws Exception {
        long userId = RequestContext.getUserId();
        TabConfigService tabConfigService = BeanFactory.getBean(TabConfigService.class);
        tabConfigService.updateOrder(command.getIds(), userId);
        return Result.buildSuccessMsg("修改成功");
    }

}
