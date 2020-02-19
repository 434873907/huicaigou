/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.controllers.item;

import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chengzi.apiunion.common.data.search.elastic.pojo.OrderBy;
import com.chengzi.apiunion.item.enums.ItemApproveTypeEnum;
import com.chengzi.apiunion.item.enums.ItemStatusEnum;
import com.chengzi.apiunion.item.event.pojo.ItemApproveUpdatedEvent;
import com.chengzi.apiunion.item.pojo.search.ItemApproveSearchBO;
import com.chengzi.apiunion.item.pojo.search.query.ItemApproveSearchQuery;
import com.chengzi.apiunion.item.search.ItemApproveSearchService;
import com.chengzi.common.util.EnumUtil;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.summercool.util.LangUtil;
import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.data.enums.CurrencyEnum;
import com.chengzi.apiunion.common.data.search.elastic.pojo.SearchResult;
import com.chengzi.apiunion.common.event.EventBusFactory;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.enums.ItemApproveStatusEnum;
import com.chengzi.apiunion.item.pojo.ItemSkuInfo;
import com.chengzi.apiunion.item.pojo.ItemSkuSupplierApproveDO;
import com.chengzi.apiunion.item.pojo.ItemSkuSupplierApproveQuery;
import com.chengzi.apiunion.item.pojo.search.enums.ItemSortTypeEnum;
import com.chengzi.apiunion.item.service.ItemSkuSupplierApproveService;
import com.chengzi.apiunion.partnercategory.constant.PartnerCategoryConstant;
import com.chengzi.apiunion.partnercategory.pojo.CategoryUnit;
import com.chengzi.apiunion.partnercategory.pojo.PartnerCategoryDO;
import com.chengzi.apiunion.partnercategory.service.PartnerCategoryService;
import com.chengzi.apiunion.procurement.admin.web.formbean.item.GetItemForFastApprovedForm;
import com.chengzi.apiunion.procurement.admin.web.formbean.item.ItemListForm;
import com.chengzi.apiunion.procurement.admin.web.helper.order.item.ItemHelper;
import com.chengzi.apiunion.procurement.admin.web.pojo.item.ItemDetailBO;
import com.chengzi.apiunion.procurement.admin.web.pojo.item.SupplierStockBO;
import com.chengzi.common.data.enums.BooleanStatusEnum;
import com.chengzi.common.data.support.BigDecimalRange;
import com.chengzi.common.data.support.Range;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;

/**
 * 获取快速审核商品
 *
 * @author 行者
 */
public class GetItemForFastApprove2Controller extends AbstractApiController<GetItemForFastApprovedForm> {

