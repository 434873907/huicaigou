package com.chengzi.apiunion.procurement.admin.web.controllers.partneruser;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.partneruser.service.PartnerUserService;
import com.chengzi.apiunion.procurement.admin.web.formbean.partneruser.UpdateInitPasswordForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 初始化密码
 *
 * @author 月汐
 * @date 2018/10/18 14:07
 */
public class UpdateInitPasswordController extends AbstractApiController<UpdateInitPasswordForm> {
    @Override
    protected Result<String> doBiz(HttpServletRequest request, HttpServletResponse response, UpdateInitPasswordForm command) throws Exception {
        PartnerUserService service = BeanFactory.getBean(PartnerUserService.class);
        return service.updateInitPassword(command.getId(), command.getPassword());
    }
}
