package com.chengzi.apiunion.procurement.admin.web.controllers.expressfeetemplate;

import com.chengzi.apiunion.common.data.beans.RouteQuery;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.expressfeetemplate.pojo.ExpressFeeTemplateDO;
import com.chengzi.apiunion.expressfeetemplate.service.ExpressFeeTemplateService;
import com.chengzi.apiunion.procurement.admin.web.pojo.expressfeetemplate.QueryExpressFeeTemplateBO;
import com.chengzi.common.data.beans.PageDataList;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.BasePageForm;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取运费模板列表
 *
 * @author 月汐
 * @date 2018/10/19 15:01
 */
public class QueryExpressFeeTemplateController extends AbstractApiController<BasePageForm> {

    @Override
    protected Result<PageDataList<QueryExpressFeeTemplateBO>> doBiz(HttpServletRequest request, HttpServletResponse response, BasePageForm command) throws Exception {
        ExpressFeeTemplateService service = BeanFactory.getBean(ExpressFeeTemplateService.class);

        RouteQuery query = new RouteQuery();
        query.setOffset(command.getOffset());
        query.setLimit(command.getLimit());

        List<ExpressFeeTemplateDO> list = service.queryTemplates(query);
        List<QueryExpressFeeTemplateBO> resultList = new ArrayList<>();
        for (ExpressFeeTemplateDO expressFeeTemplateDO : list) {
            resultList.add(QueryExpressFeeTemplateBO.convert(expressFeeTemplateDO));
        }
        long total = service.countTemplates();
        return Result.buildSuccessResult(new PageDataList<>(total, command.getPage(), command.getLimit(), resultList));
    }

}
