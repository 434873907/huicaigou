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
 * 模板类目List查询
 */
public class QueryTemplateCategoryListController extends AbstractApiController<QueryTemplateCategoryListForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, QueryTemplateCategoryListForm command) throws Exception {

        SupplierTemplateCategoryService templateCategoryService = BeanFactory.getBean(SupplierTemplateCategoryService.class);

        TemplateCategoryQuery query = buildQuery(command);
        List<SupplierTemplateCategoryDO> categoryDOS = templateCategoryService.queryTemplateCategoryList(query);
        if(CollectionUtil.isNotEmpty(categoryDOS)) {
            long total = templateCategoryService.queryTemplateCategoryCount(query);

            List<TemplateCategoryBO> templateCategoryBOS = new ArrayList<>();
            for (SupplierTemplateCategoryDO categoryDO : categoryDOS) {

                TemplateCategoryBO categoryBO = convert(categoryDO);
                templateCategoryBOS.add(categoryBO);
            }
            return Result.buildSuccessResult(new PageDataList(total, command.getPage(), command.getLimit(), templateCategoryBOS));
        } else {
            return Result.buildSuccessResult(new PageDataList<>(0, command.getPage(), command.getLimit(), new ArrayList<>()));
        }
    }

    private TemplateCategoryQuery buildQuery(QueryTemplateCategoryListForm command) {

        TemplateCategoryQuery query = new TemplateCategoryQuery();
        query.setCateName(command.getCateName());
        query.setType(command.getType());
        query.setLimit(command.getLimit());
        query.setOffset(command.getOffset());

        return query;
    }

    private TemplateCategoryBO convert(SupplierTemplateCategoryDO categoryDO) {
        TemplateCategoryBO bo = new TemplateCategoryBO();

        bo.setId(categoryDO.getId());
        bo.setCateId(categoryDO.getCateId());
        bo.setCateName(categoryDO.getCateName());
        bo.setCateDesc(categoryDO.getCateDesc());
        if(StringUtils.isNotBlank(categoryDO.getCateSkuNames())) {
            List<String> skuNames = JSON.parseArray(categoryDO.getCateSkuNames(), String.class);
            bo.setCateSkuNames(skuNames);
        }
        bo.setType(categoryDO.getType());
        return bo;
    }


}
