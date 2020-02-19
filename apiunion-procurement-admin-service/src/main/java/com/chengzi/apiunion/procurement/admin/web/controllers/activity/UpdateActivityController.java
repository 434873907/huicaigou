package com.chengzi.apiunion.procurement.admin.web.controllers.activity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chengzi.apiunion.item.service.ItemSkuService;
import com.chengzi.apiunion.sales.ActivityCO;
import com.chengzi.apiunion.sales.pojo.strategy.*;
import com.chengzi.common.util.CollectionUtil;
import org.apache.commons.lang3.StringUtils;
import org.summercool.web.servlet.BeanFactory;

import com.alibaba.fastjson.JSONObject;
import com.chengzi.apiunion.common.module.image.pojo.PartnerImageSizeDO;
import com.chengzi.apiunion.common.module.image.service.ImageService;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.activity.UpdateActivityForm;
import com.chengzi.apiunion.sales.enums.ActTypeEnum;
import com.chengzi.apiunion.sales.pojo.ActivityDO;
import com.chengzi.apiunion.sales.pojo.ActivityScope;
import com.chengzi.apiunion.sales.pojo.BannerImage;
import com.chengzi.apiunion.sales.service.ActivityService;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.StringUtil;

/** 
* @author 苏子 
* @date 2018年10月20日
*/
public class UpdateActivityController extends AbstractApiController<UpdateActivityForm>{

	@Override
	protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, UpdateActivityForm command)
			throws Exception {
	    long actId = command.getId();
		String actName = command.getActName();
		int actType = command.getActType();
		String actStrategyStr = command.getActStrategy();
		ActivityScope actScope = command.getActScope();
		ActivityScope excludeScope = command.getExcludeScope();
		List<Long> headItemIdsList = command.getHeadItemIds();
		BannerImage bannerImage = command.getBannerImage();
		
		String summary = command.getSummary();
		Date startTime = command.getStartTime();
		Date endTime = command.getEndTime();
		
		
		//解析样式
		Class<? extends BaseStrategy> strategyClass = ActTypeEnum.getStrategyClass(actType);
		BaseStrategy actStrategy = JSONObject.parseObject(actStrategyStr, strategyClass);

		if (actType == ActTypeEnum.PRICE_REDUCTION.getCode()) {
			PriceReductionStrategy strategy = JSONObject.parseObject(actStrategyStr, PriceReductionStrategy.class);
			ItemSkuService itemSkuService = BeanFactory.getBean(ItemSkuService.class);
			Result<String> result = null;
			if (strategy.getAllPriceReduction() != null) {
				result = itemSkuService.verifyActItemPrice(actScope.getItemIdList(), strategy.getAllPriceReduction());
			} else if (strategy.getAllOnePrice() != null) {
				result = itemSkuService.verifyActItemPrice(actScope.getItemIdList(), strategy.getAllOnePrice());
			}
			if (result != null && !result.isSuccess()) {
				return result;
			}
		} else if (actType == ActTypeEnum.FULL_REDUCTION.getCode()) {
			FullReductionStrategy strategy = JSONObject.parseObject(actStrategyStr, FullReductionStrategy.class);
			for (FullReductionLimit limit : strategy.getFullReductionLimitList()) {
				if (limit.getLimitPrice().compareTo(limit.getReductionPrice()) <= 0) {
					return Result.buildIllegalArgumentResult("满减限制价格不能低于优惠金额");
				}
			}
		} else if (actType == ActTypeEnum.DISCOUNT.getCode()) {
			DiscountStrategy strategy = JSONObject.parseObject(actStrategyStr, DiscountStrategy.class);
			if (strategy.getDiscount().compareTo(BigDecimal.valueOf(0.01)) < 0 || strategy.getDiscount().compareTo(BigDecimal.ONE) > 0) {
				return Result.buildIllegalArgumentResult("请设置正确的折扣");
			}
		} else if (actType == ActTypeEnum.FULL_GIFT.getCode()) {
			FullGiftStrategy strategy = JSONObject.parseObject(actStrategyStr, FullGiftStrategy.class);
			for (FullGiftLimit limit : strategy.getFullGiftLimitList()) {
				if (CollectionUtil.isEmpty(limit.getGiftInfoList())) {
					return Result.buildIllegalArgumentResult("请添加赠品");
				}
			}
		}

		ActivityDO activity = new ActivityDO();
		activity.setId(actId);
		activity.setActName(actName);
		activity.setActType(actType);
		activity.setActStrategy(actStrategy.toJsonString());
		activity.setActScope(actScope.toJsonString());
		if (excludeScope != null) {
		    activity.setExcludeScope(excludeScope.toJsonString());
        }
		activity.setHeadItemIds(StringUtil.formatArg(",", headItemIdsList));
		activity.setSummary(summary);
		activity.setStartTime(startTime);
		activity.setEndTime(endTime);
		
		ImageService imageService = BeanFactory.getBean(ImageService.class);
        if (StringUtils.isNotBlank(bannerImage.getBannerImageUrlForPC())) {
            PartnerImageSizeDO partnerImageSize = imageService.getImageByUrl(bannerImage.getBannerImageUrlForPC());
            bannerImage.setBannerImageUrlForPC(partnerImageSize.getImageUrl());
            bannerImage.setBannerImageHeightForPC(partnerImageSize.getImageHeight());
            bannerImage.setBannerImageWidthForPC(partnerImageSize.getImageWidth());
        }
        if (StringUtils.isNotBlank(bannerImage.getBannerImageUrlForAPP())) {
            PartnerImageSizeDO partnerImageSize = imageService.getImageByUrl(bannerImage.getBannerImageUrlForAPP());
            bannerImage.setBannerImageUrlForAPP(partnerImageSize.getImageUrl());
            bannerImage.setBannerImageHeightForAPP(partnerImageSize.getImageHeight());
            bannerImage.setBannerImageWidthForAPP(partnerImageSize.getImageWidth());
            
        }
        activity.setBannerImage(bannerImage.toJsonString());
		

		ActivityService activityService = BeanFactory.getBean(ActivityService.class);
		List<Long> mutexIds = new ArrayList<>();

		List<ActivityCO> activityCOS = activityService.queryActiveActivitiesFromRedis();
		Set<Long> idSet = CollectionUtil.getDisctinctFieldValueList(activityCOS, "id");
		for (long id : idSet) {
			if (id != activity.getId()) {
				mutexIds.add(id);
			}
		}

		if (CollectionUtil.isNotEmpty(mutexIds)) {
			activity.setHasMutexAct(1);
		}
		long id = activityService.updateActivity(activity, mutexIds);
		if (id<=0) {
			return Result.buildOpFailedResult("修改失败！");
		}
		return Result.buildSuccessMsg("修改成功！");
	}

}
