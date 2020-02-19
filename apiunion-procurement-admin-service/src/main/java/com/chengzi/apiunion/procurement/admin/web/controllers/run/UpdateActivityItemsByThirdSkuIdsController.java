package com.chengzi.apiunion.procurement.admin.web.controllers.run;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chengzi.apiunion.common.data.search.elastic.pojo.SearchResult;
import com.chengzi.apiunion.item.enums.ThirdSyncItemTypeEnum;
import com.chengzi.apiunion.item.pojo.ItemSkuInfo;
import com.chengzi.apiunion.item.pojo.ThirdItemSkuAttr;
import com.chengzi.apiunion.item.search.ItemSearchService;
import com.chengzi.apiunion.item.service.ItemSkuService;
import com.chengzi.apiunion.procurement.admin.web.controllers.run.form.UpdateActivityItemByThirdSkuIdForm;
import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.sales.ActivityCO;
import com.chengzi.apiunion.sales.pojo.ActivityDO;
import com.chengzi.apiunion.sales.pojo.ActivityScope;
import com.chengzi.apiunion.sales.service.ActivityService;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;

/** 
* @author 行者
*/
public class UpdateActivityItemsByThirdSkuIdsController extends AbstractApiController<UpdateActivityItemByThirdSkuIdForm>{

	@Override
	protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, UpdateActivityItemByThirdSkuIdForm command)
			throws Exception {
	    long actId = command.getActivityId();
		List<String> thirdSkuIds = command.getThirdSkuIds();

		ActivityService activityService = BeanFactory.getBean(ActivityService.class);
		ItemSearchService itemSearchService = BeanFactory.getBean(ItemSearchService.class);
		ItemSkuService itemSkuService = BeanFactory.getBean(ItemSkuService.class);
		ActivityDO activity = activityService.getActivity(actId);
		ActivityScope activityScope = ActivityScope.fromJson(activity.getActScope());

		SearchResult<Long> result = itemSearchService.queryByThirdSkuIds(ThirdSyncItemTypeEnum.SUPER_EXP.getCode(), thirdSkuIds);

		if (result.isEmpty()) {
			return Result.buildSuccessMsg("修改失败！");
		}
		List<Long> itemIdList = activityScope.getItemIdList();

		// 查出已存在的第三方skuId的商品
		List<ItemSkuInfo> skuInfos = itemSkuService.getAllSkuInfosByItemId(result.getData());
		Map<String, Long> skuInfoMap = new HashMap<>();

		for (ItemSkuInfo itemSkuInfo : skuInfos) {
			if (CollectionUtil.isEmpty(itemSkuInfo.getSkuChannels())) {
				continue;
			}
			for (ItemSkuInfo.SkuChannelInfo skuChannelInfo : itemSkuInfo.getSkuChannels()) {
				if (CollectionUtil.isEmpty(skuChannelInfo.getSupplierStocks())) {
					continue;
				}
				for (ItemSkuInfo.SupplierStock supplierStock : skuChannelInfo.getSupplierStocks()) {
					ThirdItemSkuAttr thirdAttr = supplierStock.getThirdAttr();
					if (thirdAttr != null && thirdSkuIds.contains(thirdAttr.getThirdSkuId())) {
						skuInfoMap.put(thirdAttr.getThirdSkuId(),itemSkuInfo.getItemId());
					}
				}
			}
		}

		thirdSkuIds.forEach(skuId -> {
			Long itemId = skuInfoMap.get(skuId);
			if (itemId != null && itemId > 0) {
				if (!itemIdList.contains(itemId)) {
					itemIdList.add(itemId);
				}
			}
		});

		activityScope.setItemIdList(itemIdList);
		activity.setActScope(activityScope.toJsonString());

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
