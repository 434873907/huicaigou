package com.chengzi.apiunion.procurement.admin.web.controllers.expresscompany;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.expresscompany.pojo.ExpressCompanyDO;
import com.chengzi.apiunion.expresscompany.service.ExpressCompanyService;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.IdForm;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteExpressCompanyController extends AbstractApiController<IdForm> {


    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, IdForm command) throws Exception {
        ExpressCompanyService expressCompanyService = BeanFactory.getBean(ExpressCompanyService.class);
        ExpressCompanyDO expressCompanyDO = expressCompanyService.getById(command.getId());
        if (expressCompanyDO == null) {
            return Result.buildOpFailedResult("该物流公司ID不存在");
        }

        int count = expressCompanyService.delete(command.getId());
        if (count < 1) {
            return Result.buildOpFailedResult("修改失败");
        }
        return Result.buildSuccessMsg("操作成功");
    }
}
