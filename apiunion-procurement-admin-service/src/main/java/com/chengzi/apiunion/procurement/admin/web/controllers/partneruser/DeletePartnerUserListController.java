package com.chengzi.apiunion.procurement.admin.web.controllers.partneruser;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.partneruser.service.PartnerUserService;
import com.chengzi.apiunion.procurement.admin.web.formbean.partneruser.DeletePartnerUserListForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 批量删除用户
 *
 * @author 月汐
 * @date 2018/10/18 14:03
 */
public class DeletePartnerUserListController extends AbstractApiController<DeletePartnerUserListForm> {

    @Override
    protected Result<String> doBiz(HttpServletRequest request, HttpServletResponse response, DeletePartnerUserListForm command) throws Exception {
        if (command.getIds().contains(RequestContext.getUserId())) {
            return Result.buildOpFailedResult("操作无效");
        }

        PartnerUserService service = BeanFactory.getBean(PartnerUserService.class);
        service.deletePartnerUserList(command.getIds());

        return Result.buildSuccessResult("删除成功");
    }

}
