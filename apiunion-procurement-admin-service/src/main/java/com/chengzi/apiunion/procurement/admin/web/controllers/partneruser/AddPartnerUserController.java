package com.chengzi.apiunion.procurement.admin.web.controllers.partneruser;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.partneruser.service.PartnerUserService;
import com.chengzi.apiunion.procurement.admin.web.formbean.partneruser.AddPartnerUserForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 新增管理用户
 *
 * @author 月汐
 * @date 2018/10/18 14:01
 */
public class AddPartnerUserController extends AbstractApiController<AddPartnerUserForm> {

    @Override
    protected Result<Long> doBiz(HttpServletRequest request, HttpServletResponse response, AddPartnerUserForm command) throws Exception {
        PartnerUserService service = BeanFactory.getBean(PartnerUserService.class);
        return service.add(AddPartnerUserForm.convert(command), command.getRoleIds());
    }

}