    private static final String[]  includesFields = { "approveItemId","approveModifyTime"};

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, GetItemForFastApprovedForm command) throws Exception {
        ItemSkuSupplierApproveService itemSkuSupplierApproveService = BeanFactory.getBean(ItemSkuSupplierApproveService.class);
        PartnerCategoryService categoryService = BeanFactory.getBean(PartnerCategoryService.class);
        ItemApproveSearchService itemApproveSearchService = BeanFactory.getBean(ItemApproveSearchService.class);

        ItemDetailBO itemDetailBO = null;
        Date sortDate = new Date();
        Map<String, ItemSkuSupplierApproveDO> approveDOMap = null;
        if (command.getItemId() > 0) {
            long itemId = command.getItemId();
            ItemSkuSupplierApproveQuery approveQuery = new ItemSkuSupplierApproveQuery();
            approveQuery.setItemIds(CollectionUtil.asArrayList(itemId));
            approveQuery.setStatusList(CollectionUtil.asArrayList(ItemApproveStatusEnum.WAIT_APPROVE.getCode()));
            List<ItemSkuSupplierApproveDO> approveDOList = itemSkuSupplierApproveService.query(approveQuery);
            approveDOMap = CollectionUtil.toMap(approveDOList, "skuId+channelType+supplierId");
            itemDetailBO = ItemHelper.buildItemDetail(itemId);
            if (itemDetailBO == null) {
                return Result.buildNotExistedResult("商品不存在！");
            }
        } else {
            ItemApproveSearchQuery searchQuery = new ItemApproveSearchQuery();
            searchQuery.setStatusList(CollectionUtil.asArrayList(ItemStatusEnum.INIT, ItemStatusEnum.ONLINE, ItemStatusEnum.OFFLINE));
            searchQuery.setBrandIds(command.getBrandIds());
            searchQuery.setShopIds(command.getShopIds());
            searchQuery.setSupplierIds(command.getSupplierIds());
            searchQuery.setChannelTypes(command.getItemChannelTypes());
            searchQuery.setExcludesItemIds(new HashSet<>(command.getExceptItemId()));
            if (command.getCateId() != null) {
                PartnerCategoryDO cateDO = categoryService.getById(command.getCateId());
                if (cateDO == null) {
                    return Result.buildIllegalArgumentResult("类目不存在");
                }
                if (PartnerCategoryConstant.THIRD_CLASS_CATEGORY == cateDO.getDepth()) {
                    searchQuery.setCategorys(CollectionUtil.asHashSet(new CategoryUnit(0, 0, cateDO.getId())));
                } else if (PartnerCategoryConstant.SECOND_CLASS_CATEGORY == cateDO.getDepth()) {
                    searchQuery.setCategorys(CollectionUtil.asHashSet(new CategoryUnit(0, cateDO.getId(), 0)));
                } else if (PartnerCategoryConstant.FIRST_CLASS_CATEGORY == cateDO.getDepth()) {
                    searchQuery.setCategorys(CollectionUtil.asHashSet(new CategoryUnit(cateDO.getId(), 0, 0)));
                }
            }

            if (StringUtils.isNotBlank(command.getKeyword())) {
                if (ItemListForm.ItemListKeywordTypeEnum.ITEM_NAME == command.getKeywordType()) {
                    searchQuery.setItemName(command.getKeyword());
                } else if (ItemListForm.ItemListKeywordTypeEnum.UPC == command.getKeywordType()) {
                    searchQuery.setUpc(command.getKeyword());
                } else if (ItemListForm.ItemListKeywordTypeEnum.ITEM_ID == command.getKeywordType()) {
                    Long itemId = LangUtil.parseLong(command.getKeyword());
                    if (itemId == null || itemId <= 0) {
                        return Result.buildIllegalArgumentResult("商品ID格式不正确");
                    }
                    searchQuery.setItemIds(CollectionUtil.asHashSet(itemId));
                } else {
                    searchQuery.setKeyword(command.getKeyword());
                }
            }

            searchQuery.setPriceRange(new BigDecimalRange(command.getMinPrice(), command.getMaxPrice()));
            searchQuery.setRouteId(RequestContext.getRouteId());
            searchQuery.setApproveStatusList(CollectionUtil.asArrayList(ItemApproveStatusEnum.WAIT_APPROVE));

            if (command.getApproveType() > 0) {
                searchQuery.setApproveTypeList(CollectionUtil.asArrayList(EnumUtil.parse(ItemApproveTypeEnum.class,command.getApproveType())));
            }

            searchQuery.setOrderBy(CollectionUtil.asArrayList(new OrderBy(SortBuilders.fieldSort("approveModifyTime").order(SortOrder.DESC))));

            searchQuery.setSize(100);// es数据可能不一致，因此多查几条
            searchQuery.setIncludesFields(includesFields);
            searchQuery.setStatus(null);
            if (command.getSortTime() != null) {
                Range<Date> range = new Range<>();
                searchQuery.setModifyTimeRange(range);
                if (command.getDirection() == BooleanStatusEnum.YES.getCode()) {
                    //下一个
                    range.setRight(command.getSortTime());
                    searchQuery.setOrderBy(ItemSortTypeEnum.APPROVE_LIST_DESC.getOrderBys());
                }else {
                    range.setLeft(command.getSortTime());
                    searchQuery.setOrderBy(ItemSortTypeEnum.APPROVE_LIST_ASC.getOrderBys());
                }
            } else {
                searchQuery.setOrderBy(ItemSortTypeEnum.APPROVE_LIST_DESC.getOrderBys());
            }
            SearchResult<ItemApproveSearchBO> searchResult = itemApproveSearchService.query(searchQuery);
            if (CollectionUtil.isEmpty(searchResult.getData())) {
                return Result.buildNotExistedResult("无更多待审核商品了！");
            }

            for (ItemApproveSearchBO itemApproveSearchBO : searchResult.getData()) {
                long itemId = itemApproveSearchBO.getApproveItemId();
                ItemSkuSupplierApproveQuery approveQuery = new ItemSkuSupplierApproveQuery();
                approveQuery.setItemIds(CollectionUtil.asArrayList(itemId));
                approveQuery.setStatusList(CollectionUtil.asArrayList(ItemApproveStatusEnum.WAIT_APPROVE.getCode()));
                List<ItemSkuSupplierApproveDO> approveDOList = itemSkuSupplierApproveService.query(approveQuery);
                if (CollectionUtil.isEmpty(approveDOList)) {
                    EventBusFactory.getSyncEventBus().post(new ItemApproveUpdatedEvent(itemId));
                    continue;
                }
                approveDOMap = CollectionUtil.toMap(approveDOList, "skuId+channelType+supplierId");
                itemDetailBO = ItemHelper.buildItemDetail(itemId);
                if (itemDetailBO != null) {
                    sortDate = itemApproveSearchBO.getApproveModifyTime();
                    break;
                }
            }
        }

        if (itemDetailBO == null) {
            return Result.buildNotExistedResult("无更多待审核商品了！");
        }

        Set<BigDecimal> priceSet = new HashSet<>();
        for (ItemSkuInfo skuInfo : itemDetailBO.getSkuList()) {
            for (ItemSkuInfo.SkuChannelInfo skuChannelInfo : skuInfo.getSkuChannels()) {
                priceSet.add(skuChannelInfo.getPrice());
                Iterator<ItemSkuInfo.SupplierStock> iterator = skuChannelInfo.getSupplierStocks().iterator();
                while (iterator.hasNext()) {
                    SupplierStockBO supplierStockBO = (SupplierStockBO) iterator.next();
                    String key = String.format("%d+%d+%d", skuInfo.getId(), skuChannelInfo.getChannelType(), supplierStockBO.getSupplierId());
                    ItemSkuSupplierApproveDO approveDO = approveDOMap.get(key);
                    if (approveDO != null) {
                        //设置审核类型
                        supplierStockBO.setApproveType(approveDO.getType());
                    }
                }
            }
        }
        itemDetailBO.setPriceRange(getPriceRange(priceSet,itemDetailBO.getCurrency()));
        itemDetailBO.setSortTime(sortDate);

        return Result.buildSuccessResult(itemDetailBO);
    }


    private String getPriceRange(Set<BigDecimal> priceSet, int currency) {
        String priceRange = null;
        String symbol = CurrencyEnum.getSymbol(currency);
        if (CollectionUtil.isNotEmpty(priceSet)) {
            if (priceSet.size() == 1) {
                priceRange = String.format("%s%s",symbol,priceSet.iterator().next().toString());
            } else {
                BigDecimal max = Collections.max(priceSet);
                BigDecimal min = Collections.min(priceSet);
                priceRange = String.format("%s%s~%s%s",symbol,min.toString(),symbol,max.toString());
        ;
            }
        }
        return priceRange;
    }
}