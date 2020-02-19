package com.chengzi.apiunion.procurement.admin.web.controllers.expresscompany;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.expresscompany.pojo.ExpressCompanyDO;
import com.chengzi.apiunion.expresscompany.pojo.ExpressCompanyQuery;
import com.chengzi.apiunion.expresscompany.service.ExpressCompanyService;
import com.chengzi.apiunion.procurement.admin.web.formbean.expresscompany.QueryExpressCompanyListForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.expresscompany.ExpressCompanyBO;
import com.chengzi.apiunion.procurement.admin.web.pojo.expresscompany.QueryExpressCompanyListBO;
import com.chengzi.common.data.beans.PageDataList;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 物流公司列表获取
 * @author 行者
 */
public class ExpressCompanyListController extends AbstractApiController<QueryExpressCompanyListForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, QueryExpressCompanyListForm command) throws Exception {
        ExpressCompanyService expressCompanyService = BeanFactory.getBean(ExpressCompanyService.class);
        ExpressCompanyQuery query = new ExpressCompanyQuery();
        query.setName(command.getName());
        query.setHide(0);
        query.setLimit(command.getLimit());
        query.setOffset(command.getOffset());
        List<ExpressCompanyDO> expressCompanyDOList = expressCompanyService.queryExpressCompany(query);
        List<ExpressCompanyBO> boList = new ArrayList<>();
        for (ExpressCompanyDO expressCompanyDO : expressCompanyDOList) {
            ExpressCompanyBO bo = new ExpressCompanyBO();
            bo.setId(expressCompanyDO.getId());
            bo.setCompanyName(expressCompanyDO.getCompanyName());
            bo.setIcon(expressCompanyDO.getIcon());
            bo.setOfficialUrl(expressCompanyDO.getOfficialUrl());
            bo.setType(expressCompanyDO.getType());
            boList.add(bo);
        }
        long count = expressCompanyService.countByQuery(query);
        return Result.buildSuccessResult(new PageDataList<>(count, command.getPage(), command.getLimit(), boList));
    }
}
