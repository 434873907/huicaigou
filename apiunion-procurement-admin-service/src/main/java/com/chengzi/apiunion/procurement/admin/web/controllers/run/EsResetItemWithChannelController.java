package com.chengzi.apiunion.procurement.admin.web.controllers.run;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.data.search.elastic.SearchScrollable;
import com.chengzi.apiunion.common.data.search.elastic.pojo.ScrollResult;
import com.chengzi.apiunion.common.data.search.elastic.scroll.ScrollDataProcessor;
import com.chengzi.apiunion.common.web.controllers.AbstractManageController;
import com.chengzi.apiunion.item.enums.ItemStatusEnum;
import com.chengzi.apiunion.item.pojo.ItemRichDO;
import com.chengzi.apiunion.item.pojo.ItemSkuInfo;
import com.chengzi.apiunion.item.pojo.search.ItemSearchBO;
import com.chengzi.apiunion.item.pojo.search.ItemWithChannelSearchBO;
import com.chengzi.apiunion.item.pojo.search.ItemWithChannelSearchBuilder;
import com.chengzi.apiunion.item.search.ItemSearchService;
import com.chengzi.apiunion.item.search.ItemWithChannelSearchService;
import com.chengzi.apiunion.item.service.ItemService;
import com.chengzi.apiunion.item.service.ItemSkuService;
import com.chengzi.client.util.CollectionUtil;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.EmptyForm;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 月汐
 * @date 2019/10/15 11:02
 */
public class EsResetItemWithChannelController extends AbstractManageController<EmptyForm> {
    @Override
    protected Result<?> doInnerBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {
        ItemService itemService = BeanFactory.getBean(ItemService.class);
        ItemSkuService itemSkuService = BeanFactory.getBean(ItemSkuService.class);
        SearchScrollable searchScrollable = (SearchScrollable) BeanFactory.getBean(ItemSearchService.class);
        ItemWithChannelSearchService itemWithChannelSearchService = BeanFactory.getBean(ItemWithChannelSearchService.class);

        List<Long> updateItemIds = new ArrayList<>();

        searchScrollable.scroll(600_000, 100, requestBuilder -> {
            BoolQueryBuilder postFilter = QueryBuilders.boolQuery();
            postFilter.must(QueryBuilders.termQuery("routeId", RequestContext.getRouteId()));
            postFilter.must(QueryBuilders.termQuery("joinType", "item"));
            postFilter.must(QueryBuilders.termQuery("status", ItemStatusEnum.ONLINE.getCode()));

            requestBuilder.setPostFilter(postFilter);
            requestBuilder.setFetchSource(new String[]{"id", "routeId"}, null);
        }, new ScrollDataProcessor<ItemSearchBO>() {
            @Override
            public void onScrolled(ScrollResult<ItemSearchBO> scrollResult) {
                List<Long> itemIds = CollectionUtil.getFieldValueList(scrollResult.getData(), "id");

                List<ItemRichDO> itemRichList = itemService.getItemRichByIds(itemIds);

                if (com.chengzi.common.util.CollectionUtil.isNotEmpty(itemRichList)) {
                    List<ItemSkuInfo> skuList = itemSkuService.getAllSkuInfosByItemId(itemIds);
                    Map<Long, List<ItemSkuInfo>> itemSkusMap = com.chengzi.common.util.CollectionUtil.toListMap(skuList, "itemId");

                    for (ItemRichDO itemRichDO : itemRichList) {
                        List<ItemSkuInfo> itemSkuInfoList = itemSkusMap.get(itemRichDO.getId());
                        List<ItemWithChannelSearchBO> itemWithChannelSearchBOList = ItemWithChannelSearchBuilder.build(itemRichDO, itemSkuInfoList);
                        for (ItemWithChannelSearchBO itemWithChannelSearchBO : itemWithChannelSearchBOList) {
                            itemWithChannelSearchService.indexItemWithChannel(itemWithChannelSearchBO);
                        }
                    }
                }
                updateItemIds.addAll(itemIds);
            }
        });
        return Result.buildSuccessResult("更新成功；本次共更新" + updateItemIds.size() + "条数据！");
    }
}
