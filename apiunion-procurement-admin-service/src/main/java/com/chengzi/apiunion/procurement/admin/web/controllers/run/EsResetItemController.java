package com.chengzi.apiunion.procurement.admin.web.controllers.run;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.data.search.elastic.SearchScrollable;
import com.chengzi.apiunion.common.data.search.elastic.pojo.BaseSearchBO;
import com.chengzi.apiunion.common.data.search.elastic.pojo.ScrollResult;
import com.chengzi.apiunion.common.data.search.elastic.scroll.ScrollDataProcessor;
import com.chengzi.apiunion.common.event.EventBusFactory;
import com.chengzi.apiunion.common.module.shard.enums.TableShardStrategyDefinitionEnum;
import com.chengzi.apiunion.common.module.shard.strategy.DivisorTableShardStrategy;
import com.chengzi.apiunion.common.mybatis.shard.ShardUtil;
import com.chengzi.apiunion.common.web.controllers.AbstractManageController;
import com.chengzi.apiunion.item.dao.ItemMapper;
import com.chengzi.apiunion.item.event.pojo.ItemsDeletedEvent;
import com.chengzi.apiunion.item.event.pojo.ItemsUpdatedEvent;
import com.chengzi.apiunion.item.pojo.ItemDO;
import com.chengzi.apiunion.item.pojo.ItemQuery;
import com.chengzi.apiunion.item.pojo.ItemRichDO;
import com.chengzi.apiunion.item.pojo.search.ItemSearchBO;
import com.chengzi.apiunion.item.search.ItemSearchService;
import com.chengzi.apiunion.item.service.ItemService;
import com.chengzi.client.util.CollectionUtil;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.EmptyForm;

/**
 * 重新初始化商品搜索
 * 
 * @author Kolor
 */
public class EsResetItemController extends AbstractManageController<EmptyForm> {
    @Override
    protected Result<?> doInnerBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {
        ItemMapper itemMapper = BeanFactory.getBean(ItemMapper.class);
        ItemService itemService = BeanFactory.getBean(ItemService.class);
        Set<String> tableNames = ShardUtil.loadTableSchema(TableShardStrategyDefinitionEnum.ITEM);
        DivisorTableShardStrategy shardStrategy = (DivisorTableShardStrategy) TableShardStrategyDefinitionEnum.ITEM.getShardStrategy();

        ItemQuery query = new ItemQuery();
        query.setIsDeleted(null);
        query.setLimit(1000);
        for (String tableName : tableNames) {
            int idx = StringUtils.lastIndexOf(tableName, "_");
            int suffixNum = Integer.parseInt(StringUtils.substring(tableName, idx + 1));

            query.setShardValue(suffixNum * shardStrategy.getDivisor() + 1);
            List<ItemDO> list = itemMapper.selectByQuery(query);
            while (!list.isEmpty()) {
                List<Long> updateItemIds = new ArrayList<>();
                for (ItemDO itemDO : list) {
                    if (itemDO.isDeleted()) {
                        EventBusFactory.getSyncEventBus().post(new ItemsDeletedEvent(CollectionUtil.asArrayList(itemDO.getId())));
                    } else {
                        updateItemIds.add(itemDO.getId());
                    }
                }
                EventBusFactory.getSyncEventBus().post(new ItemsUpdatedEvent(updateItemIds));
                
                query.setOffset(query.getOffset() + 1000);
                list = itemMapper.selectByQuery(query);
            }
        }

        SearchScrollable searchScrollable = (SearchScrollable) BeanFactory.getBean(ItemSearchService.class);

        searchScrollable.scroll(600_000, 100, requestBuilder -> {
            BoolQueryBuilder postFilter = QueryBuilders.boolQuery();
            postFilter.must(QueryBuilders.termQuery("routeId", RequestContext.getRouteId()));
            postFilter.must(QueryBuilders.termQuery("joinType", "item"));

            requestBuilder.setPostFilter(postFilter);
            requestBuilder.setFetchSource(new String[] { "id", "routeId" }, null);
        }, new ScrollDataProcessor<ItemSearchBO>() {
            @Override
            public void onScrolled(ScrollResult<ItemSearchBO> scrollResult) {
                List<Long> itemIds = CollectionUtil.getFieldValueList(scrollResult.getData(), "id");
                if (CollectionUtil.isEmpty(itemIds)) {
                    return;
                }
                List<ItemRichDO> itemList = itemService.getItemRichByIds(itemIds);
                for (ItemRichDO itemRich : itemList) {
                    itemIds.remove(itemRich.getId());
                }
                if (CollectionUtil.isNotEmpty(itemIds)) {
                    for (Long itemId : itemIds) {
                        searchScrollable.deleteById(BaseSearchBO.buildSearchId(RequestContext.getRouteId(), String.valueOf(itemId)));
                    }
                }
            }
        });
        return Result.buildSuccessResult("更新成功");
    }
}
