package com.chengzi.apiunion.procurement.admin.web.controllers.template;

import com.alibaba.fastjson.JSON;
import com.chengzi.apiunion.common.data.cache.KVLocalCache;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.partnercategory.pojo.CategoryNode;
import com.chengzi.apiunion.procurement.admin.web.formbean.template.QueryTemplateCategoryCountForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.template.TemplateCategoryByParentBO;
import com.chengzi.apiunion.supplier.common.item.pojo.query.SupplierTemplateItemSearchQuery;
import com.chengzi.apiunion.supplier.common.item.service.SupplierTemplateItemSearchService;
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

/**
 * 查询类目商品数量
 *
 * @author 行者
 */
public class QueryTemplateCategoryCountController extends AbstractApiController<QueryTemplateCategoryCountForm> {

    private static final KVLocalCache<String, Map<Long, Long>> CATE_KV_CACHE = new KVLocalCache<>(1000, 600, QueryTemplateCategoryCountController::getCategoryByParentBOList, null);

    @Override
    protected Result<List<TemplateCategoryByParentBO>> doBiz(HttpServletRequest request, HttpServletResponse response, QueryTemplateCategoryCountForm command) throws Exception {

        Map<Long, Long> resultMap = CATE_KV_CACHE.get(buildKey(command));

        List<TemplateCategoryByParentBO> resultList = new ArrayList<>();

        TemplateCategoryQuery categoryQuery = new TemplateCategoryQuery();
        categoryQuery.setParentId(command.getParentId());

        SupplierTemplateCategoryService templateCategoryService = BeanFactory.getBean(SupplierTemplateCategoryService.class);
        List<SupplierTemplateCategoryDO> cateList = templateCategoryService.queryTemplateCategoryList(categoryQuery);

        for (SupplierTemplateCategoryDO category : cateList) {
            resultList.add(TemplateCategoryByParentBO.convert(category, resultMap.getOrDefault(category.getId(), 0L)));
        }

        return Result.buildSuccessResult(resultList);
    }

    private static Map<Long, Long> getCategoryByParentBOList(String key) {
        SupplierTemplateCategoryService service = BeanFactory.getBean(SupplierTemplateCategoryService.class);
        SupplierTemplateItemSearchService templateItemSearchService = BeanFactory.getBean(SupplierTemplateItemSearchService.class);

        SupplierTemplateItemSearchQuery query = JSON.parseObject(key, SupplierTemplateItemSearchQuery.class);
        TemplateCategoryQuery categoryQuery = new TemplateCategoryQuery();

        List<SupplierTemplateCategoryDO> categoryDOList = service.queryTemplateCategoryList(categoryQuery);

        Map<Long, Long> resultMap = new HashMap<>();
        List<CategoryNode> categoryNodes;
        categoryNodes = templateItemSearchService.cateAggregation(query).getCategorys();
        if (CollectionUtil.isEmpty(categoryNodes)) {
            return new HashMap<>();
        }
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

        for (SupplierTemplateCategoryDO templateCategoryDO : categoryDOList) {
            long count = categoryNodeMap.getOrDefault(templateCategoryDO.getId(), new CategoryNode()).getCount();
            resultMap.put(templateCategoryDO.getId(), count);
        }
        return resultMap;
    }

    private String buildKey(QueryTemplateCategoryCountForm command) {
        return command.buildQuery().toJsonString();
    }


}
