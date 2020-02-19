package com.chengzi.apiunion.procurement.admin.web.controllers.groupbuying;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.groupbuying.service.GroupBuyingService;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.IdForm;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 删除团购
 *
 * @author 月汐
 * @date 2019/2/18 19:15
 */
public class DeleteGroupBuyingController extends AbstractApiController<IdForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, IdForm command) throws Exception {
        GroupBuyingService groupBuyingService = BeanFactory.getBean(GroupBuyingService.class);
        return groupBuyingService.delete(command.getId());
    }
}
