package com.chengzi.apiunion.procurement.admin.web.controllers.partnerrole;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.partnerrole.GetPartnerRoleByIdForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.partnerrole.GetPartnerRoleByIdBO;
import com.chengzi.apiunion.procurement.role.partnerrole.pojo.PartnerRoleDO;
import com.chengzi.apiunion.procurement.role.partnerrole.service.PartnerRoleService;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 获取角色详情
 *
 * @author 月汐
 * @date 2018/11/5 15:45
 */
public class GetPartnerRoleByIdController extends AbstractApiController<GetPartnerRoleByIdForm> {

    @Override
    protected Result<GetPartnerRoleByIdBO> doBiz(HttpServletRequest request, HttpServletResponse response, GetPartnerRoleByIdForm command) throws Exception {
        PartnerRoleService service = BeanFactory.getBean(PartnerRoleService.class);
        PartnerRoleDO roleDO = service.getById(command.getId());
        return Result.buildSuccessResult(GetPartnerRoleByIdBO.convert(roleDO));
    }

}
