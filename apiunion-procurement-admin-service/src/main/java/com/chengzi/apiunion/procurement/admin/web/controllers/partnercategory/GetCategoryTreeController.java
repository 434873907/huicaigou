package com.chengzi.apiunion.procurement.admin.web.controllers.partnercategory;

import com.chengzi.apiunion.common.data.cache.LocalCache;
import com.chengzi.apiunion.common.data.search.elastic.pojo.SearchResult;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.pojo.search.AggBucket;
import com.chengzi.apiunion.item.pojo.search.FieldAggBuckets;
import com.chengzi.apiunion.item.pojo.search.ItemSearchBO;
import com.chengzi.apiunion.item.pojo.search.query.ItemSearchField;
import com.chengzi.apiunion.item.pojo.search.query.ShareAggregationQuery;
import com.chengzi.apiunion.item.search.ItemSearchService;
import com.chengzi.apiunion.partnercategory.constant.PartnerCategoryConstant;
import com.chengzi.apiunion.partnercategory.pojo.PartnerCategoryDO;
import com.chengzi.apiunion.partnercategory.service.PartnerCategoryService;
import com.chengzi.apiunion.procurement.admin.web.formbean.partnercategory.GetCategoryTreeForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.partnercategory.PartnerCategoryBO;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 获取类目树
 *
 * @author 月汐
 * @date 2018/11/6 10:21
 */
public class GetCategoryTreeController extends AbstractApiController<GetCategoryTreeForm> {

    private static final LocalCache<Map<Long, Long>> CATEGORY_ITEM_NUM_CACHE = new LocalCache<>(3600, (key) ->
            generateCategoryIdCountMap()
    );

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, GetCategoryTreeForm command) throws Exception {
        List<PartnerCategoryBO> resultList = getCategoryBOList();
        return Result.buildSuccessResult(resultList);
    }

    private static List<PartnerCategoryBO> getCategoryBOList() {
        PartnerCategoryService service = BeanFactory.getBean(PartnerCategoryService.class);
        List<PartnerCategoryDO> list = service.queryCategoryInCache();

        List<PartnerCategoryBO> resultList = new ArrayList<>();

        List<PartnerCategoryBO> firstCategory = new ArrayList<>();
        Map<Long, List<PartnerCategoryBO>> secondCategory = new LinkedHashMap<>();
        Map<Long, List<PartnerCategoryBO>> thirdCategory = new LinkedHashMap<>();

        Map<Long, Long> categoryIdCountMap  = CATEGORY_ITEM_NUM_CACHE.get();

        for (PartnerCategoryDO partnerCategoryDO : list) {
            if (PartnerCategoryConstant.FIRST_CLASS_CATEGORY == partnerCategoryDO.getDepth()) {
                firstCategory.add(PartnerCategoryBO.convert(partnerCategoryDO, null, true, categoryIdCountMap));
            } else if (PartnerCategoryConstant.SECOND_CLASS_CATEGORY == partnerCategoryDO.getDepth()) {
                List<PartnerCategoryBO> secondCategoryList = secondCategory.get(partnerCategoryDO.getParentId());
                if (secondCategoryList == null) {
                    secondCategoryList = new ArrayList<>();
                    secondCategory.put(partnerCategoryDO.getParentId(), secondCategoryList);
                }
                secondCategoryList.add(PartnerCategoryBO.convert(partnerCategoryDO, null, true, categoryIdCountMap));
            } else if (PartnerCategoryConstant.THIRD_CLASS_CATEGORY == partnerCategoryDO.getDepth()) {
                List<PartnerCategoryBO> thirdCategoryList = thirdCategory.get(partnerCategoryDO.getParentId());
                if (thirdCategoryList == null) {
                    thirdCategoryList = new ArrayList<>();
                    thirdCategory.put(partnerCategoryDO.getParentId(), thirdCategoryList);
                }
                thirdCategoryList.add(PartnerCategoryBO.convert(partnerCategoryDO, null, true, categoryIdCountMap));
            }
        }

        for (PartnerCategoryBO bo : firstCategory) {
            List<PartnerCategoryBO> secondCategoryList = secondCategory.get(bo.getId());
            if (secondCategoryList != null && secondCategoryList.size() > 0) {
                for (PartnerCategoryBO secondBo : secondCategoryList) {
                    List<PartnerCategoryBO> thirdCategoryList = thirdCategory.get(secondBo.getId());
                    secondBo.setChildren(thirdCategoryList);
                }
            }
            bo.setChildren(secondCategoryList);
            resultList.add(bo);
        }
        return resultList;
    }

    private static Map<Long, Long> generateCategoryIdCountMap() {
        Map<Long, Long> categoryIdCountMap = new HashMap<>();
        ItemSearchService itemSearchService = BeanFactory.getBean(ItemSearchService.class);
        ShareAggregationQuery query = new ShareAggregationQuery();
        query.setAggregationField(ItemSearchField.CATE_ID3);
        SearchResult<FieldAggBuckets<ItemSearchField, String, ItemSearchBO>> fieldAggBucketsSearchResult = itemSearchService.multiAggregation(CollectionUtil.asArrayList(query));
        List<AggBucket<String, ItemSearchBO>> buckets = fieldAggBucketsSearchResult.getData().get(0).getBuckets();
        for (AggBucket<String, ItemSearchBO> bucket : buckets) {
            categoryIdCountMap.put(Long.parseLong(bucket.getKey()), bucket.getDocCount());
            List<AggBucket<String, ItemSearchBO>> subBuckets = bucket.getSubBuckets();
            if (CollectionUtil.isNotEmpty(subBuckets)) {
                for (AggBucket<String, ItemSearchBO> subBucket : subBuckets) {
                    categoryIdCountMap.put(Long.parseLong(subBucket.getKey()), subBucket.getDocCount());
                    List<AggBucket<String, ItemSearchBO>> lastBuckets = subBucket.getSubBuckets();
                    if (CollectionUtil.isNotEmpty(lastBuckets)) {
                        for (AggBucket<String, ItemSearchBO> lastBucket : lastBuckets) {
                            categoryIdCountMap.put(Long.parseLong(lastBucket.getKey()), lastBucket.getDocCount());
                        }
                    }
                }
            }
        }
        return categoryIdCountMap;
    }

}
