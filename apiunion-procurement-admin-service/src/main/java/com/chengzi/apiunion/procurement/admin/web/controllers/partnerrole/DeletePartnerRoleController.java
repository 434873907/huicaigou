package com.chengzi.apiunion.procurement.admin.web.controllers.partnerrole;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.partnerrole.DeletePartnerRoleForm;
import com.chengzi.apiunion.procurement.role.partnerrole.service.PartnerRoleService;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 删除角色
 *
 * @author 月汐
 * @date 2018/11/5 15:40
 */
public class DeletePartnerRoleController extends AbstractApiController<DeletePartnerRoleForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, DeletePartnerRoleForm command) throws Exception {
        PartnerRoleService service = BeanFactory.getBean(PartnerRoleService.class);
        return service.delete(command.getId());
    }

}
