package com.chengzi.apiunion.procurement.admin.web.controllers.expresscompany;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.expresscompany.pojo.ExpressCompanyDO;
import com.chengzi.apiunion.expresscompany.service.ExpressCompanyService;
import com.chengzi.apiunion.procurement.admin.web.formbean.expresscompany.AddExpressCompanyForm;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.BaseForm;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddExpressCompanyController extends AbstractApiController<AddExpressCompanyForm> {

    private static final int TRACK_STRATEGY_CODE = 1;

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, AddExpressCompanyForm command) throws Exception {
        ExpressCompanyService expressCompanyService = BeanFactory.getBean(ExpressCompanyService.class);
        Long companyId = expressCompanyService.getCompanyIdByName(command.getCompanyName());
        if (companyId != null) {
            return Result.buildOpFailedResult("该物流公司已存在");
        }

        ExpressCompanyDO expressCompanyDO = new ExpressCompanyDO();
        expressCompanyDO.setCompanyName(command.getCompanyName());
        expressCompanyDO.setIcon(command.getIcon());
        expressCompanyDO.setOfficialUrl(command.getOfficialUrl());
        expressCompanyDO.setType(command.getType());
        expressCompanyDO.setSpiderStrategy(TRACK_STRATEGY_CODE);

        return expressCompanyService.addExpressCompany(expressCompanyDO);
    }
}
