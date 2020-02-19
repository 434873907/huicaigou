package com.chengzi.apiunion.procurement.admin.web.controllers.pcconfig.tab;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.pcconfig.tab.pojo.Condition;
import com.chengzi.apiunion.pcconfig.tab.pojo.TabConfigDO;
import com.chengzi.apiunion.pcconfig.tab.service.TabConfigService;
import com.chengzi.apiunion.procurement.admin.web.formbean.pcconfig.tab.AddTabConfigForm;
import com.chengzi.common.data.validate.Result;

/**
 * @author 苏子
 * @date 2019年1月18日
 */
public class AddTabConfigController extends AbstractApiController<AddTabConfigForm>{

    @Override
    protected Result<String> doBiz(HttpServletRequest request, HttpServletResponse response, AddTabConfigForm command) throws Exception {
        long routeId = RequestContext.getRouteId();
        long userId = RequestContext.getUserId();
        
        TabConfigService tabConfigService = BeanFactory.getBean(TabConfigService.class);
        
        int order = tabConfigService.queryMaxOrder();
        
        TabConfigDO tabConfig = new TabConfigDO();
        tabConfig.setRouteId(routeId);
        tabConfig.setCreateUserId(userId);
        tabConfig.setTitle(command.getTitle());
        tabConfig.setCondition(Condition.toJsonArrayStr(command.getConditionList()));
        tabConfig.setStartTime(command.getStartTime());
        tabConfig.setEndTime(command.getEndTime());
        tabConfig.setStatus(command.getStatus());
        tabConfig.setOrder(++order);
        
        long id = tabConfigService.addTabConfig(tabConfig);
        if (id > 0) {
            return Result.buildSuccessMsg("新增成功");
        }
        return Result.buildOpFailedResult("新增失败");
    }

}
