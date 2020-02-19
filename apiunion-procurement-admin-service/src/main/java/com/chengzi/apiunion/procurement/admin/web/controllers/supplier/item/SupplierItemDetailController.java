package com.chengzi.apiunion.procurement.admin.web.controllers.supplier.item;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.pojo.ItemSkuNameValues;
import com.chengzi.apiunion.procurement.admin.web.formbean.supplier.item.SupplierItemDetailForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.supplier.SupplierItemDetailBO;
import com.chengzi.apiunion.supplier.common.data.beans.SupplierItemImageInfo;
import com.chengzi.apiunion.supplier.common.item.pojo.SupplierItemImageDO;
import com.chengzi.apiunion.supplier.common.item.pojo.SupplierItemRichDO;
import com.chengzi.apiunion.supplier.common.item.service.SupplierItemImageService;
import com.chengzi.apiunion.supplier.common.item.service.SupplierItemService;
import com.chengzi.apiunion.supplier.common.item.service.SupplierItemSkuService;
import com.chengzi.apiunion.supplier.common.item.util.SupplierItemHelper;
import com.chengzi.apiunion.supplier.common.template.pojo.SupplierTemplateCategoryDO;
import com.chengzi.apiunion.supplier.common.template.service.SupplierTemplateCategoryService;
import com.chengzi.apiunion.supplier.pojo.SupplierDO;
import com.chengzi.apiunion.supplier.service.SupplierService;
import com.chengzi.common.data.validate.Result;
import org.apache.commons.lang3.StringUtils;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A端 查询各个供应商的商品详情
 */
public class SupplierItemDetailController extends AbstractApiController<SupplierItemDetailForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, SupplierItemDetailForm command) throws Exception {

        long itemId = command.getItemId();

        SupplierItemDetailBO itemDetailBO = buildItemDetailBO(itemId);
        if (itemDetailBO == null) {
            return Result.buildSuccessMsg("商品不存在");
        }
        return Result.buildSuccessResult(itemDetailBO);
    }

    private SupplierItemDetailBO buildItemDetailBO(long itemId) {
        SupplierService supplierService = BeanFactory.getBean(SupplierService.class);
//        PartnerCategoryService categoryService = BeanFactory.getBean(PartnerCategoryService.class);
        SupplierTemplateCategoryService templateCategoryService = BeanFactory.getBean(SupplierTemplateCategoryService.class);
        SupplierItemService supplierItemService = BeanFactory.getBean(SupplierItemService.class);
        SupplierItemImageService supplierItemImageService = BeanFactory.getBean(SupplierItemImageService.class);
        SupplierItemSkuService skuService = BeanFactory.getBean(SupplierItemSkuService.class);

        SupplierItemDetailBO supplierItemDetailBO = new SupplierItemDetailBO();

        SupplierItemRichDO itemRichDO = supplierItemService.getItemRichBySupplierItemId(itemId);
        if (itemRichDO == null) {
            return null;
        }
        supplierItemDetailBO.setId(itemRichDO.getId());
        supplierItemDetailBO.setCreateTime(itemRichDO.getCreateTime());
        supplierItemDetailBO.setModifyTime(itemRichDO.getModifyTime());
        supplierItemDetailBO.setDeleted(itemRichDO.isDeleted());
        supplierItemDetailBO.setName(itemRichDO.getName());
        supplierItemDetailBO.setOriginalName(itemRichDO.getOriginalName());
        supplierItemDetailBO.setBrandId(itemRichDO.getBrandId());
        supplierItemDetailBO.setBrandName(itemRichDO.getBrandName());
        supplierItemDetailBO.setSupplierId(itemRichDO.getSupplierId());
        if (itemRichDO.getSupplierId() != null) {
            SupplierDO supplierDO = supplierService.getSupplier(itemRichDO.getSupplierId());
            if (supplierDO != null) {
                supplierItemDetailBO.setSupplierName(supplierDO.getSupplierName());
            }
        }
        supplierItemDetailBO.setItemModel(itemRichDO.getItemModel());
        supplierItemDetailBO.setUnit(itemRichDO.getUnit());
        supplierItemDetailBO.setCurrency(itemRichDO.getCurrency());

        supplierItemDetailBO.setRichDesc(itemRichDO.getRichDesc());
        supplierItemDetailBO.setStatus(itemRichDO.getStatus());
        supplierItemDetailBO.setCateId(itemRichDO.getCateId());
        if (itemRichDO.getCateId() > 0) {
            SupplierTemplateCategoryDO templateCategoryDO = templateCategoryService.getSupplierTemplateById(itemRichDO.getCateId());
            if (templateCategoryDO != null) {
                supplierItemDetailBO.setCateName(templateCategoryDO.getCateName());
            }
        }
        supplierItemDetailBO.setDesc(itemRichDO.getDesc());

        if (itemRichDO.getSupplierTemplateItemId() > 0) {
            supplierItemDetailBO.setTemplateItemId(itemRichDO.getSupplierTemplateItemId());
            supplierItemDetailBO.setFromTemplate(true);
        } else {
            supplierItemDetailBO.setFromTemplate(false);
        }
        List<ItemSkuNameValues> itemSkuNameValuesList = SupplierItemHelper.getItemSkuNameValuesList(itemId);


        List<SupplierItemImageDO> supplierItemImageDOList = supplierItemImageService.getImageListByItemId(itemId);
        List<SupplierItemImageInfo> imageList = new ArrayList<>();
        supplierItemImageDOList.forEach(itemImageDO -> {
            String imageUrl = StringUtils.isNotBlank(itemImageDO.getImageUrl()) ? itemImageDO.getImageUrl() : itemImageDO.getOriImageUrl();
            imageList.add(new SupplierItemImageInfo(itemImageDO.getId(), imageUrl, itemImageDO.getType(),itemImageDO.getOrder()));
        });
        Collections.sort(imageList);
        supplierItemDetailBO.setImageList(imageList);

        supplierItemDetailBO.setSkuNameValues(itemSkuNameValuesList);

        supplierItemDetailBO.setSkuList(skuService.getSkuInfoByItemId(itemId));

        return supplierItemDetailBO;
    }

}
