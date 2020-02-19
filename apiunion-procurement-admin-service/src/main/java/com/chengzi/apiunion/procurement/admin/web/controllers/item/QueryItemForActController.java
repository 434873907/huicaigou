package com.chengzi.apiunion.procurement.admin.web.controllers.item;

import com.chengzi.apiunion.common.data.search.elastic.pojo.SearchResult;
import com.chengzi.apiunion.common.module.currency.util.AmountUtil;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.enums.ItemStatusEnum;
import com.chengzi.apiunion.item.pojo.ItemSkuInfo;
import com.chengzi.apiunion.item.pojo.cache.ItemCO;
import com.chengzi.apiunion.item.pojo.search.ItemSearchBO;
import com.chengzi.apiunion.item.pojo.search.query.ItemSearchQuery;
import com.chengzi.apiunion.item.search.ItemSearchService;
import com.chengzi.apiunion.item.service.ItemService;
import com.chengzi.apiunion.item.service.ItemSkuService;
import com.chengzi.apiunion.item.util.SkuUtil;
import com.chengzi.apiunion.partnercategory.constant.PartnerCategoryConstant;
import com.chengzi.apiunion.partnercategory.pojo.CategoryUnit;
import com.chengzi.apiunion.partnercategory.pojo.PartnerCategoryDO;
import com.chengzi.apiunion.partnercategory.service.PartnerCategoryService;
import com.chengzi.apiunion.procurement.admin.web.formbean.item.QueryItemForActForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.item.ItemForActBO;
import com.chengzi.apiunion.procurement.admin.web.pojo.item.ItemForActBO.ChannelInfo;
import com.chengzi.apiunion.procurement.admin.web.pojo.item.ItemForActBO.SkuInfo;
import com.chengzi.common.data.beans.PageDataList;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import org.apache.commons.lang3.StringUtils;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 月汐
 * @date 2019/2/20 11:10
 */
public class QueryItemForActController extends AbstractApiController<QueryItemForActForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, QueryItemForActForm command) throws Exception {
        PartnerCategoryService categoryService = BeanFactory.getBean(PartnerCategoryService.class);
        ItemSearchService itemSearchService = BeanFactory.getBean(ItemSearchService.class);
        ItemService itemService = BeanFactory.getBean(ItemService.class);

        ItemSearchQuery query = new ItemSearchQuery();
        query.setFrom(command.getOffset());
        query.setSize(command.getLimit());
        query.setStatus(ItemStatusEnum.ONLINE);
        if (command.getBrandId() != null && command.getBrandId() > 0) {
            query.setBrandIds(CollectionUtil.asHashSet(command.getBrandId()));
        }
        if (command.getCateId() != null) {
            PartnerCategoryDO cateDO = categoryService.getById(command.getCateId());
            if (cateDO == null) {
                return Result.buildIllegalArgumentResult("类目不存在");
            }
            if (PartnerCategoryConstant.THIRD_CLASS_CATEGORY == cateDO.getDepth()) {
                query.setCategorys(CollectionUtil.asHashSet(new CategoryUnit(0, 0, cateDO.getId())));
            } else if (PartnerCategoryConstant.SECOND_CLASS_CATEGORY == cateDO.getDepth()) {
                query.setCategorys(CollectionUtil.asHashSet(new CategoryUnit(0, cateDO.getId(), 0)));
            } else if (PartnerCategoryConstant.FIRST_CLASS_CATEGORY == cateDO.getDepth()) {
                query.setCategorys(CollectionUtil.asHashSet(new CategoryUnit(cateDO.getId(), 0, 0)));
            }
        }

        if (StringUtils.isNotBlank(command.getKeyword())) {
            query.setNameOrUpcOrId(command.getKeyword());
        }

        if (command.isGift()) {
            query.setIsGift(1);
            query.setGiftActId(0L);
        } else if (!command.isGift()) {
            query.setIsGift(0);
        }

