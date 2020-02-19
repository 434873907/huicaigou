package com.chengzi.apiunion.procurement.admin.web.controllers.expresscompany;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.expresscompany.pojo.ExpressCompanyDO;
import com.chengzi.apiunion.expresscompany.service.ExpressCompanyService;
import com.chengzi.apiunion.procurement.admin.web.formbean.expresscompany.UpdateExpressCompanyForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateExpressCompanyController extends AbstractApiController<UpdateExpressCompanyForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, UpdateExpressCompanyForm command) throws Exception {
        ExpressCompanyService expressCompanyService = BeanFactory.getBean(ExpressCompanyService.class);
        ExpressCompanyDO expressCompanyDO = expressCompanyService.getById(command.getId());
        if (expressCompanyDO == null) {
            return Result.buildOpFailedResult("该物流公司ID不存在");
        }

        expressCompanyDO.setCompanyName(command.getCompanyName());
        expressCompanyDO.setIcon(command.getIcon());
        expressCompanyDO.setOfficialUrl(command.getOfficialUrl());
        expressCompanyDO.setType(command.getType());

        int count = expressCompanyService.update(expressCompanyDO);
        if (count < 1) {
            return Result.buildOpFailedResult("修改失败");
        }
        return Result.buildSuccessMsg("操作成功");
    }
}
