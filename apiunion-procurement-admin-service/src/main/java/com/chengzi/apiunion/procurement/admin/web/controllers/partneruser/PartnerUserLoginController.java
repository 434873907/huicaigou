package com.chengzi.apiunion.procurement.admin.web.controllers.partneruser;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.common.web.cookie.CookieUtils;
import com.chengzi.apiunion.procurement.admin.partneruser.pojo.PartnerUserDO;
import com.chengzi.apiunion.procurement.admin.partneruser.service.PartnerUserService;
import com.chengzi.apiunion.procurement.admin.web.formbean.partneruser.PartnerUserLoginForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.partneruser.PartnerUserLoginBO;
import com.chengzi.apiunion.procurement.role.partnerrole.pojo.PartnerRoleDO;
import com.chengzi.apiunion.procurement.role.partnerrole.service.PartnerRoleService;
import com.chengzi.common.data.enums.PlatformEnum;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;

/**
 * 用户登录
 *
 * @author 月汐
 * @date 2018/10/18 14:04
 */
public class PartnerUserLoginController extends AbstractApiController<PartnerUserLoginForm> {

    @Override
    protected Result<PartnerUserLoginBO> doBiz(HttpServletRequest request, HttpServletResponse response, PartnerUserLoginForm command) throws Exception {
        if (StringUtils.isBlank(command.getAccount())) {

        }

        PartnerUserService service = BeanFactory.getBean(PartnerUserService.class);
        PartnerUserDO partnerUserDO = service.getUserByAccount(command.getAccount(), command.getPassword());
        if (partnerUserDO == null) {
            return Result.buildIllegalArgumentResult("用户名或密码错误");
        }
        PartnerRoleService roleService = BeanFactory.getBean(PartnerRoleService.class);
        List<PartnerRoleDO> roleDOS = roleService.getRoleByUserId(partnerUserDO.getId());

        if (CollectionUtil.isEmpty(roleDOS)) {
            return Result.buildOpFailedResult("当前账号无权限");
        }

        PartnerUserLoginBO bo = PartnerUserLoginBO.convert(partnerUserDO, roleDOS);
        CookieUtils.setToken(request, bo.getId(), PlatformEnum.PROCUREMENT_ADMIN, (byte)0, bo.getNickName());
        return Result.buildSuccessResult(bo);
    }

}
