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
 * 更新模板品牌
 */
public class UpdateBrandLibraryController extends AbstractApiController<BrandLibraryForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, BrandLibraryForm command) throws Exception {

        SupplierBrandLibraryService brandLibraryService = BeanFactory.getBean(SupplierBrandLibraryService.class);

        Long brandId = command.getBrandId();
        if (brandId == null || brandId == 0) {
            return Result.buildOpFailedResult("品牌ID不能为空");
        }
        SupplierBrandLibraryDO brandLibraryDO = buildBrandLibrary(command);
        Result<String> result = brandLibraryService.update(brandLibraryDO);
        return result;
    }


    private SupplierBrandLibraryDO buildBrandLibrary(BrandLibraryForm command) {
        SupplierBrandLibraryDO brandLibraryDO = new SupplierBrandLibraryDO();

        brandLibraryDO.setId(command.getBrandId());
        brandLibraryDO.setNameZh(command.getNameZh());
        brandLibraryDO.setNameEn(command.getNameEn());

        List<String> alias = command.getAlias();
        if(CollectionUtil.isNotEmpty(alias)) {
            brandLibraryDO.setAlias(JSON.toJSONString(alias));
        }
        brandLibraryDO.setCountryCode(command.getCountryCode());
        return brandLibraryDO;
    }

}
