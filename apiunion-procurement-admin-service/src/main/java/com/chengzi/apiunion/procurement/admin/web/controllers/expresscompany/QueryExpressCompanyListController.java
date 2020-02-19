package com.chengzi.apiunion.procurement.admin.web.controllers.expresscompany;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.expresscompany.pojo.ExpressCompanyDO;
import com.chengzi.apiunion.expresscompany.service.ExpressCompanyService;
import com.chengzi.apiunion.procurement.admin.web.formbean.expresscompany.QueryExpressCompanyListForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.expresscompany.QueryExpressCompanyListBO;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 物流公司列表获取
 *
 * @author 月汐
 * @date 2018/12/10 10:10
 */
public class QueryExpressCompanyListController extends AbstractApiController<QueryExpressCompanyListForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, QueryExpressCompanyListForm command) throws Exception {
        ExpressCompanyService expressCompanyService = BeanFactory.getBean(ExpressCompanyService.class);
        List<ExpressCompanyDO> expressCompanyDOList = expressCompanyService.queryExpressCompanyList();
        List<QueryExpressCompanyListBO> boList = new ArrayList<>();
        for (ExpressCompanyDO expressCompanyDO : expressCompanyDOList) {
            if (command.getHideNonExpressCompany() == 1 && expressCompanyDO.getHide() == 1) {
                continue;
            }
            QueryExpressCompanyListBO bo = new QueryExpressCompanyListBO();
            bo.setId(expressCompanyDO.getId());
            bo.setName(expressCompanyDO.getCompanyName());
            boList.add(bo);
        }
        return Result.buildSuccessResult(boList);
    }
}
