package com.chengzi.apiunion.procurement.admin.web.controllers.shop;

import com.chengzi.apiunion.common.data.search.elastic.pojo.SearchResult;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.enums.ItemStatusEnum;
import com.chengzi.apiunion.item.pojo.search.AggBucket;
import com.chengzi.apiunion.item.pojo.search.FieldAggBuckets;
import com.chengzi.apiunion.item.pojo.search.ItemSearchBO;
import com.chengzi.apiunion.item.pojo.search.query.ItemSearchField;
import com.chengzi.apiunion.item.pojo.search.query.ShareAggregationQuery;
import com.chengzi.apiunion.item.search.ItemSearchService;
import com.chengzi.apiunion.procurement.admin.web.formbean.shop.QueryShopForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.shop.QueryShopBO;
import com.chengzi.apiunion.shop.pojo.ShopDO;
import com.chengzi.apiunion.shop.pojo.search.ShopSearchBO;
import com.chengzi.apiunion.shop.pojo.search.query.ShopSearchQuery;
import com.chengzi.apiunion.shop.search.ShopSearchService;
import com.chengzi.apiunion.shop.service.ShopService;
import com.chengzi.common.data.beans.PageDataList;
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
 * 获取店铺列表
 *
 * @author 月汐
 * @date 2018/11/15 20:13
 */
public class QueryShopController extends AbstractApiController<QueryShopForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, QueryShopForm command) throws Exception {
        ItemSearchService itemSearchService = BeanFactory.getBean(ItemSearchService.class);
        ShopService shopService = BeanFactory.getBean(ShopService.class);
        ShopSearchService shopSearchService = BeanFactory.getBean(ShopSearchService.class);

        ShopSearchQuery query = new ShopSearchQuery();
        query.setName(command.getName());
        query.setStatus(command.getStatus());
        query.setFrom(command.getOffset());
        query.setSize(command.getLimit());
        SearchResult<ShopSearchBO> searchResult = shopSearchService.query(query);
        List<ShopSearchBO> shopSearchBOList = searchResult.getData();

        ShareAggregationQuery shareAggregationQuery = new ShareAggregationQuery();
        shareAggregationQuery.setAggregationField(ItemSearchField.SHOP_ID);
        shareAggregationQuery.setStatus(ItemStatusEnum.ONLINE);
        SearchResult<FieldAggBuckets<ItemSearchField, String, ItemSearchBO>> onlineResult = itemSearchService.multiAggregation(CollectionUtil.asArrayList(shareAggregationQuery));
        List<AggBucket<String, ItemSearchBO>> buckets = onlineResult.getData().get(0).getBuckets();
        Map<Long, Integer> onlineShopItemNumMap = new HashMap<>();
        for (AggBucket<String, ItemSearchBO> bucket : buckets) {
            onlineShopItemNumMap.put(Long.parseLong(bucket.getKey()), (int) bucket.getDocCount());
        }

        shareAggregationQuery.setStatus(ItemStatusEnum.OFFLINE);
        SearchResult<FieldAggBuckets<ItemSearchField, String, ItemSearchBO>> offlineResult = itemSearchService.multiAggregation(CollectionUtil.asArrayList(shareAggregationQuery));
        Map<Long, Integer> offlineShopItemNumMap = new HashMap<>();
        for (AggBucket<String, ItemSearchBO> bucket : offlineResult.getData().get(0).getBuckets()) {
            offlineShopItemNumMap.put(Long.parseLong(bucket.getKey()), (int) bucket.getDocCount());
        }

        List<ShopDO> shopList = CollectionUtil.asArrayListFromIterable(shopService.getByIdsWithCache(CollectionUtil.getFieldValueList(shopSearchBOList, "id")));
        List<QueryShopBO> resultList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(shopList)) {
            for (ShopDO shopDO : shopList) {
                int onlineItemNum = onlineShopItemNumMap.getOrDefault(shopDO.getId(), 0);
                int offlineItemNum = offlineShopItemNumMap.getOrDefault(shopDO.getId(), 0);
                resultList.add(QueryShopBO.convert(shopDO, onlineItemNum + offlineItemNum, onlineItemNum, offlineItemNum));
            }
        }
        Long total = searchResult.getTotalHits();
        return Result.buildSuccessResult(new PageDataList<>(total, command.getPage(), command.getLimit(), resultList));
    }
}