        if (command.isPresell() == null) {
            query.setIsPresell(-1);
        } else if (command.isPresell()) {
            query.setIsPresell(1);
        } else {
            query.setIsPresell(0);
        }

        SearchResult<ItemSearchBO> searchResult = itemSearchService.query(query);
        if (searchResult.isEmpty()) {
            return Result.buildSuccessResult(new PageDataList<>(0, command.getPage(), command.getLimit(), new ArrayList<>()));
        }
        List<Long> itemIds = CollectionUtil.getFieldValueList(searchResult.getData(), "id");
        List<ItemCO> itemList = itemService.getItemWithCacheByIds(itemIds);

        return Result
                .buildSuccessResult(new PageDataList<>(searchResult.getTotalHits(), command.getPage(), command.getLimit(), convert(itemList)));
    }

    private List<ItemForActBO> convert(List<ItemCO> itemList) {
        ItemSkuService itemSkuService = BeanFactory.getBean(ItemSkuService.class);

        List<ItemSkuInfo> itemSkuInfoList = itemSkuService.getSkuInfosWithCacheByItemIds(CollectionUtil.getFieldValueList(itemList, "id"));
        Map<Long, List<ItemSkuInfo>> itemSkuInfoMap = new HashMap<>();
        for (ItemSkuInfo itemSkuInfo : itemSkuInfoList) {
            List<ItemSkuInfo> skuInfoList = itemSkuInfoMap.computeIfAbsent(itemSkuInfo.getItemId(), k -> new ArrayList<>());
            skuInfoList.add(itemSkuInfo);
        }

        List<ItemForActBO> boList = new ArrayList<>();
        for (ItemCO item : itemList) {
            ItemForActBO bo = new ItemForActBO();
            bo.setItemId(item.getId());
            bo.setItemName(item.getName());
            bo.setMainImg(item.getMainImageUrl());
            bo.setChannelInfoList(convertSkuInfo(itemSkuInfoMap.get(item.getId()), item.getCurrency(), item.getUnit()));
            boList.add(bo);
        }
        return boList;
    }

    private List<ChannelInfo> convertSkuInfo(List<ItemSkuInfo> skuInfoList, int currency, String unit) {
        Map<Integer, ChannelInfo> channelInfoMap = new HashMap<>();

        for (ItemSkuInfo itemSkuInfo : skuInfoList) {
            if (CollectionUtil.isEmpty(itemSkuInfo.getSkuChannels())) {
                continue;
            }
            for (ItemSkuInfo.SkuChannelInfo skuChannelInfo : itemSkuInfo.getSkuChannels()) {
                if (skuChannelInfo.getTotalStock() != null && skuChannelInfo.getTotalStock() > 0) {
                    ChannelInfo channelInfo = channelInfoMap.get(skuChannelInfo.getChannelType());
                    if (channelInfo == null) {
                        channelInfo = new ChannelInfo();
                        channelInfo.setChannelType(skuChannelInfo.getChannelType());
                        channelInfo.setChannelName(skuChannelInfo.getChannelName());
                        channelInfo.setSkuInfoList(new ArrayList<>());
                    }

                    SkuInfo skuInfo = new SkuInfo();
                    skuInfo.setSkuId(itemSkuInfo.getId());
                    skuInfo.setSkuValue(SkuUtil.buildSkuDescIncludeGroup(itemSkuInfo, unit));
                    skuInfo.setStockNum(itemSkuInfo.getTotalStock());
                    skuInfo.setPrice(AmountUtil.getAdminDisplayPriceStartWithSymbol(currency, skuChannelInfo.getChannelPrice()));

                    channelInfo.getSkuInfoList().add(skuInfo);

                    channelInfoMap.put(skuChannelInfo.getChannelType(), channelInfo);
                }
            }
        }

        return new ArrayList<>(channelInfoMap.values());
    }
}
