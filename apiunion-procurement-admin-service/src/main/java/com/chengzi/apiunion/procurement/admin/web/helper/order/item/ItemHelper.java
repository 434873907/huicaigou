package com.chengzi.apiunion.procurement.admin.web.helper.order.item;

import com.chengzi.apiunion.brand.pojo.BrandDO;
import com.chengzi.apiunion.brand.service.BrandService;
import com.chengzi.apiunion.common.data.beans.SimpleRouteOperate;
import com.chengzi.apiunion.item.dao.ItemSkuNameMapper;
import com.chengzi.apiunion.item.enums.*;
import com.chengzi.apiunion.item.pojo.*;
import com.chengzi.apiunion.item.pojo.ItemSkuInfo.SkuChannelInfo;
import com.chengzi.apiunion.item.pojo.ItemSkuInfo.SupplierStock;
import com.chengzi.apiunion.item.service.ItemImageService;
import com.chengzi.apiunion.item.service.ItemService;
import com.chengzi.apiunion.item.service.ItemSkuService;
import com.chengzi.apiunion.item.util.ItemUtil;
import com.chengzi.apiunion.item.util.SkuUtil;
import com.chengzi.apiunion.partnercategory.service.PartnerCategoryService;
import com.chengzi.apiunion.procurement.admin.web.pojo.item.ItemDetailBO;
import com.chengzi.apiunion.procurement.admin.web.pojo.item.SupplierStockBO;
import com.chengzi.apiunion.shop.pojo.ShopDO;
import com.chengzi.apiunion.shop.service.ShopService;
import com.chengzi.common.util.CollectionUtil;
import com.chengzi.common.util.EnumUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.summercool.web.servlet.BeanFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * @author 行者
 */
public class ItemHelper {

