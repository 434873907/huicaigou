package com.chengzi.apiunion.procurement.admin.web.controllers.partneruser;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.partneruser.pojo.PartnerUserDO;
import com.chengzi.apiunion.procurement.admin.partneruser.service.PartnerUserService;
import com.chengzi.apiunion.procurement.admin.web.formbean.partneruser.GetPartnerUserDetailForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.partneruser.GetPartnerUserDetailBO;
import com.chengzi.apiunion.procurement.role.partnerrole.service.PartnerRoleService;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 获取用户详情
 *
 * @author 月汐
 * @date 2018/11/9 11:32
 */
public class GetPartnerUserDetailController extends AbstractApiController<GetPartnerUserDetailForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, GetPartnerUserDetailForm command) throws Exception {
        PartnerUserService userService = BeanFactory.getBean(PartnerUserService.class);
        PartnerRoleService roleService = BeanFactory.getBean(PartnerRoleService.class);
        PartnerUserDO user = userService.getUserById(command.getId());
        String roleIds = roleService.getRoleNamesByUserId(command.getId());
        return Result.buildSuccessResult(GetPartnerUserDetailBO.convert(user, roleIds));
    }

}
