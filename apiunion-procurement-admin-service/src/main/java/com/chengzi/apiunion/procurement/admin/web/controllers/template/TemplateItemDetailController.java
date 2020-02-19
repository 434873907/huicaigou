package com.chengzi.apiunion.procurement.admin.web.controllers.template;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.pojo.ItemSkuNameValues;
import com.chengzi.apiunion.procurement.admin.web.formbean.template.TemplateDetailItemForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.template.TemplateItemDetailBO;
import com.chengzi.apiunion.supplier.common.data.beans.SupplierItemImageInfo;
import com.chengzi.apiunion.supplier.common.data.beans.SupplierItemSkuInfo;
import com.chengzi.apiunion.supplier.common.template.pojo.SupplierTemplateCategoryDO;
import com.chengzi.apiunion.supplier.common.template.pojo.SupplierTemplateItemDO;
import com.chengzi.apiunion.supplier.common.template.pojo.SupplierTemplateItemDetailDO;
import com.chengzi.apiunion.supplier.common.template.pojo.SupplierTemplateItemImageDO;
import com.chengzi.apiunion.supplier.common.template.service.*;
import com.chengzi.apiunion.supplier.common.template.util.TemplateItemHelper;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * A端 查询模板商品详情
 */
public class TemplateItemDetailController extends AbstractApiController<TemplateDetailItemForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, TemplateDetailItemForm command) throws Exception {

        long templateItemId = command.getTemplateItemId();

        TemplateItemDetailBO itemDetailBO = buildTemplateItemDetailBO(templateItemId);
        if (itemDetailBO == null) {
            return Result.buildSuccessMsg("商品不存在");
        }
        return Result.buildSuccessResult(itemDetailBO);
    }

    private TemplateItemDetailBO buildTemplateItemDetailBO(long templateItemId) {

        SupplierTemplateItemService templateItemService = BeanFactory.getBean(SupplierTemplateItemService.class);
        SupplierTemplateItemDetailService templateItemDetailService = BeanFactory.getBean(SupplierTemplateItemDetailService.class);
        SupplierTemplateItemImageService templateItemImageService = BeanFactory.getBean(SupplierTemplateItemImageService.class);
        SupplierTemplateCategoryService categoryService = BeanFactory.getBean(SupplierTemplateCategoryService.class);
        SupplierTemplateItemSkuService templateItemSkuService = BeanFactory.getBean(SupplierTemplateItemSkuService.class);

        TemplateItemDetailBO templateItemDetailBO = new TemplateItemDetailBO();
        List<SupplierTemplateItemDO> templateItemDOS =  templateItemService.getSupplierTemplateItemByIds(CollectionUtil.asArrayList(templateItemId));
        List<SupplierTemplateItemDetailDO> templateItemDetailDOS = templateItemDetailService.getItemDetailByItemIds(CollectionUtil.asArrayList(templateItemId));

        SupplierTemplateItemDO templateItemDO;
        SupplierTemplateItemDetailDO templateItemDetailDO;
        if(CollectionUtil.isNotEmpty(templateItemDOS) && CollectionUtil.isNotEmpty(templateItemDetailDOS)) {
            templateItemDO = templateItemDOS.get(0);
            templateItemDetailDO = templateItemDetailDOS.get(0);
        } else {
            return null;
        }
        templateItemDetailBO.setId(templateItemDO.getId());
        templateItemDetailBO.setCreateTime(templateItemDO.getCreateTime());
        templateItemDetailBO.setModifyTime(templateItemDO.getModifyTime());
        templateItemDetailBO.setDeleted(templateItemDO.isDeleted());
        templateItemDetailBO.setName(templateItemDO.getName());
        templateItemDetailBO.setBrandId(templateItemDO.getBrandId());
        templateItemDetailBO.setBrandName(templateItemDO.getBrandName());
        templateItemDetailBO.setItemModel(templateItemDO.getItemModel());
        templateItemDetailBO.setUnit(templateItemDO.getUnit());
        templateItemDetailBO.setCurrency(templateItemDO.getCurrency());
        templateItemDetailBO.setStatus(templateItemDO.getStatus());

        templateItemDetailBO.setCateId(templateItemDO.getCateId());
        SupplierTemplateCategoryDO templateCategoryDO = categoryService.getSupplierTemplateById(templateItemDO.getCateId());
        if(templateCategoryDO != null) {
            templateItemDetailBO.setCateName(templateCategoryDO.getCateName());
        }
        templateItemDetailBO.setRichDesc(templateItemDetailDO.getRichDesc());
        templateItemDetailBO.setDesc(templateItemDetailDO.getDesc());

        List<ItemSkuNameValues> itemSkuNameValuesList = TemplateItemHelper.getItemSkuNameValuesList(templateItemId);

        List<SupplierTemplateItemImageDO> templateItemImageDOS = templateItemImageService.getImageListByTemplateItemId(templateItemId);
        List<SupplierItemImageInfo> imageList =  TemplateItemHelper.buildImage(templateItemImageDOS);
        templateItemDetailBO.setImageList(imageList);

        templateItemDetailBO.setSkuNameValues(itemSkuNameValuesList);

//        TemplateItemHelper.buildTemplateItemSkuInfo(templateItemSkuDOS);
        List<SupplierItemSkuInfo> itemSkuInfos  = templateItemSkuService.getSkuInfoByItemId(templateItemId);
        templateItemDetailBO.setSkuList( itemSkuInfos);

        return templateItemDetailBO;
    }

}
