package com.chengzi.apiunion.procurement.admin.web.controllers.partnerrole;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.partnerrole.AddPartnerRoleForm;
import com.chengzi.apiunion.procurement.role.partnerrole.pojo.PartnerRoleDO;
import com.chengzi.apiunion.procurement.role.partnerrole.service.PartnerRoleService;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 新增角色
 *
 * @author 月汐
 * @date 2018/11/5 15:05
 */
public class AddPartnerRoleController extends AbstractApiController<AddPartnerRoleForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, AddPartnerRoleForm command) throws Exception {
        PartnerRoleDO partnerRoleDO = new PartnerRoleDO();
        partnerRoleDO.setRoleName(command.getRoleName());
        partnerRoleDO.setRouteId(RequestContext.getRouteId());
        partnerRoleDO.setFunctionPermissions(command.getFunctionPermissions());
        partnerRoleDO.setMenuPermissions(command.getMenuPermissions());

        PartnerRoleService service = BeanFactory.getBean(PartnerRoleService.class);

        return service.add(partnerRoleDO);
    }
}
