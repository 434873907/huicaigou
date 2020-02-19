package com.chengzi.apiunion.procurement.admin.web.controllers.item;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.service.ItemService;
import com.chengzi.apiunion.partnercategory.constant.PartnerCategoryConstant;
import com.chengzi.apiunion.partnercategory.pojo.PartnerCategoryDO;
import com.chengzi.apiunion.partnercategory.service.PartnerCategoryService;
import com.chengzi.apiunion.procurement.admin.web.formbean.item.UpdateItemCateForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 月汐
 * @date 2019/07/11 14:07
 */
public class UpdateItemCateController extends AbstractApiController<UpdateItemCateForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, UpdateItemCateForm command) throws Exception {
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

        ItemService itemService = BeanFactory.getBean(ItemService.class);

        return itemService.updateItemCate(command.getItemIds(), firstCateDO.getId(), secondCateDO.getId(), thirdCateDO.getId());
    }
}
