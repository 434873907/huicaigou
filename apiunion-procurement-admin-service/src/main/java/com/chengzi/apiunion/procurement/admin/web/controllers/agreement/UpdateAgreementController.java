package com.chengzi.apiunion.procurement.admin.web.controllers.agreement;

import com.chengzi.apiunion.common.module.agreement.pojo.AgreementDO;
import com.chengzi.apiunion.common.module.agreement.service.AgreementService;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.agreement.UpdateAgreementForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 更新协议
 *
 * @author 月汐
 * @date 2019/1/14 10:28
 */
public class UpdateAgreementController extends AbstractApiController<UpdateAgreementForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, UpdateAgreementForm command) throws Exception {
        AgreementService agreementService = BeanFactory.getBean(AgreementService.class);
        AgreementDO agreementDO = new AgreementDO();
        agreementDO.setId(command.getId());
        agreementDO.setAgreementType(command.getType());
        agreementDO.setAgreementContent(command.getContent());
        return agreementService.update(agreementDO);
    }
}
