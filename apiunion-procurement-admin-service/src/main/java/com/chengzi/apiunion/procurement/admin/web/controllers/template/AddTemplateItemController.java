package com.chengzi.apiunion.procurement.admin.web.controllers.template;

import com.alibaba.fastjson.JSON;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.enums.ItemImageTypeEnum;
import com.chengzi.apiunion.item.enums.ItemStatusEnum;
import com.chengzi.apiunion.item.pojo.ItemSkuNameValues;
import com.chengzi.apiunion.procurement.admin.web.formbean.template.AddTemplateItemForm;
import com.chengzi.apiunion.supplier.common.data.beans.SupplierItemImageInfo;
import com.chengzi.apiunion.supplier.common.data.beans.SupplierItemSkuInfo;
import com.chengzi.apiunion.supplier.common.template.pojo.SupplierTemplateItemDO;
import com.chengzi.apiunion.supplier.common.template.pojo.SupplierTemplateItemDetailDO;
import com.chengzi.apiunion.supplier.common.template.service.SupplierTemplateItemService;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import org.springframework.validation.BindException;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

public class AddTemplateItemController extends AbstractApiController<AddTemplateItemForm> {

    private static final int MAX_IMG_NUM = 8;

    @Override
    protected void onBindAndValidate(HttpServletRequest request, AddTemplateItemForm command, BindException errors) throws Exception {
        super.onBindAndValidate(request, command, errors);
        if (errors.hasErrors()) {
            return;
        }


        if (CollectionUtil.isEmpty(command.getSkuNameValues())) {
            addError(command, errors, "skuNameValues", command.getSkuNameValues(), "添加规格名称");
        }


        Map<Integer, List<SupplierItemImageInfo>> imageMap = CollectionUtil.toListMap(command.getAddImages(), "type");
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
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, AddTemplateItemForm command) throws Exception {
        SupplierTemplateItemService templateItemService = BeanFactory.getBean(SupplierTemplateItemService.class);

        Result<String> checkResult = check(command);

        if (!checkResult.isSuccess()) {
            return checkResult;
        }
        try {
            SupplierTemplateItemDO templateItemDO = buildTemplateItemDO(command);
            SupplierTemplateItemDetailDO templateItemDetailDO = buildTemplateItemDetailDO(command);

            Result<String> result = templateItemService.addTemplateItem(templateItemDO, templateItemDetailDO, command.getSkuNameValues(),
                    command.getAddImages(), command.getSkuList());

            return result;
        } catch (Exception e) {
            logger.error("error", e);
        }
        return Result.buildOpFailedResult("添加失败");
    }

    private SupplierTemplateItemDO buildTemplateItemDO(AddTemplateItemForm itemForm) {
        SupplierTemplateItemDO templateItemDO = new SupplierTemplateItemDO();

        templateItemDO.setName(itemForm.getName());
        templateItemDO.setBrandId(itemForm.getBrandId());
        templateItemDO.setBrandName(itemForm.getBrandName());
        templateItemDO.setCurrency(itemForm.getCurrency());
        templateItemDO.setUnit(itemForm.getUnit());
        templateItemDO.setCateId(itemForm.getCategoryId());
        templateItemDO.setStatus(itemForm.getStatus());

        templateItemDO.setItemModel(itemForm.getItemModel());
        return templateItemDO;
    }

    private SupplierTemplateItemDetailDO buildTemplateItemDetailDO(AddTemplateItemForm itemForm) {
        SupplierTemplateItemDetailDO itemDetailDO = new SupplierTemplateItemDetailDO();

        itemDetailDO.setDesc(itemForm.getDesc());
        itemDetailDO.setRichDesc(itemForm.getRichDesc());

//        itemDetailDO.setSupplierId(supplierId);
        return itemDetailDO;
    }

    private Result<String> check(AddTemplateItemForm command) {
        Integer status = command.getStatus();
        if (status == null) {
            return Result.buildOpFailedResult("状态参数错误");
        }
        if (status != 1 && status != 2) {//草稿，发布
            return Result.buildOpFailedResult("状态参数错误");
        }
        HashMap<String, LinkedHashSet<String>> skuNameValuesMap = new HashMap();
        for (ItemSkuNameValues skuNameValues : command.getSkuNameValues()) {
            skuNameValuesMap.put(skuNameValues.getSkuName(), skuNameValues.getSkuValues());
        }
        //TODO 规格校验
        for (SupplierItemSkuInfo skuInfo : command.getSkuList()) {

            SupplierItemSkuInfo.ItemSkuValues itemSkuValues = skuInfo.getSkuValues();
            if (itemSkuValues != null) {
                for (SupplierItemSkuInfo.ItemSkuValue itemSkuValue : itemSkuValues) {
                    LinkedHashSet<String> valueSet = skuNameValuesMap.get(itemSkuValue.getSkuName());
                    if (!valueSet.contains(itemSkuValue.getSkuValue())) {

                        return Result.buildOpFailedResult("规格参数错误");
                    }
                }
            }
        }
        //价格校验
        for (SupplierItemSkuInfo skuInfo : command.getSkuList()) {
            BigDecimal settlePrice = skuInfo.getSettlePrice();
            BigDecimal salePrice = skuInfo.getSalePrice();
            if (salePrice != null && settlePrice != null) {

            } else {
                return Result.buildOpFailedResult("价格参数错误");
            }
        }
        return Result.buildSuccessMsg("校验通过");
    }
}
