package com.chengzi.apiunion.procurement.admin.web.controllers.template;

import com.alibaba.fastjson.JSON;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.template.BrandLibraryForm;
import com.chengzi.apiunion.supplier.common.template.pojo.SupplierBrandLibraryDO;
import com.chengzi.apiunion.supplier.common.template.service.SupplierBrandLibraryService;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

/**
 * 添加模板品牌
 */
public class AddBrandLibraryController extends AbstractApiController<BrandLibraryForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, BrandLibraryForm command) throws Exception {

        SupplierBrandLibraryService brandLibraryService = BeanFactory.getBean(SupplierBrandLibraryService.class);

        SupplierBrandLibraryDO brandLibraryDO = buildBrandLibrary(command);

        Result<String> result = brandLibraryService.add(brandLibraryDO);
        return result;
    }


    private SupplierBrandLibraryDO buildBrandLibrary(BrandLibraryForm command) {
        SupplierBrandLibraryDO brandLibraryDO = new SupplierBrandLibraryDO();

        brandLibraryDO.setNameZh(command.getNameZh());
        brandLibraryDO.setNameEn(command.getNameEn());

        List<String> alias = command.getAlias();
        if(CollectionUtil.isNotEmpty(alias)) {
            brandLibraryDO.setAlias(JSON.toJSONString(alias));
        }
        brandLibraryDO.setCountryCode(command.getCountryCode());
        return brandLibraryDO;
    }

    public static void main(String[] args) {
        BrandLibraryForm form = new BrandLibraryForm();
        form.setNameEn("KM");
        form.setNameZh("KM");
        form.setCountryCode(1);

        String json = JSON.toJSONString(form);
        System.out.println(json);

    }
}
