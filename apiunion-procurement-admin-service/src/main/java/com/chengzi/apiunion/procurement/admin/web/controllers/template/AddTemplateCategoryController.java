package com.chengzi.apiunion.procurement.admin.web.controllers.template;

import com.alibaba.fastjson.JSON;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.template.AddTemplateCategoryForm;
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
 * 添加模板类目
 */
public class AddTemplateCategoryController extends AbstractApiController<AddTemplateCategoryForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, AddTemplateCategoryForm command) throws Exception {

        SupplierTemplateCategoryService templateCategoryService = BeanFactory.getBean(SupplierTemplateCategoryService.class);

        SupplierTemplateCategoryDO templateCategoryDO = buildTemplateCategory(command);

        Result<String> result = templateCategoryService.add(templateCategoryDO);
        return result;
    }


    private SupplierTemplateCategoryDO buildTemplateCategory(AddTemplateCategoryForm command) {
        SupplierTemplateCategoryDO templateCategoryDO = new SupplierTemplateCategoryDO();

        templateCategoryDO.setCateName(command.getCateName());
        templateCategoryDO.setCateDesc(command.getCateDesc());

        List<String> cateSkuNames = command.getCateSkuNames();
        if(CollectionUtil.isNotEmpty(cateSkuNames)) {
            templateCategoryDO.setCateSkuNames(JSON.toJSONString(cateSkuNames));
        }
        templateCategoryDO.setType(command.getType());
        return templateCategoryDO;
    }

    public static void main(String[] args) {
        List<String> cateSkuNames = CollectionUtil.asArrayList("ab", "dd");
        System.out.println(JSON.toJSONString(cateSkuNames));
    }

}
