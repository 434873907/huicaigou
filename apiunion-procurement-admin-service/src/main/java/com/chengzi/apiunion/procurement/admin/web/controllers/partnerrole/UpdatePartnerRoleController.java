package com.chengzi.apiunion.procurement.admin.web.controllers.partnerrole;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.partnerrole.UpdatePartnerRoleForm;
import com.chengzi.apiunion.procurement.role.partnerrole.pojo.PartnerRoleDO;
import com.chengzi.apiunion.procurement.role.partnerrole.service.PartnerRoleService;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 更新角色信息
 *
 * @author 月汐
 * @date 2018/11/5 17:32
 */
public class UpdatePartnerRoleController extends AbstractApiController<UpdatePartnerRoleForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, UpdatePartnerRoleForm command) throws Exception {
        PartnerRoleService service = BeanFactory.getBean(PartnerRoleService.class);

        PartnerRoleDO partnerRoleDO = new PartnerRoleDO();
        partnerRoleDO.setId(command.getId());
        partnerRoleDO.setRoleName(command.getRoleName());
        partnerRoleDO.setMenuPermissions(command.getMenuPermissions());
        partnerRoleDO.setFunctionPermissions(command.getFunctionPermissions());

        return service.update(partnerRoleDO);
    }

}
