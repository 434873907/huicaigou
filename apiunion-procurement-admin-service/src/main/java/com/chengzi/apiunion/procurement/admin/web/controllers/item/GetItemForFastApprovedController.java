/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.controllers.item;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.data.enums.CurrencyEnum;
import com.chengzi.apiunion.common.data.search.elastic.pojo.SearchResult;
import com.chengzi.apiunion.common.event.EventBusFactory;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.enums.ItemApproveStatusEnum;
import com.chengzi.apiunion.item.event.pojo.ItemUpdatedEvent;
import com.chengzi.apiunion.item.pojo.*;
import com.chengzi.apiunion.item.pojo.search.ItemSearchBO;
import com.chengzi.apiunion.item.pojo.search.enums.ItemSortTypeEnum;
import com.chengzi.apiunion.item.pojo.search.query.ItemSearchQuery;
import com.chengzi.apiunion.item.search.ItemSearchService;
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
import com.chengzi.common.util.ContextEnvUtil;
import org.apache.commons.lang3.StringUtils;
import org.summercool.util.LangUtil;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

/**
 * 获取快速审核商品
 * 
 * @author 行者
 */
@Deprecated
public class GetItemForFastApprovedController extends AbstractApiController<GetItemForFastApprovedForm> {

    private static final String[]  includesFields = { "id","currency"};

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, GetItemForFastApprovedForm command) throws Exception {
        ItemSearchService itemSearchService = BeanFactory.getBean(ItemSearchService.class);
        ItemSkuSupplierApproveService itemSkuSupplierApproveService = BeanFactory.getBean(ItemSkuSupplierApproveService.class);
        PartnerCategoryService categoryService = BeanFactory.getBean(PartnerCategoryService.class);
        ItemSearchQuery query = new ItemSearchQuery();
        query.setBrandIds(command.getBrandIds());
        query.setShopIds(command.getShopIds());
        query.setSupplierIds(command.getSupplierIds());
        query.setChannelTypes(command.getItemChannelTypes());
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
            if (ItemListForm.ItemListKeywordTypeEnum.ITEM_NAME == command.getKeywordType()) {
                query.setItemName(command.getKeyword());
            } else if (ItemListForm.ItemListKeywordTypeEnum.UPC == command.getKeywordType()) {
                query.setUpc(command.getKeyword());
            } else if (ItemListForm.ItemListKeywordTypeEnum.ITEM_ID == command.getKeywordType()) {
                Long itemId = LangUtil.parseLong(command.getKeyword());
                if (itemId == null || itemId <= 0) {
                    return Result.buildIllegalArgumentResult("商品ID格式不正确");
                }
                query.setItemIds(CollectionUtil.asHashSet(itemId));
            } else {
                query.setKeyword(command.getKeyword());
            }
        }

        query.setPriceRange(new BigDecimalRange(command.getMinPrice(), command.getMaxPrice()));
        query.setRouteId(RequestContext.getRouteId());
        
        query.setApprovedStatusList(CollectionUtil.asArrayList(ItemApproveStatusEnum.WAIT_APPROVE));
        if (ContextEnvUtil.isDev()) {
            query.setSize(100);// TODO 测试环境es数据可能不一致，因此多查几条
        } else {
            query.setSize(1);
        }
        query.setIncludesFields(includesFields);
        query.setStatus(null);
        if (command.getItemId()>0) {
            Range<Long> IdRange = new Range<>();
            query.setIdRange(IdRange);
            if (command.getDirection() == BooleanStatusEnum.YES.getCode()) {
                //下一个
                IdRange.setRight(command.getItemId());
                query.setOrderBy(ItemSortTypeEnum.ID_DESC.getOrderBys());
            }else {
                IdRange.setLeft(command.getItemId());
                query.setOrderBy(ItemSortTypeEnum.ID_ASC.getOrderBys());
            }
        } else {
            query.setOrderBy(ItemSortTypeEnum.ID_DESC.getOrderBys());
        }
        SearchResult<ItemSearchBO> searchResult = itemSearchService.query(query);
        if (CollectionUtil.isEmpty(searchResult.getData())) {
            return Result.buildNotExistedResult("无更多待审核商品了！");
        }
        ItemDetailBO itemDetailBO = null;
        Map<String, ItemSkuSupplierApproveDO> approveDOMap = null;
        for (ItemSearchBO itemSearchBO : searchResult.getData()) {
            ItemSkuSupplierApproveQuery approveQuery = new ItemSkuSupplierApproveQuery();
            approveQuery.setItemIds(CollectionUtil.asArrayList(itemSearchBO.getId()));
            approveQuery.setStatusList(CollectionUtil.asArrayList(ItemApproveStatusEnum.WAIT_APPROVE.getCode()));
            List<ItemSkuSupplierApproveDO> approveDOList = itemSkuSupplierApproveService.query(approveQuery);
            if (CollectionUtil.isEmpty(approveDOList)) {
                EventBusFactory.getSyncEventBus().post(new ItemUpdatedEvent(itemSearchBO.getId()));
                continue;
            }
            approveDOMap = CollectionUtil.toMap(approveDOList, "skuId+channelType+supplierId");
            itemDetailBO = ItemHelper.buildItemDetail(itemSearchBO.getId());
            if (itemDetailBO != null) {
                break;
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