package com.chengzi.apiunion.procurement.admin.web.controllers.template;

import com.alibaba.fastjson.JSON;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.template.QueryTemplateCategoryListForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.template.TemplateCategoryBO;
import com.chengzi.apiunion.supplier.common.template.pojo.SupplierTemplateCategoryDO;
import com.chengzi.apiunion.supplier.common.template.pojo.TemplateCategoryQuery;
import com.chengzi.apiunion.supplier.common.template.service.SupplierTemplateCategoryService;
import com.chengzi.common.data.beans.PageDataList;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import org.apache.commons.lang3.StringUtils;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 模板类目全量数据查询
 */
public class QueryTemplateCategoryController extends AbstractApiController<QueryTemplateCategoryListForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, QueryTemplateCategoryListForm command) throws Exception {

        SupplierTemplateCategoryService templateCategoryService = BeanFactory.getBean(SupplierTemplateCategoryService.class);

        TemplateCategoryQuery query = buildQuery(command);
        List<SupplierTemplateCategoryDO> categoryDOS = templateCategoryService.queryTemplateCategoryAll(query);
        if(CollectionUtil.isNotEmpty(categoryDOS)) {
//            long total = templateCategoryService.queryTemplateCategoryCount(query);

            List<TemplateCategoryBO> templateCategoryBOS = new ArrayList<>();
            for (SupplierTemplateCategoryDO categoryDO : categoryDOS) {

                TemplateCategoryBO categoryBO = convert(categoryDO);
                templateCategoryBOS.add(categoryBO);
            }
            return Result.buildSuccessResult(templateCategoryBOS);
        } else {
            return Result.buildSuccessResult( new ArrayList<>());
        }
    }

    private TemplateCategoryQuery buildQuery(QueryTemplateCategoryListForm command) {

        TemplateCategoryQuery query = new TemplateCategoryQuery();
        query.setCateName(command.getCateName());
        query.setLimit(command.getLimit());
        query.setOffset(command.getOffset());

        return query;
    }

    private TemplateCategoryBO convert(SupplierTemplateCategoryDO categoryBO) {
        TemplateCategoryBO bo = new TemplateCategoryBO();

        bo.setId(categoryBO.getId());
        bo.setCateId(categoryBO.getCateId());
        bo.setCateName(categoryBO.getCateName());
        bo.setCateDesc(categoryBO.getCateDesc());
        if(StringUtils.isNotBlank(categoryBO.getCateSkuNames())) {
            List<String> skuNames = JSON.parseArray(categoryBO.getCateSkuNames(), String.class);
            bo.setCateSkuNames(skuNames);
        }
        return bo;
    }


}
