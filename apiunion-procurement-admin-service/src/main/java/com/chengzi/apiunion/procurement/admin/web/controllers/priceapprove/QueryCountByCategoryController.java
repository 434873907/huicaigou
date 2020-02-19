package com.chengzi.apiunion.procurement.admin.web.controllers.priceapprove;

import com.chengzi.apiunion.common.data.search.elastic.pojo.SearchResult;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.partnercategory.pojo.CategoryNode;
import com.chengzi.apiunion.procurement.admin.web.formbean.priceapprove.QueryPriceApproveListForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.priceapprove.CategoryApproveCountBO;
import com.chengzi.apiunion.supplier.common.item.pojo.query.SupplierItemSearchQuery;
import com.chengzi.apiunion.supplier.common.item.service.SupplierItemSearchService;
import com.chengzi.apiunion.supplier.common.priceapprove.pojo.search.SkuPriceApproveSearchBO;
import com.chengzi.apiunion.supplier.common.priceapprove.query.SkuPriceApproveSearchQuery;
import com.chengzi.apiunion.supplier.common.priceapprove.service.SkuPriceApproveSearchService;
import com.chengzi.apiunion.supplier.common.template.pojo.SupplierTemplateCategoryDO;
import com.chengzi.apiunion.supplier.common.template.pojo.TemplateCategoryQuery;
import com.chengzi.apiunion.supplier.common.template.service.SupplierTemplateCategoryService;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryCountByCategoryController extends AbstractApiController<QueryPriceApproveListForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, QueryPriceApproveListForm command) throws Exception {
        SkuPriceApproveSearchService priceApproveSearchService = BeanFactory.getBean(SkuPriceApproveSearchService.class);
        SupplierItemSearchService supplierItemSearchService = BeanFactory.getBean(SupplierItemSearchService.class);
        SupplierTemplateCategoryService categoryService = BeanFactory.getBean(SupplierTemplateCategoryService.class);

        TemplateCategoryQuery categoryQuery = new TemplateCategoryQuery();
        List<SupplierTemplateCategoryDO> categoryDOList = categoryService.queryTemplateCategoryList(categoryQuery);

        SkuPriceApproveSearchQuery approveSearchQuery = new SkuPriceApproveSearchQuery();
        approveSearchQuery.setStatus(command.getStatus());
        SearchResult<SkuPriceApproveSearchBO> searchResult = priceApproveSearchService.query(approveSearchQuery);
        List<Long> supplierItemIds = CollectionUtil.getFieldValueList(searchResult.getData(), "supplierItemId");

        SupplierItemSearchQuery itemSearchQuery = new SupplierItemSearchQuery();
        itemSearchQuery.setItemIds(supplierItemIds);
        List<CategoryNode> categoryNodes = supplierItemSearchService.cateAggregation(itemSearchQuery).getCategorys();
        Map<Long, CategoryNode> categoryNodeMap = new HashMap<>(CollectionUtil.toMap(categoryNodes, "cateId"));
        for (CategoryNode categoryNode : categoryNodes) {
            if (CollectionUtil.isNotEmpty(categoryNode.getNodes())) {
                categoryNodeMap.putAll(CollectionUtil.toMap(categoryNode.getNodes(), "cateId"));
                categoryNode.getNodes().forEach(node -> {
                    if (CollectionUtil.isNotEmpty(node.getNodes())) {
                        categoryNodeMap.putAll(CollectionUtil.toMap(node.getNodes(), "cateId"));
                    }
                });
            }
        }

        ArrayList<CategoryApproveCountBO> boList = new ArrayList<>();
        categoryDOList.forEach(categoryDO -> {
            CategoryApproveCountBO bo = new CategoryApproveCountBO();
            bo.setCateId(categoryDO.getId());
            bo.setCateName(categoryDO.getCateName());
            bo.setCount(categoryNodeMap.getOrDefault(categoryDO.getId(), new CategoryNode()).getCount());

            boList.add(bo);
        });

        return Result.buildSuccessResult(boList);
    }
}
