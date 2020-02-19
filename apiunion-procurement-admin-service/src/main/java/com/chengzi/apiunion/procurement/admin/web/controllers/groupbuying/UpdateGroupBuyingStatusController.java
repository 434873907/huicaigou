package com.chengzi.apiunion.procurement.admin.web.controllers.groupbuying;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.groupbuying.UpdateGroupBuyingStatusForm;
import com.chengzi.apiunion.groupbuying.service.GroupBuyingService;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 月汐
 * @date 2019/2/18 19:28
 */
public class UpdateGroupBuyingStatusController extends AbstractApiController<UpdateGroupBuyingStatusForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, UpdateGroupBuyingStatusForm command) throws Exception {
        GroupBuyingService groupBuyingService = BeanFactory.getBean(GroupBuyingService.class);

        return groupBuyingService.updateStatus(command.getId(), command.getStatus());
    }
}
