package com.chengzi.apiunion.procurement.admin.web.helper.template;

import com.chengzi.apiunion.item.pojo.ItemSkuNameValues;
import com.chengzi.apiunion.item.util.SkuUtil;
import com.chengzi.apiunion.procurement.admin.web.pojo.template.TemplateItemDetailBO;
import com.chengzi.apiunion.supplier.common.data.beans.SupplierItemImageInfo;
import com.chengzi.apiunion.supplier.common.data.beans.SupplierItemSkuInfo;
import com.chengzi.apiunion.supplier.common.template.pojo.*;
import com.chengzi.apiunion.supplier.common.template.service.*;
import com.chengzi.common.util.CollectionUtil;
import org.apache.commons.lang3.StringUtils;
import org.summercool.web.servlet.BeanFactory;

import java.util.*;

/**
 * @author zhiyuan
 * 模板商品 helper
 */
public class TemplateItemDetailHelper {


    public static List<TemplateItemDetailBO> buildTemplateItemDetailBOS(List<Long> templateItemIds) {

        SupplierTemplateItemService templateItemService = BeanFactory.getBean(SupplierTemplateItemService.class);
        SupplierTemplateItemDetailService templateItemDetailService = BeanFactory.getBean(SupplierTemplateItemDetailService.class);
        SupplierTemplateItemImageService templateItemImageService = BeanFactory.getBean(SupplierTemplateItemImageService.class);
        SupplierTemplateItemSkuService templateItemSkuService = BeanFactory.getBean(SupplierTemplateItemSkuService.class);
        SupplierTemplateItemSkuNameService templateItemSkuNameService = BeanFactory.getBean(SupplierTemplateItemSkuNameService.class);
        SupplierTemplateItemSkuValueService templateItemSkuValueService = BeanFactory.getBean(SupplierTemplateItemSkuValueService.class);

        List<TemplateItemDetailBO> itemDetailBOS = new ArrayList<>();

        List<SupplierTemplateItemDO> templateItemDOS = templateItemService.getSupplierTemplateItemByIds(templateItemIds);

        List<SupplierTemplateItemDetailDO> templateItemDetailDOS = templateItemDetailService.getItemDetailByItemIds(templateItemIds);

        Map<String, SupplierTemplateItemDetailDO> itemIdItemDetailMap = CollectionUtil.toMap(templateItemDetailDOS, "supplierTemplateItemId");
        for (SupplierTemplateItemDO templateItemDO : templateItemDOS) {

            long templateItemId = templateItemDO.getId();

            SupplierTemplateItemDetailDO templateItemDetailDO = itemIdItemDetailMap.get(templateItemId);

            List<SupplierTemplateItemSkuDO> templateItemSkuDOS = templateItemSkuService.getByTemplateItemId(templateItemId);
            List<SupplierTemplateItemImageDO> templateItemImageDOS = templateItemImageService.getImageListByTemplateItemId(templateItemId);
            List<SupplierTemplateItemSkuNameDO> templateItemSkuNameDOS = templateItemSkuNameService.selectByTemplateItemId(templateItemId);
            List<SupplierTemplateItemSkuValueDO> templateItemSkuValueDOS = templateItemSkuValueService.selectByTemplateItemId(templateItemId);


            TemplateItemDetailBO templateItemDetailBO = initTemplateItemDetail(templateItemDO, templateItemDetailDO);

            setImages(templateItemDetailBO, templateItemImageDOS);
            setSkuNameValues(templateItemDetailBO, templateItemSkuNameDOS, templateItemSkuValueDOS);


            List<SupplierItemSkuInfo> supplierItemSkuInfos = buildTemplateItemSkuInfo(templateItemSkuDOS, templateItemSkuValueDOS);
            templateItemDetailBO.setSkuList(supplierItemSkuInfos);

            itemDetailBOS.add(templateItemDetailBO);
        }
        return itemDetailBOS;
    }


    private static TemplateItemDetailBO initTemplateItemDetail(SupplierTemplateItemDO templateItemDO,
                                                               SupplierTemplateItemDetailDO templateItemDetailDO) {
        SupplierTemplateCategoryService categoryService = BeanFactory.getBean(SupplierTemplateCategoryService.class);
        TemplateItemDetailBO detailBO = new TemplateItemDetailBO();

        detailBO.setId(templateItemDO.getId());
        detailBO.setBrandId(templateItemDO.getBrandId());
        detailBO.setBrandName(templateItemDO.getBrandName());
        detailBO.setCateId(templateItemDO.getCateId());
        SupplierTemplateCategoryDO templateCategoryDO=  categoryService.getSupplierTemplateById(templateItemDO.getCateId());
        if(templateCategoryDO != null) {
            detailBO.setCateName(templateCategoryDO.getCateName());
        }

        detailBO.setName(templateItemDO.getName());
        detailBO.setModifyTime(templateItemDO.getModifyTime());
        detailBO.setCurrency(templateItemDO.getCurrency());
        detailBO.setStatus(templateItemDO.getStatus());

        if(templateItemDetailDO != null) {
            detailBO.setDesc(templateItemDetailDO.getDesc());
            detailBO.setRichDesc(templateItemDetailDO.getRichDesc());
        }
        return detailBO;
    }

