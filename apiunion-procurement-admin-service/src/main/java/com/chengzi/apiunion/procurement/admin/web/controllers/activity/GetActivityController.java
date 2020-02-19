package com.chengzi.apiunion.procurement.admin.web.controllers.activity;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chengzi.apiunion.common.module.currency.util.AmountUtil;
import com.chengzi.apiunion.common.module.image.enums.ImageBizType;
import com.chengzi.apiunion.common.module.image.util.ImageUtil;
import com.chengzi.apiunion.item.enums.ItemStatusEnum;
import com.chengzi.apiunion.item.pojo.ItemSkuInfo;
import com.chengzi.apiunion.item.pojo.ItemSkuInfo.SkuChannelInfo;
import com.chengzi.apiunion.item.service.ItemSkuService;
import com.chengzi.apiunion.sales.pojo.strategy.FullGiftLimit;
import com.chengzi.apiunion.sales.pojo.strategy.FullGiftStrategy;
import org.apache.commons.lang3.StringUtils;
import org.summercool.web.servlet.BeanFactory;

import com.alibaba.fastjson.JSONObject;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.pojo.cache.ItemCO;
import com.chengzi.apiunion.item.service.ItemService;
import com.chengzi.apiunion.procurement.admin.web.formbean.activity.GetActivityForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.activity.ActItemBO;
import com.chengzi.apiunion.procurement.admin.web.pojo.activity.ActivityBO;
import com.chengzi.apiunion.sales.enums.ActTypeEnum;
import com.chengzi.apiunion.sales.pojo.ActivityDO;
import com.chengzi.apiunion.sales.pojo.ActivityScope;
import com.chengzi.apiunion.sales.pojo.BannerImage;
import com.chengzi.apiunion.sales.pojo.strategy.BaseStrategy;
import com.chengzi.apiunion.sales.service.ActivityService;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;

/**
 * @author 苏子
 * @date 2018年11月13日
 */
public class GetActivityController extends AbstractApiController<GetActivityForm> {

    @Override
    protected Result<ActivityBO> doBiz(HttpServletRequest request, HttpServletResponse response, GetActivityForm command) throws Exception {
        ActivityService activityService = BeanFactory.getBean(ActivityService.class);
        ActivityDO activity = activityService.getActivity(command.getId());
        ActivityBO activityBO = ActivityBO.converter(activity);
        ItemService itemService = BeanFactory.getBean(ItemService.class);
        ItemSkuService itemSkuService = BeanFactory.getBean(ItemSkuService.class);

        if (activity.getActType() == ActTypeEnum.FULL_GIFT.getCode()) {
            FullGiftStrategy actStrategy = FullGiftStrategy.parse(activity.getActStrategy());
            List<FullGiftLimit> fullGiftLimitList = actStrategy.getFullGiftLimitList();

            // 添加商品信息
            for (FullGiftLimit fullGiftLimit : fullGiftLimitList) {
                List<FullGiftLimit.GiftInfo> giftInfoList = fullGiftLimit.getGiftInfoList();
                for (FullGiftLimit.GiftInfo giftInfo : giftInfoList) {
                    ItemCO itemCO = itemService.getItemWithCache(giftInfo.getItemId());
                    ItemSkuInfo skuInfo = itemSkuService.getSkuInfosBySkuId(giftInfo.getItemId(), giftInfo.getSkuId());
                    giftInfo.setItemName(itemCO.getName());
                    giftInfo.setItemImg(itemCO.getMainImageUrl());
                    giftInfo.setStockNum(skuInfo.getTotalStock());
                    giftInfo.setSkuValue(skuInfo.getDisplaySkuValue());
                    // 取第一个渠道的原价
                    for (SkuChannelInfo skuChannelInfo : skuInfo.getSkuChannels()) {
                        giftInfo.setPrice(AmountUtil.getDisplayPriceStartWithSymbol(itemCO.getCurrency(), skuChannelInfo.getOriginalPrice()));
                        break;
                    }
                }
            }

            activityBO.setActStrategy(actStrategy);
        } else {
            //策略解析
            Class<? extends BaseStrategy> strategyClass = ActTypeEnum.getStrategyClass(activity.getActType());
            BaseStrategy actStrategy = JSONObject.parseObject(activity.getActStrategy(), strategyClass);
            activityBO.setActStrategy(actStrategy);
        }

        activityBO.setActScope(scopeItemList(activity.getActScope()));
        activityBO.setExcludeScope(scopeItemList(activity.getExcludeScope()));
        activityBO.setHeadItemList(itemList(CollectionUtil.splitAsLong(activity.getHeadItemIds(), ",")));
        BannerImage bannerImage = JSONObject.parseObject(activity.getBannerImage(), BannerImage.class);
        bannerImage.setBannerImageUrlForAPP(bannerImage.getBannerImageUrlForAPP());
        bannerImage.setBannerImageUrlForPC(bannerImage.getBannerImageUrlForPC());
        activityBO.setBannerImage(bannerImage);

        return Result.buildSuccessResult(activityBO);
    }

    private List<ActItemBO> scopeItemList(String scopeStr) {
        List<ActItemBO> actScopeItemList = new ArrayList<>();
        if (StringUtils.isNotBlank(scopeStr)) {
            ActivityScope actScope = JSONObject.parseObject(scopeStr, ActivityScope.class);
            List<Long> itemIdList = actScope.getItemIdList();
            actScopeItemList.addAll(itemList(itemIdList));
        }
        return actScopeItemList;
    }
    
    private List<ActItemBO> itemList(List<Long> itemIdList) {
        List<ActItemBO> actScopeItemList = new ArrayList<>();
        ItemService itemService = BeanFactory.getBean(ItemService.class);
        List<ItemCO> itemCOList = itemService.getItemWithCacheByIds(itemIdList);
        for (ItemCO itemCO : itemCOList) {
            if (itemCO.isDeleted()) {
                continue;
            }
            if (itemCO.getStatus() != ItemStatusEnum.ONLINE.getCode()) {
                actScopeItemList.add(new ActItemBO(itemCO.getId(), itemCO.getName() + "(已下架)", itemCO.getMainImageUrl(), AmountUtil.getDisplayPriceStartWithSymbol(itemCO.getCurrency(), itemCO.getPrice())));
            } else {
                actScopeItemList.add(new ActItemBO(itemCO.getId(), itemCO.getName(), itemCO.getMainImageUrl(), AmountUtil.getDisplayPriceStartWithSymbol(itemCO.getCurrency(), itemCO.getPrice())));
            }
        }
        return actScopeItemList;
    }

}
