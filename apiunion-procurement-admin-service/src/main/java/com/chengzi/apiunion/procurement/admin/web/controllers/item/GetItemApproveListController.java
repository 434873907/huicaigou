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

import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.brand.pojo.BrandDO;
import com.chengzi.apiunion.brand.service.BrandService;
import com.chengzi.apiunion.common.data.search.elastic.pojo.SearchResult;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.enums.ItemApproveStatusEnum;
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
import com.chengzi.apiunion.partnercategory.service.PartnerCategoryService;
import com.chengzi.apiunion.procurement.admin.web.formbean.item.GetItemApproveListForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.item.GetItemApproveListBO;
import com.chengzi.apiunion.shop.pojo.ShopDO;
import com.chengzi.apiunion.shop.service.ShopService;
import com.chengzi.apiunion.supplier.service.SupplierService;
import com.chengzi.common.data.beans.PageDataList;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;

/**
 * 查询商品审核列表
 * 
 * @author Kolor
 */
public class GetItemApproveListController extends AbstractApiController<GetItemApproveListForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, GetItemApproveListForm command) throws Exception {
        ItemApproveSearchService itemApproveSearchService = BeanFactory.getBean(ItemApproveSearchService.class);
        ItemApproveSearchQuery query = new ItemApproveSearchQuery();
        query.setItemIds(CollectionUtil.asHashSet(command.getId()));
        query.setApproveStatusList(CollectionUtil.asArrayList(ItemApproveStatusEnum.WAIT_APPROVE));
        query.setFrom(command.getOffset());
        query.setSize(command.getLimit());

        SearchResult<ItemApproveSearchBO> searchResult = itemApproveSearchService.query(query);
        if (searchResult.isEmpty()) {
            return Result.buildSuccessResult(new PageDataList<>(0, command.getPage(), command.getLimit(), new ArrayList<>()));
        }

        return Result
                .buildSuccessResult(
                        new PageDataList<>(searchResult.getTotalHits(), command.getPage(), command.getLimit(), convert(searchResult.getData())));
    }

    private List<GetItemApproveListBO> convert(List<ItemApproveSearchBO> data) {
        ItemService itemService = BeanFactory.getBean(ItemService.class);
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

        List<GetItemApproveListBO> boList = new ArrayList<>();
        for (ItemApproveSearchBO itemApproveSearchBO : data) {
            GetItemApproveListBO bo = new GetItemApproveListBO();
            boList.add(bo);
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
            bo.setMainImageUrl(itemMainImageMap.get(itemRichDO.getId()));
            bo.setModifyTime(itemApproveSearchBO.getApproveModifyTime());
            bo.setShopId(itemRichDO.getShopId());
            ShopDO shopDO = shopMap.get(itemRichDO.getShopId());
            if (shopDO != null) {
                bo.setShopName(shopDO.getName());
            }
            bo.setSkuId(itemApproveSearchBO.getApproveSkuId());
            ItemSkuInfo skuInfo = skuMap.get(itemApproveSearchBO.getApproveSkuId());
            if (skuInfo != null) {
                bo.setSku(skuInfo.getDisplaySkuValue());
                for (SkuChannelInfo skuChannelInfo : skuInfo.getSkuChannels()) {
                    // 匹配渠道
                    if (skuChannelInfo.getChannelType() == itemApproveSearchBO.getApproveChannelType()) {
                        bo.setPrice(skuChannelInfo.getChannelPrice());
                        for (SupplierStock supplierStock : skuChannelInfo.getSupplierStocks()) {
                            // 匹配供应商
                            if (supplierStock.getSupplierId() == itemApproveSearchBO.getApproveSupplierId()) {
                                bo.setChannelPrice(supplierStock.getChannelPrice());
                                bo.setOldChannelPrice(supplierStock.getApprovedChannelPrice());
                                bo.setStock(supplierStock.getStock());
                                bo.setProfitRate(String.format("%f%%", skuChannelInfo.getEnterprisePrice().subtract(supplierStock.getChannelPrice())
                                        .divide(supplierStock.getChannelPrice(), 4, RoundingMode.HALF_UP).multiply(new BigDecimal(100))));
                                break;
                            }
                        }
                        break;
                    }
                }
            }
            bo.setSupplierId(itemApproveSearchBO.getApproveSupplierId());
            bo.setSupplierName(supplierService.getSupplierName(itemApproveSearchBO.getApproveSupplierId()));

        }
        return boList;
    }
}