    private static void setImages(TemplateItemDetailBO templateItemDetailBO, List<SupplierTemplateItemImageDO> templateItemImageDOS) {

        List<SupplierItemImageInfo> imageList = new ArrayList<>();
        templateItemImageDOS.forEach(itemImageDO -> {
            String imageUrl = StringUtils.isNotBlank(itemImageDO.getImageUrl()) ? itemImageDO.getImageUrl() : itemImageDO.getOriImageUrl();
            imageList.add(new SupplierItemImageInfo(itemImageDO.getId(), imageUrl, itemImageDO.getType(), itemImageDO.getOrder()));

        });
        Collections.sort(imageList);
        templateItemDetailBO.setImageList(imageList);

    }
    private static void setSkuNameValues(TemplateItemDetailBO templateItemDetailBO, List<SupplierTemplateItemSkuNameDO> itemSkuNameDOS, List<SupplierTemplateItemSkuValueDO> itemSkuValueDOS) {

        // 规格名称及可选值
        List<ItemSkuNameValues> skuNameValues = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(itemSkuNameDOS)) {
            Map<String, List<SupplierTemplateItemSkuValueDO>> skuNameSkuValueDOListMap = CollectionUtil.toListMap(itemSkuValueDOS, "skuName");
            for (SupplierTemplateItemSkuNameDO itemSkuNameDO : itemSkuNameDOS) {
                ItemSkuNameValues skuNV = new ItemSkuNameValues();
                skuNV.setSkuName(SkuUtil.fixDisplaySkuValue(itemSkuNameDO.getSkuName()));
                LinkedHashSet values = new LinkedHashSet();
                List<SupplierTemplateItemSkuValueDO> valueDOList = skuNameSkuValueDOListMap.get(itemSkuNameDO.getSkuName());
                if (CollectionUtil.isNotEmpty(valueDOList)) {
                    valueDOList.sort(Comparator.comparing(SupplierTemplateItemSkuValueDO::getOrder));
                    valueDOList.forEach(valueDO -> {
//                        if (!valueDO.isGroup()) {
                            values.add(valueDO.getSkuValue());
//                        }
                    });
                    skuNV.setSkuValues(values);
                }
                skuNameValues.add(skuNV);
            }
        }
        templateItemDetailBO.setSkuNameValues(skuNameValues);
    }


    public static List<SupplierItemSkuInfo> buildTemplateItemSkuInfo(List<SupplierTemplateItemSkuDO> templateItemSkuDOS,
                                                                     List<SupplierTemplateItemSkuValueDO> templateItemSkuValueDOS) {

        List<SupplierItemSkuInfo> skuInfoList = new ArrayList<>();

        Map<Long, List<SupplierTemplateItemSkuValueDO>> skuIdSkuValueDOMap = CollectionUtil.toListMap(templateItemSkuValueDOS, "supplierTemplateSkuId");;


        for (SupplierTemplateItemSkuDO templateItemSkuDO : templateItemSkuDOS) {
            SupplierItemSkuInfo itemSkuInfo = new SupplierItemSkuInfo();


            itemSkuInfo.setId(templateItemSkuDO.getId());
//            itemSkuInfo.setStatus(templateItemSkuDO.get);
            itemSkuInfo.setWeight(templateItemSkuDO.getWeight());
            itemSkuInfo.setQuantity(templateItemSkuDO.getQuantity());
            itemSkuInfo.setSalePrice(templateItemSkuDO.getRetailPrice());
            itemSkuInfo.setSettlePrice(templateItemSkuDO.getSettlePrice());
            itemSkuInfo.setUpc(templateItemSkuDO.getUpc());

            List<SupplierTemplateItemSkuValueDO> valueDOList = skuIdSkuValueDOMap.get(itemSkuInfo.getId());
            if (CollectionUtil.isNotEmpty(valueDOList)) {
                itemSkuInfo.setSkuValues(SupplierItemSkuInfo.ItemSkuValues.fromTemplateItem(valueDOList));
            }
//            if (skuDO.isGroup()) {
//                List<SupplierItemSkuInfo.SupplierItemSkuGroupInfo> groupInfos = new ArrayList<>();
//                List<SupplierItemSkuGroupDO> groupDOList = skuGroupService.getBySkuId(skuDO.getId());
//                groupDOList.forEach(groupDO -> groupInfos.add(new SupplierItemSkuInfo.SupplierItemSkuGroupInfo(groupDO.getRefSupplierSkuId(),groupDO.getQuantity())));
//                skuInfo.setSkuGroup(groupInfos);
//            }

            itemSkuInfo.setSupplierItemId(templateItemSkuDO.getSupplierTemplateItemId());
            skuInfoList.add(itemSkuInfo);
        }

        return skuInfoList;
    }
}
