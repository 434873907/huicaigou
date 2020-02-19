package com.chengzi.apiunion.procurement.admin.web.controllers.partnercategory;

import com.alibaba.fastjson.JSON;
import com.chengzi.apiunion.common.data.cache.KVLocalCache;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.enums.ItemStatusEnum;
import com.chengzi.apiunion.item.pojo.search.query.ItemApproveSearchQuery;
import com.chengzi.apiunion.item.search.ItemApproveSearchService;
import com.chengzi.apiunion.item.search.ItemSearchService;
import com.chengzi.apiunion.partnercategory.pojo.CategoryByParentIdAndStatusQuery;
import com.chengzi.apiunion.partnercategory.pojo.CategoryNode;
import com.chengzi.apiunion.partnercategory.pojo.PartnerCategoryDO;
import com.chengzi.apiunion.partnercategory.service.PartnerCategoryService;
import com.chengzi.apiunion.procurement.admin.web.formbean.partnercategory.QueryCategoryByParentForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.partnercategory.QueryCategoryByParentBO;
import com.chengzi.common.data.enums.BooleanStatusEnum;
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
 * 查询子类目
 *
 * @author 月汐
 * @date 2018/10/12 19:11
 */
public class QueryCategoryByParentController extends AbstractApiController<QueryCategoryByParentForm> {

    private static final KVLocalCache<String, Map<Long, Long>> CATE_KV_CACHE = new KVLocalCache<>(1000, 600, QueryCategoryByParentController::getCategoryByParentBOList, null);

    @Override
    protected Result<List<QueryCategoryByParentBO>> doBiz(HttpServletRequest request, HttpServletResponse response, QueryCategoryByParentForm command) throws Exception {
        if (command.getParentId() == null) {
            return Result.buildSuccessResult(new ArrayList<>());
        }

        Map<Long, Long> resultMap = CATE_KV_CACHE.get(buildKey(command));

        List<QueryCategoryByParentBO> resultList = new ArrayList<>();

        CategoryByParentIdAndStatusQuery categoryQuery = new CategoryByParentIdAndStatusQuery();
        categoryQuery.setParentId(command.getParentId());
        if (command.getStatus() != null && command.getStatus() != -1) {
            categoryQuery.setStatus(command.getStatus());
        }

        PartnerCategoryService service = BeanFactory.getBean(PartnerCategoryService.class);
        List<PartnerCategoryDO> list = service.queryByParentIdAndStatus(categoryQuery);

        for (PartnerCategoryDO category : list) {
            resultList.add(QueryCategoryByParentBO.convert(category, resultMap.getOrDefault(category.getId(), 0L)));
        }

        return Result.buildSuccessResult(resultList);
    }

    private static Map<Long, Long> getCategoryByParentBOList(String key) {
        PartnerCategoryService service = BeanFactory.getBean(PartnerCategoryService.class);
        ItemSearchService itemSearchService = BeanFactory.getBean(ItemSearchService.class);
        ItemApproveSearchService itemApproveSearchService = BeanFactory.getBean(ItemApproveSearchService.class);

        ItemApproveSearchQuery query = JSON.parseObject(key, ItemApproveSearchQuery.class);
        CategoryByParentIdAndStatusQuery categoryQuery = new CategoryByParentIdAndStatusQuery();
        categoryQuery.setStatus(BooleanStatusEnum.YES.getCode());

        List<PartnerCategoryDO> list = service.queryByParentIdAndStatus(categoryQuery);

        Map<Long, Long> resultMap = new HashMap<>();
        List<CategoryNode> categoryNodes;
        if (query.getStatus() != ItemStatusEnum.INIT) {
            categoryNodes = itemSearchService.cateAggregation(query).getCategorys();
        } else {
            categoryNodes = itemApproveSearchService.cateAggregation(query).getCategorys();
        }
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

        for (PartnerCategoryDO partnerCategoryDO : list) {
            long count = categoryNodeMap.getOrDefault(partnerCategoryDO.getId(), new CategoryNode()).getCount();
            resultMap.put(partnerCategoryDO.getId(), count);
        }
        return resultMap;
    }

    private String buildKey(QueryCategoryByParentForm command) {
        return command.buildQuery().toJsonString();
    }


}
