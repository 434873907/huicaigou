/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.controllers.item;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chengzi.apiunion.common.data.enums.CurrencyEnum;
import com.chengzi.apiunion.common.data.search.elastic.pojo.OrderBy;
import com.chengzi.apiunion.common.module.currency.util.AmountUtil;
import com.chengzi.apiunion.item.enums.ItemApproveTypeEnum;
import com.chengzi.apiunion.item.pojo.ItemSkuSupplierApproveDO;
import com.chengzi.apiunion.item.pojo.ItemSkuSupplierApproveQuery;
import com.chengzi.apiunion.item.pojo.search.enums.ItemSortTypeEnum;
import com.chengzi.apiunion.item.service.ItemSkuSupplierApproveService;
import com.chengzi.apiunion.item.util.SkuUtil;
import com.chengzi.apiunion.procurement.admin.web.formbean.item.ItemListForm;
import com.chengzi.common.util.EnumUtil;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.summercool.util.LangUtil;
import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.brand.pojo.BrandDO;
import com.chengzi.apiunion.brand.service.BrandService;
import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.data.search.elastic.pojo.SearchResult;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.enums.ItemApproveStatusEnum;
import com.chengzi.apiunion.item.enums.ItemStatusEnum;
import com.chengzi.apiunion.item.pojo.ItemRichDO;
import com.chengzi.apiunion.item.pojo.ItemSkuInfo;
import com.chengzi.apiunion.item.pojo.ItemSkuInfo.SkuChannelInfo;
import com.chengzi.apiunion.item.pojo.ItemSkuInfo.SupplierStock;
import com.chengzi.apiunion.item.pojo.search.ItemApproveSearchBO;
import com.chengzi.apiunion.item.pojo.search.query.ItemApproveSearchQuery;
import com.chengzi.apiunion.item.search.ItemApproveSearchService;
import com.chengzi.apiunion.item.service.ItemChannelTypeService;
import com.chengzi.apiunion.item.service.ItemImageService;
import com.chengzi.apiunion.item.service.ItemService;
import com.chengzi.apiunion.item.service.ItemSkuService;
import com.chengzi.apiunion.partnercategory.constant.PartnerCategoryConstant;
import com.chengzi.apiunion.partnercategory.pojo.CategoryUnit;
import com.chengzi.apiunion.partnercategory.pojo.PartnerCategoryDO;
import com.chengzi.apiunion.partnercategory.service.PartnerCategoryService;
import com.chengzi.apiunion.procurement.admin.web.formbean.item.ItemListForm.ItemListKeywordTypeEnum;
import com.chengzi.apiunion.procurement.admin.web.formbean.item.UnapprovedItemSkuListForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.item.UnapprovedItemSkuListBO;
import com.chengzi.apiunion.shop.pojo.ShopDO;
import com.chengzi.apiunion.shop.service.ShopService;
import com.chengzi.apiunion.supplier.service.SupplierService;
import com.chengzi.common.data.beans.PageDataList;
import com.chengzi.common.data.support.BigDecimalRange;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;

/**
 * 待审核商品规格列表
 * 
 * @author Kolor
 */
