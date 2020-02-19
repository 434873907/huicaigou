package com.chengzi.apiunion.procurement.admin.web.controllers.partneruser;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.partneruser.pojo.PartnerUserUpdateOperate;
import com.chengzi.apiunion.procurement.admin.partneruser.service.PartnerUserService;
import com.chengzi.apiunion.procurement.admin.web.formbean.partneruser.UpdatePartnerUserForm;
import com.chengzi.common.data.validate.Result;
import org.apache.commons.lang.StringUtils;
import org.summercool.util.MD5Util;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 更新用户信息
 *
 * @author 月汐
 * @date 2018/10/18 14:08
 */
public class UpdatePartnerUserController extends AbstractApiController<UpdatePartnerUserForm> {

    @Override
    protected Result<String> doBiz(HttpServletRequest request, HttpServletResponse response, UpdatePartnerUserForm command) throws Exception {
        PartnerUserService service = BeanFactory.getBean(PartnerUserService.class);
        PartnerUserUpdateOperate operate = new PartnerUserUpdateOperate();
        operate.setId(command.getId());
        operate.setNickName(command.getNickName());
        operate.setLogoUrl(command.getLogoUrl());
        operate.setEmail(command.getEmail());
        operate.setPhone(command.getPhone());
        operate.setContacts(command.getContacts());
        return service.update(operate, command.getRoleIds());
    }

}
