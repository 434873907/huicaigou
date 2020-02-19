/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.controllers.item;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chengzi.apiunion.common.data.beans.SimpleRouteOperate;
import com.chengzi.apiunion.item.enums.*;
import com.chengzi.apiunion.item.pojo.*;
import com.chengzi.common.util.EnumUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindException;
import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.pojo.ItemSkuInfo.SupplierStock;
import com.chengzi.apiunion.item.service.ItemService;
import com.chengzi.apiunion.item.util.SkuUtil;
import com.chengzi.apiunion.partnercategory.constant.PartnerCategoryConstant;
import com.chengzi.apiunion.partnercategory.pojo.PartnerCategoryDO;
import com.chengzi.apiunion.partnercategory.service.PartnerCategoryService;
import com.chengzi.apiunion.procurement.admin.web.formbean.item.UpdateItemForm;
import com.chengzi.apiunion.supplier.service.SupplierService;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;

/**
 * 更新商品
 * 
 * @author Kolor
 *
 */
public class UpdateItemController extends AbstractApiController<UpdateItemForm> {

    private static final int MAX_IMG_NUM = 30;

    @Override
    protected void onBindAndValidate(HttpServletRequest request, UpdateItemForm command, BindException errors) throws Exception {
        super.onBindAndValidate(request, command, errors);
        if (errors.hasErrors()) {
            return;
        }
        Set<String> skuNames = new HashSet<>();
        if (command.getSkuNameValues() != null) {
            for (ItemSkuNameValues skuNV : command.getSkuNameValues()) {
                if (!skuNames.add(StringUtils.upperCase(skuNV.getSkuName()))) {
                    addError(command, errors, "skuNameValues", command.getSkuNameValues(), String.format("规格名称%s重复", skuNV.getSkuName()));
                } else {
                    // 删除重复项
                    Set<String> values = new HashSet<>();
                    Iterator<String> iter = skuNV.getSkuValues().iterator();
                    while (iter.hasNext()) {
                        if (!values.add(StringUtils.upperCase(iter.next()))) {
                            iter.remove();
                        }
                    }
                }
            }
        }

        Set<String> skuKeys = new HashSet<>();
        for (ItemSkuInfo skuInfo : command.getSkuList()) {

            if (!skuKeys.add(SkuUtil.buildSkuKey(skuInfo))) {
                // 规格值重复
                addError(command, errors, "skuList[].skuValues", command.getSkuList(),
                        String.format("规格%s重复添加", skuInfo.getSkuValues() == null ? "" : skuInfo.getSkuValues()));
                return;
            }

            //价格校验
            for (ItemSkuInfo.SkuChannelInfo channelInfo : skuInfo.getSkuChannels()) {
                if (channelInfo.getPrice().compareTo(channelInfo.getOriginalPrice()) > 0) {
                    addError(command, errors, "skuList[].skuChannels[].price", command.getSkuList(),
                            "售价不能高于原价");
                    return;
                }
                if (channelInfo.getEnterprisePrice().compareTo(channelInfo.getPrice()) > 0) {
                    addError(command, errors, "skuList[].skuChannels[].enterprisePrice", command.getSkuList(),
                            "企业价不能高于售价");
                    return;
                }
                // 审核页面，必须审核所有的供应商
                if (CollectionUtil.isNotEmpty(channelInfo.getSupplierStocks())) {
                    for (SupplierStock supplierStock : channelInfo.getSupplierStocks()) {
                        if (supplierStock.getApprovedStatus() != null
                                && supplierStock.getApprovedStatus().intValue() == SupplierItemSkuStatusEnum.WAIT_APPROVE.getCode()
                                && UpdateItemForm.FROM_PAGE_APPROVE == command.getFromPage()) {
                            
                            SupplierService supplierService = BeanFactory.getBean(SupplierService.class);
                            addError(command, errors, "skuList[].skuChannels[].supplierStocks.approvedStatus", command.getSkuList(),
                                    String.format("规格%s的供应商%s未设置审核状态", skuInfo.getSkuValues(),
                                            supplierService.getSupplierName(supplierStock.getSupplierId())));
                            return;
                        }
                    }
                }
            }
        }

        if (CollectionUtil.isNotEmpty(command.getDelImageIds())) {
            Map<Long, ItemImageInfo> imageIdMap = new HashMap<>();
            for (ItemImageInfo itemImageInfo : command.getAddImages()) {
                if (itemImageInfo.getId() != null && itemImageInfo.getId() > 0) {
                    imageIdMap.put(itemImageInfo.getId(), itemImageInfo);
                }
            }
            for (long imageId : command.getDelImageIds()) {
                ItemImageInfo itemImageInfo = imageIdMap.get(imageId);
                if (itemImageInfo != null) {
                    command.getAddImages().remove(itemImageInfo);
                }
            }
        }

        //        for (ItemImageInfo itemImageInfo : command.getAddImages()) {
        //            // 图片尺寸校验
        //            if (!ImageSizeVerifyUtil.verifyImageSize(itemImageInfo.getUrl(), ImageSizeLimitEnum.ITEM, null).isSuccess()) {
        //                addError(command, errors, "addImages", command.getSkuList(),
        //                        "请添加合适尺寸的图片");
        //            }
        //        }
        //
        //        for (ItemSkuInfo itemSkuInfo : command.getSkuList()) {
        //            // 图片尺寸校验
        //            if (!ImageSizeVerifyUtil.verifyImageSize(itemSkuInfo.getGroupImageUrl(), ImageSizeLimitEnum.ITEM, null).isSuccess()) {
        //                addError(command, errors, "addImages", command.getSkuList(),
        //                        "请添加合适尺寸的图片");
        //            }
        //        }

        Map<Integer, List<ItemImageInfo>> imageMap = CollectionUtil.toListMap(command.getAddImages(), "type");
        if (CollectionUtil.isEmpty(imageMap.get(ItemImageTypeEnum.MAIN.getCode()))) {
            addError(command, errors, "addImages", command.getSkuList(),
                    "请添加主图");
        }

        if (command.getAddImages().size() > MAX_IMG_NUM) {
            addError(command, errors, "addImages", command.getSkuList(),
                    "请添加不多于8张图片");
        }
    }

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, UpdateItemForm command) {
        //修改商品也走审核流程
        command.setFromPage(UpdateItemForm.FROM_PAGE_APPROVE);
        ItemService itemService = BeanFactory.getBean(ItemService.class);
        ItemRichDO itemRichDO = itemService.getItemRich(command.getId());
        if (itemRichDO == null) {
            return Result.buildNotExistedResult("商品不存在");
        }

        // 校验类目
        if (needVerifyCategory(command.getSkuList())) {
            if (command.getCategoryId() <= 0) {
                return Result.buildIllegalArgumentResult("请选择类目");
            }
            PartnerCategoryService categoryService = BeanFactory.getBean(PartnerCategoryService.class);
            PartnerCategoryDO thirdCateDO = categoryService.getById(command.getCategoryId());
            if (thirdCateDO == null) {
                return Result.buildIllegalArgumentResult("类目不存在");
            } else if (PartnerCategoryConstant.THIRD_CLASS_CATEGORY != thirdCateDO.getDepth()) {
                return Result.buildIllegalArgumentResult("请为商品选择一个三级类目");
            }
            // 二级类目
            PartnerCategoryDO secondCateDO = categoryService.getById(thirdCateDO.getParentId());
            if (secondCateDO == null || PartnerCategoryConstant.SECOND_CLASS_CATEGORY != secondCateDO.getDepth()) {
                return Result.buildIllegalArgumentResult(String.format("选择的三级类目%s未设置二级类目", thirdCateDO.getName()));
            }
            // 一级类目
            PartnerCategoryDO firstCateDO = categoryService.getById(secondCateDO.getParentId());
            if (firstCateDO == null || PartnerCategoryConstant.FIRST_CLASS_CATEGORY != firstCateDO.getDepth()) {
                return Result.buildIllegalArgumentResult(String.format("选择的二级类目%s未设置一级类目", secondCateDO.getName()));
            }
            itemRichDO.setCateId1(firstCateDO.getId());
            itemRichDO.setCateId2(secondCateDO.getId());
            itemRichDO.setCateId3(thirdCateDO.getId());
        }

        if (itemRichDO.getBrandId() != command.getBrandId()) {
            itemRichDO.setModifyStatus(EnumUtil.or(itemRichDO.getRefStatus(), ItemModifyStatusEnum.BRAND));
            itemRichDO.setBrandId(command.getBrandId());
        }

        List<ItemImageDO> itemImageDOS = itemService.getImagesByItemId(command.getId());
        if (CollectionUtil.isNotEmpty(itemImageDOS) && CollectionUtil.isNotEmpty(command.getAddImages())) {
            Set<String> oldImageUrls = new HashSet<>();
            Set<String> newImageUrls = new HashSet<>();
            itemImageDOS.forEach(itemImageDO -> oldImageUrls.add(delUrlSuffix(StringUtils.isNotBlank(itemImageDO.getImageUrl()) ? itemImageDO.getImageUrl() : itemImageDO.getOriImageUrl())));
            command.getAddImages().forEach(image -> newImageUrls.add(delUrlSuffix(image.getUrl())));
            boolean imageChanged = false;
            for (String url : oldImageUrls) {
                if (!newImageUrls.remove(url)) {
                    imageChanged = true;
                }
            }
            if (!newImageUrls.isEmpty()) {
                imageChanged = true;
            }

            if (imageChanged) {
                //图片有改动过
                itemRichDO.setModifyStatus(EnumUtil.or(itemRichDO.getRefStatus(), ItemModifyStatusEnum.IMAGE));
            }
        }

        itemRichDO.setCreateBy(RequestContext.getUserId());
        itemRichDO.setDesc(command.getDesc());
        itemRichDO.setInfo(command.getInfo());
        //        itemRichDO.setKeyword(keyword);
        if (!itemRichDO.getName().equals(command.getName())) {
            //修改了商品名
            itemRichDO.setName(command.getName());
            itemRichDO.setModifyStatus(EnumUtil.or(itemRichDO.getRefStatus(), ItemModifyStatusEnum.NAME));
        }
        // itemRichDO.setOriginalName(command.getOriginalName());
        //        itemRichDO.setPropStatus(propStatus);
        itemRichDO.setRichDesc(command.getRichDesc());
        itemRichDO.setRouteId(RequestContext.getRouteId());
        itemRichDO.setShopId(command.getShopId());
        itemRichDO.setStatus(command.getStatus().getCode());
        //        itemRichDO.setVirtual(isVirtual);
        itemRichDO.setCurrency(command.getCurrency().getCode());
        itemRichDO.setDesc(command.getDesc());
        itemRichDO.setName(command.getName());
        itemRichDO.setOriginalName(command.getOriginalName());
        itemRichDO.setRichDesc(command.getRichDesc());
        itemRichDO.setStatus(command.getStatus().getCode());
        itemRichDO.setGift(command.isGift());
        itemRichDO.setPresell(command.isPresell());
        itemRichDO.setUnit(command.getUnit());
        itemRichDO.setUnitCanChange(command.getUnitCanChange());
        itemRichDO.setModelNo(command.getModelNo());
        itemRichDO.setNameLabels(command.getNameLabels());
        if (UpdateItemForm.FROM_PAGE_APPROVE == command.getFromPage() && command.getStatus() == ItemStatusEnum.INIT) {
            itemRichDO.setStatus(ItemStatusEnum.ONLINE.getCode());
        }

        if (command.getStatus() == ItemStatusEnum.OFFLINE || itemRichDO.getOfflineType() != ItemOfflineTypeEnum.AUTO_OFFLINE.getCode()) {
            itemRichDO.setOfflineType(ItemOfflineTypeEnum.NORMAL.getCode());
        }

        return itemService.updateItem(itemRichDO, command.getSkuNameValues(), command.getSkuList(), command.getAddImages(),
                command.getDelImageIds(), RequestContext.getUserId(), UpdateItemForm.FROM_PAGE_APPROVE == command.getFromPage());
    }

    private boolean needVerifyCategory(List<ItemSkuInfo> skuInfoList) {
        for (ItemSkuInfo skuInfo : skuInfoList) {
            for (ItemSkuInfo.SkuChannelInfo skuChannelInfo : skuInfo.getSkuChannels()) {
                for (ItemSkuInfo.SupplierStock supplierStock : skuChannelInfo.getSupplierStocks()) {
                    if (supplierStock.getApprovedStatus() == 1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private String delUrlSuffix(String url) {
        int i = url.indexOf("?");
        if (i > -1) {
            url = url.substring(0,i);
        }
        return url;
    }

}
