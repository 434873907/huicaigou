package com.chengzi.apiunion.procurement.admin.web.controllers.partneruser;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.partneruser.service.PartnerUserService;
import com.chengzi.apiunion.procurement.admin.web.formbean.manageuser.DeleteManageUserForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 删除用户
 *
 * @author 月汐
 * @date 2018/10/18 14:01
 */
public class DeletePartnerUserController extends AbstractApiController<DeleteManageUserForm> {

    @Override
    protected Result<String> doBiz(HttpServletRequest request, HttpServletResponse response, DeleteManageUserForm command) throws Exception {
        if (command.getId() == RequestContext.getUserId()) {
            return Result.buildOpFailedResult("无效操作");
        }

        PartnerUserService service = BeanFactory.getBean(PartnerUserService.class);
        service.delete(command.getId());

        return Result.buildSuccessResult("删除成功");
    }

}
