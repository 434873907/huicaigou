package com.chengzi.apiunion.procurement.admin.web.controllers.activity;

import com.alibaba.fastjson.JSON;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.pojo.ItemSkuInfo;
import com.chengzi.apiunion.item.pojo.cache.ItemCO;
import com.chengzi.apiunion.item.service.ItemService;
import com.chengzi.apiunion.item.service.ItemSkuService;
import com.chengzi.apiunion.procurement.admin.web.formbean.activity.FlashSaleActivityListForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.activity.FlashSaleActivityBO;
import com.chengzi.apiunion.sales.pojo.FlashSaleActivityDO;
import com.chengzi.apiunion.sales.pojo.ParticipateItem;
import com.chengzi.apiunion.sales.pojo.strategy.FlashSaleActivityListQuery;
import com.chengzi.apiunion.sales.service.ActivityService;
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
 * <p>
 * </p>
 * User: 摩天
 * Date: 2019-02-20 10:51
 */
public class FlashSaleActivityListController extends AbstractApiController<FlashSaleActivityListForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, FlashSaleActivityListForm command) throws Exception {

        ActivityService activityService = BeanFactory.getBean(ActivityService.class);
        FlashSaleActivityListQuery query = new FlashSaleActivityListQuery();
        query.setStartTime(command.getStartTime());
        query.setEndTime(command.getEndTime());
        int total = activityService.countFlashSaleActivitys(query);
        List<FlashSaleActivityBO> flashSaleActivityBOS = new ArrayList<>();
        if (total > 0) {
            List<FlashSaleActivityDO> flashSaleActivityDOS = activityService.queryFlashSaleActivitys(query);
            List<Long> itemIds = new ArrayList<>();
            Map<Long, List<ParticipateItem>> participateItemMap = new HashMap<>();
            flashSaleActivityDOS.forEach(x -> {
                List<ParticipateItem> participateItems = JSON.parseArray(x.getParticipateItemNum(), ParticipateItem.class);
                participateItems.forEach(y -> itemIds.add(y.getItemId()));
                participateItemMap.put(x.getId(), participateItems);
            });
            ItemService itemService = BeanFactory.getBean(ItemService.class);
            ItemSkuService itemSkuService = BeanFactory.getBean(ItemSkuService.class);
            // 规格
            Map<Long, ItemSkuInfo> skuInfosMap = CollectionUtil.toMap(itemSkuService.getSkuInfosByItemIds(itemIds), "id");
            Map<Long, ItemCO> itemCOMap = CollectionUtil.toMap(itemService.getItemWithCacheByIds(itemIds), "id");

            flashSaleActivityDOS.forEach(x -> flashSaleActivityBOS.add(FlashSaleActivityBO.convert(x, itemCOMap, skuInfosMap)));
        }

        return Result.buildSuccessResult(new PageDataList<FlashSaleActivityBO>(total, command.getPage(), command.getLimit(), flashSaleActivityBOS));
    }
}
