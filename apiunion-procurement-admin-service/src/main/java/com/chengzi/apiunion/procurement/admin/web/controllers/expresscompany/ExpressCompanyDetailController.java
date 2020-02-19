package com.chengzi.apiunion.procurement.admin.web.controllers.expresscompany;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.expresscompany.pojo.ExpressCompanyDO;
import com.chengzi.apiunion.expresscompany.service.ExpressCompanyService;
import com.chengzi.apiunion.procurement.admin.web.formbean.expresscompany.QueryExpressCompanyListForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.expresscompany.ExpressCompanyBO;
import com.chengzi.apiunion.procurement.admin.web.pojo.expresscompany.QueryExpressCompanyListBO;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.IdForm;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 物流公司详情
 * @author 行者
 */
public class ExpressCompanyDetailController extends AbstractApiController<IdForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, IdForm command) throws Exception {
        ExpressCompanyService expressCompanyService = BeanFactory.getBean(ExpressCompanyService.class);
        ExpressCompanyDO expressCompanyDO = expressCompanyService.getById(command.getId());
        if (expressCompanyDO == null) {
            return Result.buildOpFailedResult("该物流公司ID不存在");
        }

        ExpressCompanyBO bo = new ExpressCompanyBO();
        bo.setId(expressCompanyDO.getId());
        bo.setCompanyName(expressCompanyDO.getCompanyName());
        bo.setIcon(expressCompanyDO.getIcon());
        bo.setOfficialUrl(expressCompanyDO.getOfficialUrl());
        bo.setType(expressCompanyDO.getType());

        return Result.buildSuccessResult(bo);
    }
}
