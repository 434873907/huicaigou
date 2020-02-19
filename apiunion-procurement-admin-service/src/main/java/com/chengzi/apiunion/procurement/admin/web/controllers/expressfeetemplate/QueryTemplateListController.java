package com.chengzi.apiunion.procurement.admin.web.controllers.expressfeetemplate;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.expressfeetemplate.pojo.ExpressFeeTemplateDO;
import com.chengzi.apiunion.expressfeetemplate.service.ExpressFeeTemplateService;
import com.chengzi.apiunion.procurement.admin.web.pojo.expressfeetemplate.QueryTemplateListBySupplierBO;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.EmptyForm;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取运费模板下拉列表
 *
 * @author 月汐
 * @date 2018/10/19 16:52
 */
public class QueryTemplateListController extends AbstractApiController<EmptyForm> {
    @Override
    protected Result<List<QueryTemplateListBySupplierBO>> doBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {
        List<QueryTemplateListBySupplierBO> resultList = new ArrayList<>();
        ExpressFeeTemplateService service = BeanFactory.getBean(ExpressFeeTemplateService.class);
        List<ExpressFeeTemplateDO> list = service.queryTemplateList();
        for (ExpressFeeTemplateDO expressFeeTemplateDO : list) {
            resultList.add(QueryTemplateListBySupplierBO.convert(expressFeeTemplateDO));
        }
        return Result.buildSuccessResult(resultList);
    }
}