public class UnapprovedItemSkuListController extends AbstractApiController<UnapprovedItemSkuListForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, UnapprovedItemSkuListForm command) throws Exception {
        ItemApproveSearchService itemApproveSearchService = BeanFactory.getBean(ItemApproveSearchService.class);
        PartnerCategoryService categoryService = BeanFactory.getBean(PartnerCategoryService.class);
        ItemApproveSearchQuery searchQuery = new ItemApproveSearchQuery();
        searchQuery.setFrom(command.getOffset());
        searchQuery.setSize(command.getLimit());
        searchQuery.setStatusList(CollectionUtil.asArrayList(ItemStatusEnum.INIT, ItemStatusEnum.ONLINE, ItemStatusEnum.OFFLINE));
        searchQuery.setBrandIds(command.getBrandIds());
        searchQuery.setShopIds(command.getShopIds());
        searchQuery.setSupplierIds(command.getSupplierIds());
        searchQuery.setIsPresell(command.getPresell());
        searchQuery.setChannelTypes(command.getItemChannelTypes());
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
            if (ItemListKeywordTypeEnum.ITEM_NAME == command.getKeywordType()) {
                searchQuery.setItemName(command.getKeyword());
            } else if (ItemListKeywordTypeEnum.UPC == command.getKeywordType()) {
                searchQuery.setUpc(command.getKeyword());
            } else if (ItemListForm.ItemListKeywordTypeEnum.ITEM_ID == command.getKeywordType()) {
                Long itemId = LangUtil.parseLong(command.getKeyword());
                if (itemId == null || itemId.longValue() <= 0) {
                    return Result.buildIllegalArgumentResult("商品ID格式不正确");
                }
                searchQuery.setItemIds(CollectionUtil.asHashSet(itemId));
            } else {
                searchQuery.setKeyword(command.getKeyword());
            }
        }

        searchQuery.setPriceRange(new BigDecimalRange(command.getMinPrice(), command.getMaxPrice()));
        searchQuery.setRouteId(RequestContext.getRouteId());
        searchQuery.setIsGift(command.getIsGift());
        searchQuery.setApproveStatusList(CollectionUtil.asArrayList(ItemApproveStatusEnum.WAIT_APPROVE));

        if (command.getApproveType() > 0) {
            searchQuery.setApproveTypeList(CollectionUtil.asArrayList(EnumUtil.parse(ItemApproveTypeEnum.class,command.getApproveType())));
        }

        searchQuery.setOrderBy(ItemSortTypeEnum.APPROVE_LIST_DESC.getOrderBys());

        SearchResult<ItemApproveSearchBO> searchResult = itemApproveSearchService.query(searchQuery);
        if (searchResult.isEmpty()) {
            return Result.buildSuccessResult(new PageDataList<>(0, command.getPage(), command.getLimit(), new ArrayList<>()));
        }

        return Result
                .buildSuccessResult(
                        new PageDataList<>(searchResult.getTotalHits(), command.getPage(), command.getLimit(), convert(searchResult.getData())));
    }

    private List<UnapprovedItemSkuListBO> convert(List<ItemApproveSearchBO> data) {
        ItemService itemService = BeanFactory.getBean(ItemService.class);
        ItemSkuSupplierApproveService itemSkuSupplierApproveService = BeanFactory.getBean(ItemSkuSupplierApproveService.class);
        ItemApproveSearchService itemApproveSearchService = BeanFactory.getBean(ItemApproveSearchService.class);
        ItemImageService itemImageService = BeanFactory.getBean(ItemImageService.class);
        BrandService brandService = BeanFactory.getBean(BrandService.class);
        ShopService shopService = BeanFactory.getBean(ShopService.class);
        PartnerCategoryService categoryService = BeanFactory.getBean(PartnerCategoryService.class);
        ItemChannelTypeService channelService = BeanFactory.getBean(ItemChannelTypeService.class);
        ItemSkuService itemSkuService = BeanFactory.getBean(ItemSkuService.class);
        SupplierService supplierService = BeanFactory.getBean(SupplierService.class);

        Map<Long, List<Long>> itemSkuIdsMap = CollectionUtil.toListMap(data, "approveItemId", null, "approveSkuId", null);
        Map<Long, ItemSkuInfo> skuMap = new HashMap<>();
        for (Entry<Long, List<Long>> entry : itemSkuIdsMap.entrySet()) {
            List<ItemSkuInfo> skuList = itemSkuService.getSkuInfosBySkuIds(entry.getKey(), entry.getValue());
            for (ItemSkuInfo itemSkuInfo : skuList) {
                skuMap.put(itemSkuInfo.getId(), itemSkuInfo);
            }
        }

        List<Long> itemIds = CollectionUtil.getFieldValueList(data, "approveItemId");
        List<ItemRichDO> itemList = itemService.getItemRichByIds(itemIds);
        Map<Long, ItemRichDO> itemMap = CollectionUtil.toMap(itemList, "id");

        Map<Long, String> itemMainImageMap = itemImageService.getItemMainImageByItemIds(itemIds);

        List<Long> brandIds = CollectionUtil.getFieldValueList(itemList, "brandId");
        List<BrandDO> brandList = brandService.getByIds(brandIds);
        Map<Long, BrandDO> brandMap = CollectionUtil.toMap(brandList, "id");

        List<Long> shopIds = CollectionUtil.getFieldValueList(itemList, "shopId");
        List<ShopDO> shopList = shopService.getByIds(shopIds);
        Map<Long, ShopDO> shopMap = CollectionUtil.toMap(shopList, "id");

        List<UnapprovedItemSkuListBO> boList = new ArrayList<>();
        for (ItemApproveSearchBO itemApproveSearchBO : data) {
            UnapprovedItemSkuListBO bo = new UnapprovedItemSkuListBO();
            ItemRichDO itemRichDO = itemMap.get(itemApproveSearchBO.getApproveItemId());
            if (itemRichDO == null) {
                continue;
            }

            bo.setBrandId(itemRichDO.getBrandId());
            BrandDO brandDO = brandMap.get(itemRichDO.getBrandId());
            if (brandDO != null) {
                bo.setBrandName(brandDO.getBrandNameOrOriginalName());
            }
            bo.setType(itemApproveSearchBO.getApproveType());
            bo.setCateId1(itemRichDO.getCateId1());
            bo.setCateName1(categoryService.getCategoryNameInCache(itemRichDO.getCateId1()));
            bo.setCateId2(itemRichDO.getCateId2());
            bo.setCateName2(categoryService.getCategoryNameInCache(itemRichDO.getCateId2()));
            bo.setCateId3(itemRichDO.getCateId3());
            bo.setCateName3(categoryService.getCategoryNameInCache(itemRichDO.getCateId3()));
            bo.setChannelName(channelService.getChannelNameInCache(itemApproveSearchBO.getApproveChannelType()));
            bo.setChannelType(itemApproveSearchBO.getApproveChannelType());
            bo.setCreateTime(itemApproveSearchBO.getApproveCreateTime());
            bo.setId(itemApproveSearchBO.getApproveId());
            bo.setInfo(itemApproveSearchBO.getApproveInfo());
            bo.setItemId(itemApproveSearchBO.getApproveItemId());
            bo.setItemName(itemRichDO.getName());
            //主图
            String mainImage = itemMainImageMap.get(itemRichDO.getId());
            bo.setMainImageUrl(mainImage);
            bo.setModifyTime(itemApproveSearchBO.getApproveModifyTime());
            bo.setShopId(itemRichDO.getShopId());
            ShopDO shopDO = shopMap.get(itemRichDO.getShopId());
            if (shopDO != null) {
                bo.setShopName(shopDO.getName());
            }
            bo.setSkuId(itemApproveSearchBO.getApproveSkuId());
            ItemSkuInfo skuInfo = skuMap.get(itemApproveSearchBO.getApproveSkuId());
            // 只展示匹配上规格和渠道的
            if (skuInfo != null) {
                bo.setSku(SkuUtil.buildSkuDescIncludeGroup(skuInfo,itemRichDO.getUnit()));
                boolean matched = false;

                if (CollectionUtil.isEmpty(skuInfo.getSkuChannels())) {
                    continue;
                }

                for (SkuChannelInfo skuChannelInfo : skuInfo.getSkuChannels()) {
                    // 匹配渠道
                    if (skuChannelInfo.getChannelType() == itemApproveSearchBO.getApproveChannelType()) {
                        int currency = itemRichDO.getCurrency();
                        bo.setPrice(AmountUtil.getAdminDisplayPriceStartWithSymbol(currency,skuChannelInfo.getChannelPrice()));
                        for (SupplierStock supplierStock : skuChannelInfo.getSupplierStocks()) {
                            // 匹配供应商
                            if (supplierStock.getSupplierId() == itemApproveSearchBO.getApproveSupplierId()) {
                                bo.setChannelPrice(supplierStock.getChannelPrice());
                                bo.setOldChannelPrice(supplierStock.getApprovedChannelPrice());
                                bo.setStock(supplierStock.getStock());
                                bo.setProfitRate(String.format("%.2f%%", skuChannelInfo.getEnterprisePrice().subtract(supplierStock.getChannelPrice())
                                        .divide(supplierStock.getChannelPrice(), 4, RoundingMode.HALF_UP).multiply(new BigDecimal(100))));
                                matched = true;
                                break;
                            }
                        }
                        break;
                    }
                }
                if (matched) {
                    bo.setSupplierId(itemApproveSearchBO.getApproveSupplierId());
                    bo.setSupplierName(supplierService.getSupplierName(itemApproveSearchBO.getApproveSupplierId()));
                    
                    boList.add(bo);
                }
            }
        }
        return boList;
    }

}
