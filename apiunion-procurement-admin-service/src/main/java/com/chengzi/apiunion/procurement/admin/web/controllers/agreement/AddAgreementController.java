package com.chengzi.apiunion.procurement.admin.web.controllers.agreement;

import com.chengzi.apiunion.common.module.agreement.pojo.AgreementDO;
import com.chengzi.apiunion.common.module.agreement.service.AgreementService;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.agreement.AddAgreementForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 新增协议
 *
 * @author 月汐
 * @date 2019/1/14 9:56
 */
public class AddAgreementController extends AbstractApiController<AddAgreementForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, AddAgreementForm command) throws Exception {
        AgreementService agreementService = BeanFactory.getBean(AgreementService.class);
        AgreementDO agreementDO = new AgreementDO();
        agreementDO.setAgreementType(command.getType());
        agreementDO.setAgreementContent(command.getContent());
        return agreementService.add(agreementDO);
    }
}
