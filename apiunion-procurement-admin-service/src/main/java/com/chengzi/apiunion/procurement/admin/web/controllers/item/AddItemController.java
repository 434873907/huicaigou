/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.controllers.item;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chengzi.apiunion.common.module.image.enums.ImageSizeLimitEnum;
import com.chengzi.apiunion.common.module.image.util.ImageSizeVerifyUtil;
import com.chengzi.apiunion.item.enums.ItemImageTypeEnum;
import com.chengzi.apiunion.item.pojo.ItemImageInfo;
import com.chengzi.common.data.enums.BooleanStatusEnum;
import com.chengzi.common.util.CollectionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindException;
import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.pojo.ItemRichDO;
import com.chengzi.apiunion.item.pojo.ItemSkuInfo;
import com.chengzi.apiunion.item.pojo.ItemSkuNameValues;
import com.chengzi.apiunion.item.service.ItemService;
import com.chengzi.apiunion.item.util.SkuUtil;
import com.chengzi.apiunion.partnercategory.constant.PartnerCategoryConstant;
import com.chengzi.apiunion.partnercategory.pojo.PartnerCategoryDO;
import com.chengzi.apiunion.partnercategory.service.PartnerCategoryService;
import com.chengzi.apiunion.procurement.admin.web.formbean.item.AddItemForm;
import com.chengzi.common.data.validate.Result;

/**
 * 添加商品
 * 
 * @author Kolor
 *
 */
public class AddItemController extends AbstractApiController<AddItemForm> {

    private static final int MAX_IMG_NUM = 8;

    @Override
    protected void onBindAndValidate(HttpServletRequest request, AddItemForm command, BindException errors) throws Exception {
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
        Set<String> upcs = new HashSet<>();
        for (ItemSkuInfo skuInfo : command.getSkuList()) {
            if (!skuInfo.isMultiplePieces()) {
                if (StringUtils.isNotBlank(skuInfo.getUpc()) && !upcs.add(StringUtils.upperCase(skuInfo.getUpc()))) {
                    addError(command, errors, "skuList[].upc", command.getSkuList(),
                            String.format("规格%s UPC:%s重复", skuInfo.getSkuValues(), skuInfo.getUpc()));
                    return;
                }
            }
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
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, AddItemForm command) {
        if (command.getCategoryId() <= 0) {
            return Result.buildIllegalArgumentResult("请选择类目");
        }
        // 校验类目
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

        ItemService partnerItemService = BeanFactory.getBean(ItemService.class);

        ItemRichDO itemRichDO = new ItemRichDO();
        itemRichDO.setBrandId(command.getBrandId());
        itemRichDO.setCateId1(firstCateDO.getId());
        itemRichDO.setCateId2(secondCateDO.getId());
        itemRichDO.setCateId3(thirdCateDO.getId());
        itemRichDO.setCreateBy(RequestContext.getUserId());
        itemRichDO.setDesc(command.getDesc());
        itemRichDO.setInfo(command.getInfo());
        //        itemRichDO.setKeyword(keyword);
        itemRichDO.setName(command.getName());
        itemRichDO.setOriginalName(command.getName());
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
        itemRichDO.setModelNo(command.getModelNo());
        itemRichDO.setNameLabels(command.getNameLabels());
        //手动添加商品 可以修改单位
        itemRichDO.setUnitCanChange(BooleanStatusEnum.YES.getCode());
        return partnerItemService.addItem(itemRichDO, command.getSkuNameValues(), command.getSkuList(), command.getAddImages(),
                RequestContext.getUserId());
    }

}
