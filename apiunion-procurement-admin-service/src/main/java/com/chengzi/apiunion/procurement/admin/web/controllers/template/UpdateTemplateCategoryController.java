package com.chengzi.apiunion.procurement.admin.web.controllers.template;

import com.alibaba.fastjson.JSON;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.template.AddTemplateCategoryForm;
import com.chengzi.apiunion.procurement.admin.web.formbean.template.UpdateTemplateCategoryForm;
import com.chengzi.apiunion.supplier.common.template.pojo.SupplierTemplateCategoryDO;
import com.chengzi.apiunion.supplier.common.template.service.SupplierTemplateCategoryService;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

/**
 * 更新模板类目
 */
public class UpdateTemplateCategoryController extends AbstractApiController<UpdateTemplateCategoryForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, UpdateTemplateCategoryForm command) throws Exception {

        SupplierTemplateCategoryService templateCategoryService = BeanFactory.getBean(SupplierTemplateCategoryService.class);

        if(command.getId() == null || command.getId() <=0) {
            return Result.buildOpFailedResult("ID不能为空");
        }
        SupplierTemplateCategoryDO templateCategoryDO = buildTemplateCategory(command);

        Result<String> result = templateCategoryService.upate(templateCategoryDO);
        return result;
    }

    private SupplierTemplateCategoryDO buildTemplateCategory(UpdateTemplateCategoryForm command) {
        SupplierTemplateCategoryDO templateCategoryDO = new SupplierTemplateCategoryDO();

        templateCategoryDO.setId(command.getId());
        templateCategoryDO.setCateName(command.getCateName());
        templateCategoryDO.setCateDesc(command.getCateDesc());

        List<String> cateSkuNames = command.getCateSkuNames();
        if(CollectionUtil.isNotEmpty(cateSkuNames)) {
            templateCategoryDO.setCateSkuNames(JSON.toJSONString(cateSkuNames));
        }
        return templateCategoryDO;
    }

}
