package com.chengzi.apiunion.procurement.admin.web.controllers.activity;

import com.alibaba.fastjson.JSON;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.pojo.ItemSkuInfo;
import com.chengzi.apiunion.item.pojo.cache.ItemCO;
import com.chengzi.apiunion.item.service.ItemService;
import com.chengzi.apiunion.item.service.ItemSkuService;
import com.chengzi.apiunion.procurement.admin.web.formbean.activity.GetFlashSaleActivityForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.activity.FlashSaleActivityBO;
import com.chengzi.apiunion.sales.pojo.FlashSaleActivityDO;
import com.chengzi.apiunion.sales.pojo.ParticipateItem;
import com.chengzi.apiunion.sales.service.ActivityService;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * </p>
 * User: 摩天
 * Date: 2019-02-20 16:46
 */
public class GetFlashSaleActivityController extends AbstractApiController<GetFlashSaleActivityForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, GetFlashSaleActivityForm command) throws Exception {
        ActivityService activityService = BeanFactory.getBean(ActivityService.class);
        ItemService itemService = BeanFactory.getBean(ItemService.class);
        ItemSkuService itemSkuService = BeanFactory.getBean(ItemSkuService.class);
        FlashSaleActivityDO flashSaleActivityDO = activityService.getFlashSaleActivity(command.getActivityId());
        if (flashSaleActivityDO != null) {
            List<ParticipateItem> participateItems = JSON.parseArray(flashSaleActivityDO.getParticipateItemNum(), ParticipateItem.class);
            List<Long> itemIds = new ArrayList<>();
            participateItems.forEach(x -> itemIds.add(x.getItemId()));
            Map<Long, ItemSkuInfo> skuInfosMap = CollectionUtil.toMap(itemSkuService.getSkuInfosByItemIds(itemIds), "id");
            Map<Long, ItemCO> itemCOMap = CollectionUtil.toMap(itemService.getItemWithCacheByIds(itemIds), "id");
            FlashSaleActivityBO flashSaleActivityBO = FlashSaleActivityBO.convert(flashSaleActivityDO, itemCOMap, skuInfosMap);
            return Result.buildSuccessResult(flashSaleActivityBO);
        }
        return Result.buildIllegalArgumentResult("活动不存在");

    }
}
