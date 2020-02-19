package com.chengzi.apiunion.procurement.admin.web.controllers.agreement;

import com.chengzi.apiunion.common.module.agreement.pojo.AgreementDO;
import com.chengzi.apiunion.common.module.agreement.service.AgreementService;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.agreement.GetAgreementForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.agreement.GetAgreementBO;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 获取协议
 *
 * @author 月汐
 * @date 2019/1/14 11:26
 */
public class GetAgreementController extends AbstractApiController<GetAgreementForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, GetAgreementForm command) throws Exception {
        AgreementService agreementService = BeanFactory.getBean(AgreementService.class);
        AgreementDO agreementDO = agreementService.get(command.getType());
        if (agreementDO == null) {
            return Result.buildEmptySuccess();
        } else {
            GetAgreementBO bo = new GetAgreementBO();
            bo.setId(agreementDO.getId());
            bo.setType(agreementDO.getAgreementType());
            bo.setContent(agreementDO.getAgreementContent());
            return Result.buildSuccessResult(bo);
        }
    }
}
