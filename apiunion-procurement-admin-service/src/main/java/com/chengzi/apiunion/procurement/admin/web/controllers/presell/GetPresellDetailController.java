package com.chengzi.apiunion.procurement.admin.web.controllers.presell;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.pojo.ItemSkuInfo;
import com.chengzi.apiunion.item.pojo.cache.ItemCO;
import com.chengzi.apiunion.item.service.ItemChannelTypeService;
import com.chengzi.apiunion.item.service.ItemService;
import com.chengzi.apiunion.item.service.ItemSkuService;
import com.chengzi.apiunion.procurement.admin.web.pojo.presell.PresellBO;
import com.chengzi.apiunion.procurement.admin.web.pojo.presell.PresellBO.ItemInfo;
import com.chengzi.apiunion.procurement.admin.web.pojo.presell.PresellBO.SkuInfo;
import com.chengzi.apiunion.presell.pojo.PresellDO;
import com.chengzi.apiunion.presell.pojo.PresellSkuLimitDO;
import com.chengzi.apiunion.presell.service.PresellService;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import com.chengzi.common.util.DateUtil;
import com.chengzi.common.web.formbean.IdForm;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 月汐
 * @date 2019/2/26 11:04
 */
public class GetPresellDetailController extends AbstractApiController<IdForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, IdForm command) throws Exception {
        PresellService presellService = BeanFactory.getBean(PresellService.class);
        ItemService itemService = BeanFactory.getBean(ItemService.class);
        ItemSkuService itemSkuService = BeanFactory.getBean(ItemSkuService.class);
        ItemChannelTypeService itemChannelTypeService = BeanFactory.getBean(ItemChannelTypeService.class);

        PresellDO presellDO = presellService.getById(command.getId());

        PresellBO bo = new PresellBO();
        bo.setId(presellDO.getId());
        bo.setName(presellDO.getName());
        bo.setDepositStartTime(DateUtil.formatDate(presellDO.getDepositStartTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
        bo.setDepositEndTime(DateUtil.formatDate(presellDO.getDepositEndTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
        bo.setTailStartTime(DateUtil.formatDate(presellDO.getTailStartTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
        bo.setTailEndTime(DateUtil.formatDate(presellDO.getTailEndTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
        bo.setCurrency(presellDO.getCurrency());
        bo.setDepositAmount(presellDO.getDepositAmount());
        bo.setTailAmount(presellDO.getTailAmount());
        bo.setLimit(presellDO.isLimit());
        bo.setStatusAfterAct(presellDO.getStatusAfterAct());

        ItemCO itemCO = itemService.getItemWithCache(presellDO.getItemId());
        String channelName = itemChannelTypeService.getChannelNameInCache(presellDO.getChannelType());
        List<ItemSkuInfo> itemSkuInfoList = itemSkuService.getSkuInfosWithCacheByItemId(presellDO.getItemId());

        Map<Long, ItemSkuInfo> itemSkuInfoMap = CollectionUtil.toMap(itemSkuInfoList, "id");

        ItemInfo itemInfo = new ItemInfo();
        itemInfo.setItemId(itemCO.getId());
        itemInfo.setItemName(itemCO.getName());
        itemInfo.setMainImg(itemCO.getMainImageUrl());
        itemInfo.setChannelType(presellDO.getChannelType());
        itemInfo.setChannelName(channelName);

        List<SkuInfo> skuInfoList = new ArrayList<>();
        itemInfo.setSkuInfoList(skuInfoList);

        List<PresellSkuLimitDO> sellLimitList = presellDO.getSkuLimitList();
        for (PresellSkuLimitDO sellLimit : sellLimitList) {
            ItemSkuInfo itemSkuInfo = itemSkuInfoMap.get(sellLimit.getSkuId());
            if (itemSkuInfo != null) {
                SkuInfo skuInfo = new SkuInfo();
                skuInfo.setSkuId(itemSkuInfo.getId());
                skuInfo.setSkuValue(itemSkuInfo.getDisplaySkuValue());
                skuInfo.setStockNum(itemSkuInfo.getTotalStock());
                skuInfo.setLimitNum(sellLimit.getLimitNum());
                skuInfo.setPrice(itemSkuInfo.getSkuChannels().get(0).getPrice());
                skuInfoList.add(skuInfo);
            }
        }

        bo.setItemInfo(itemInfo);

        return Result.buildSuccessResult(bo);
    }
}
