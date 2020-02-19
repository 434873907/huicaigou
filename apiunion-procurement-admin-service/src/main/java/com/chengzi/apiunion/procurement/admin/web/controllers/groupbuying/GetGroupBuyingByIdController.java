package com.chengzi.apiunion.procurement.admin.web.controllers.groupbuying;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.pojo.ItemSkuInfo;
import com.chengzi.apiunion.item.pojo.ItemSkuSupplierDO;
import com.chengzi.apiunion.item.pojo.cache.ItemCO;
import com.chengzi.apiunion.item.service.ItemChannelTypeService;
import com.chengzi.apiunion.item.service.ItemService;
import com.chengzi.apiunion.item.service.ItemSkuService;
import com.chengzi.apiunion.procurement.admin.web.pojo.groupbuying.GroupBuyingBO;
import com.chengzi.apiunion.procurement.admin.web.pojo.groupbuying.GroupBuyingBO.SkuInfo;
import com.chengzi.apiunion.groupbuying.pojo.GroupBuyingDO;
import com.chengzi.apiunion.groupbuying.service.GroupBuyingService;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import com.chengzi.common.util.DateUtil;
import com.chengzi.common.web.formbean.IdForm;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取团购详情
 *
 * @author 月汐
 * @date 2019/2/19 10:08
 */
public class GetGroupBuyingByIdController extends AbstractApiController<IdForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, IdForm command) throws Exception {
        GroupBuyingService groupBuyingService = BeanFactory.getBean(GroupBuyingService.class);
        ItemService itemService = BeanFactory.getBean(ItemService.class);
        ItemSkuService itemSkuService = BeanFactory.getBean(ItemSkuService.class);
        ItemChannelTypeService itemChannelTypeService = BeanFactory.getBean(ItemChannelTypeService.class);

        GroupBuyingDO groupBuyingDO = groupBuyingService.getById(command.getId());
        ItemCO itemCO = itemService.getItemWithCache(groupBuyingDO.getItemId());

        List<Long> skuIds = CollectionUtil.splitAsLong(groupBuyingDO.getSkuIds(), ",");
        List<ItemSkuSupplierDO> itemSkuSupplierDOList = itemSkuService.getSkuSupplierBySkuIds(skuIds, itemCO.getId());
        List<ItemSkuInfo> itemSkuInfoList = itemSkuService.getSkuInfosByItemId(itemCO.getId());
        Map<Long, ItemSkuInfo> itemSkuInfoMap = CollectionUtil.toMap(itemSkuInfoList, "id");

        GroupBuyingBO bo = new GroupBuyingBO();
        bo.setId(groupBuyingDO.getId());
        bo.setName(groupBuyingDO.getName());
        bo.setStartTime(DateUtil.formatDate(groupBuyingDO.getStartTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
        bo.setEndTime(DateUtil.formatDate(groupBuyingDO.getEndTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
        bo.setBanner(groupBuyingDO.getBanner());
        bo.setDisableCoupons(groupBuyingDO.isDisableCoupons());
        bo.setItemId(itemCO.getId());
        bo.setItemImage(itemCO.getMainImageUrl());
        bo.setItemName(itemCO.getName());
        bo.setChannelType(groupBuyingDO.getChannelType());
        bo.setChannelName(itemChannelTypeService.getChannelNameInCache(groupBuyingDO.getChannelType()));
        bo.setGroupPrice(groupBuyingDO.getGroupPrice());
        bo.setStockNum(groupBuyingDO.getStockNum());
        bo.setLimit(groupBuyingDO.isLimit());
        bo.setLimitNum(groupBuyingDO.getLimitNum());
        bo.setCurrency(groupBuyingDO.getCurrency());

        Map<Long, SkuInfo> skuInfoMap = new HashMap<>();
        for (ItemSkuSupplierDO itemSkuSupplierDO : itemSkuSupplierDOList) {
            if (itemSkuSupplierDO.getChannelType() == groupBuyingDO.getChannelType()) {
                SkuInfo skuInfo = skuInfoMap.get(itemSkuSupplierDO.getSkuId());
                if (skuInfo == null) {
                    ItemSkuInfo itemSkuInfo = itemSkuInfoMap.get(itemSkuSupplierDO.getSkuId());
                    if (itemSkuInfo == null) {
                        continue;
                    }
                    skuInfo = new SkuInfo();
                    skuInfo.setSkuId(itemSkuSupplierDO.getSkuId());
                    skuInfo.setSkuValue(itemSkuInfo.getDisplaySkuValue());
                    skuInfo.setOriginalPrice(itemSkuSupplierDO.getChannelPrice());
                    skuInfo.setStock(0);
                }
                skuInfo.setStock(skuInfo.getStock() + itemSkuSupplierDO.getSupplierQuantity());
                skuInfoMap.put(itemSkuSupplierDO.getSkuId(), skuInfo);
            }
        }
        bo.setSkuInfoList(new ArrayList<>(skuInfoMap.values()));

        return Result.buildSuccessResult(bo);
    }
}