    public static ItemDetailBO buildItemDetail(long itemId,boolean withSupplierChanges) {
        ItemService itemService = BeanFactory.getBean(ItemService.class);
        ItemRichDO itemRichDO = itemService.getItemRich(itemId);
        if (itemRichDO == null || itemRichDO.isDeleted()) {
            return null;
        }

        BrandService brandService = BeanFactory.getBean(BrandService.class);
        ShopService shopService = BeanFactory.getBean(ShopService.class);
        PartnerCategoryService categoryService = BeanFactory.getBean(PartnerCategoryService.class);
        ItemImageService itemImageService = BeanFactory.getBean(ItemImageService.class);

        ItemDetailBO itemDetailBO = new ItemDetailBO();
        itemDetailBO.setId(itemRichDO.getId());
        itemDetailBO.setCreateTime(itemRichDO.getCreateTime());
        itemDetailBO.setModifyTime(itemRichDO.getModifyTime());
        itemDetailBO.setDeleted(itemRichDO.isDeleted());

        // 基本信息
        itemDetailBO.setCurrency(itemRichDO.getCurrency());
        itemDetailBO.setDesc(itemRichDO.getDesc() != null ? itemRichDO.getDesc() : "");
        itemDetailBO.setName(itemRichDO.getName());
        itemDetailBO.setOriginalName(itemRichDO.getOriginalName());
        itemDetailBO.setRichDesc(itemRichDO.getRichDesc());
        itemDetailBO.setInfo(itemRichDO.getInfo());
        itemDetailBO.setGift(itemRichDO.isGift());
        itemDetailBO.setPresell(itemRichDO.isPresell());
        itemDetailBO.setStatus(itemRichDO.getStatus());
        itemDetailBO.setUnit(StringUtils.isEmpty(itemRichDO.getUnit()) ? SkuUtil.DEFAULT_UNIT  : itemRichDO.getUnit());
        itemDetailBO.setUnitCanChange(itemRichDO.getUnitCanChange());
        itemDetailBO.setModelNo(itemRichDO.getModelNo());
        if (StringUtils.isNotBlank(itemRichDO.getNameLabels())) {
            itemDetailBO.setNameLabels(CollectionUtil.splitAsLong(itemRichDO.getNameLabels(), ","));
        }
        // 待审核页，如果是待审核状态，则改为上架状态
        if (itemRichDO.getStatus() == ItemStatusEnum.INIT.getCode()) {
            itemDetailBO.setStatus(ItemStatusEnum.ONLINE.getCode());
        }

        // 图片
        if (EnumUtil.isMatchByBit(itemRichDO.getRefStatus(), ItemRefStatusEnum.IMAGE)) {
            List<ItemImageDO> itemImageDOList = itemImageService.getItemImages(itemRichDO.getId());
            List<ItemImageInfo> imageList = new ArrayList<>();
            //是否需要重新排序
            boolean needSort = false;
            for (ItemImageDO itemImageDO : itemImageDOList) {
                if (!needSort && itemImageDO.getType() != ItemImageTypeEnum.MAIN.getCode() && itemImageDO.getSort() == 0) {
                    needSort = true;
                }
                String imageUrl = StringUtils.isNotBlank(itemImageDO.getImageUrl()) ? itemImageDO.getImageUrl() : itemImageDO.getOriImageUrl();
                imageList.add(new ItemImageInfo(itemImageDO.getId(),imageUrl, itemImageDO.getType(),itemImageDO.getSort()));
            }
            if (needSort) {
                ItemUtil.reSort(imageList);
            }
            itemDetailBO.setImageList(imageList);

            //图片排序
            imageList.sort((image1,image2) -> {
                if (image1.getType() == ItemImageTypeEnum.MAIN.getCode()) {
                    return -1;
                }
                if (image2.getType() == ItemImageTypeEnum.MAIN.getCode()) {
                    return 1;
                }
                return image1.getSort() - image2.getSort();
            });
        }

        // 类目
        itemDetailBO.setCateId1(itemRichDO.getCateId1());
        itemDetailBO.setCateName1(categoryService.getCategoryNameInCache(itemRichDO.getCateId1()));
        itemDetailBO.setCateId2(itemRichDO.getCateId2());
        itemDetailBO.setCateName2(categoryService.getCategoryNameInCache(itemRichDO.getCateId2()));
        itemDetailBO.setCateId3(itemRichDO.getCateId3());
        itemDetailBO.setCateName3(categoryService.getCategoryNameInCache(itemRichDO.getCateId3()));

        // 品牌
        itemDetailBO.setBrandId(itemRichDO.getBrandId());
        BrandDO brandDO = brandService.getById(itemRichDO.getBrandId());
        if (brandDO != null) {
            itemDetailBO.setBrandName(brandDO.getBrandNameOrOriginalName());
        }

        // 店铺
        itemDetailBO.setShopId(itemRichDO.getShopId());
        if (itemRichDO.getShopId() > 0) {
            ShopDO shopDO = shopService.getByIdWithCache(itemRichDO.getShopId());
            if (shopDO != null) {
                itemDetailBO.setShopName(shopDO.getName());
            }
        }

        // 规格名称及可选值
        List<ItemSkuNameValues> skuNameValues = new ArrayList<>();
        ItemSkuNameMapper itemSkuNameMapper = BeanFactory.getBean(ItemSkuNameMapper.class);
        List<ItemSkuNameDO> itemSkuNameDOList = itemSkuNameMapper.selectByItemId(SimpleRouteOperate.of(itemRichDO.getId()));
        for (ItemSkuNameDO itemSkuNameDO : itemSkuNameDOList) {
            ItemSkuNameValues skuNV = new ItemSkuNameValues();
            skuNV.setSkuName(SkuUtil.fixDisplaySkuValue(itemSkuNameDO.getSkuName()));
            skuNV.setSkuValues(itemSkuNameDO.parseSkuValue());
            skuNameValues.add(skuNV);
        }
        itemDetailBO.setSkuNameValues(skuNameValues);
        Map<String, ItemSkuNameValues> skuNameMap = CollectionUtil.toMap(skuNameValues, "skuName", CollectionUtil.KeyFeature.UPPER_CASE);

        // 规格
        ItemSkuService itemSkuService = BeanFactory.getBean(ItemSkuService.class);
        List<ItemSkuInfo> skuInfoList;
        if (withSupplierChanges) {
            skuInfoList = itemSkuService.getSkuInfosByItemIdWithSupplierChanges(CollectionUtil.asArrayList(itemRichDO.getId()));
        } else {
            skuInfoList = itemSkuService.getSkuInfosByItemId(itemRichDO.getId());
        }
        itemDetailBO.setSkuList(skuInfoList);

        // 使用SupplierStockBO替代SupplierStock
        Iterator<ItemSkuInfo> skuInfoIter = skuInfoList.iterator();
        while (skuInfoIter.hasNext()) {
            ItemSkuInfo itemSkuInfo = skuInfoIter.next();

            if (itemSkuInfo.getSkuChannels() == null) {
                skuInfoIter.remove();
                continue;
            }
            Iterator<SkuChannelInfo> skuChannelInfoIter = itemSkuInfo.getSkuChannels().iterator();
            while (skuChannelInfoIter.hasNext()) {
                SkuChannelInfo skuChannelInfo = skuChannelInfoIter.next();

                if (skuChannelInfo.getEnterprisePrice() == null) {
                    skuChannelInfo.setEnterprisePrice(skuChannelInfo.getPrice());
                }

                int totalStock = 0;
                List<SupplierStock> supplierStockBOList = new ArrayList<>();
                for (SupplierStock supplierStock : skuChannelInfo.getSupplierStocks()) {
                    SupplierStockBO supplierStockBO = new SupplierStockBO();
                    supplierStockBOList.add(supplierStockBO);

                    BeanUtils.copyProperties(supplierStock, supplierStockBO);

                    supplierStockBO.setProfitRate(String.format("%.2f%%", skuChannelInfo.getEnterprisePrice().subtract(supplierStock.getChannelPrice())
                            .divide(supplierStock.getChannelPrice(), 4, RoundingMode.HALF_UP).multiply(new BigDecimal(100))));
                    totalStock += supplierStock.getStock();
                }
                if (!supplierStockBOList.isEmpty()) {
                    skuChannelInfo.setSupplierStocks(supplierStockBOList);
                } else {
                    //                    // 忽略全部供应商都被过滤的渠道
                    //                    skuChannelInfoIter.remove();
                }
                skuChannelInfo.setTotalStock(totalStock);
                skuChannelInfo.setItemCode(SkuUtil.encodeSkuNo(itemRichDO.getId(), itemSkuInfo.getId(), skuChannelInfo.getChannelType()));

            }
            if (itemSkuInfo.getSkuChannels().isEmpty()) {
                //                // 忽略全部渠道都被过滤的规格
                //                skuInfoIter.remove();
            }
        }

        // 规格图片
        for (ItemSkuInfo itemSkuInfo : skuInfoList) {
            if (CollectionUtil.isEmpty(itemSkuInfo.getSkuValues())) {
                continue;
            }
            for (ItemSkuInfo.ItemSkuValue itemSkuValue : itemSkuInfo.getSkuValues()) {
                if (StringUtils.isNotBlank(itemSkuValue.getImageUrl())) {
                    ItemSkuNameValues itemSkuNameValues = skuNameMap.get(StringUtils.upperCase(itemSkuValue.getSkuName()));
                    if (itemSkuNameValues != null) {
                        itemSkuNameValues.getSkuValueImages().add(new ItemSkuNameValues.ItemSkuValueImage(itemSkuValue.getSkuValue(),
                                itemSkuValue.getImageUrl()));
                    }
                }
            }
        }

        return itemDetailBO;
    }

    //带供应商变更记录
    public static ItemDetailBO buildItemDetailWithSupplierChanges(long itemId) {
         return buildItemDetail(itemId,true);
    }

    public static ItemDetailBO buildItemDetail(long itemId) {
        return buildItemDetail(itemId,false);
    }
}
