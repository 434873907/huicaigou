package com.chengzi.apiunion.procurement.admin.web.controllers.brand;

import com.chengzi.apiunion.brand.pojo.BrandDO;
import com.chengzi.apiunion.brand.service.BrandService;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.brand.UpdateBrandPropStatusForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 月汐
 * @date 2019/11/05 15:11
 */
public class UpdateBrandPropStatusController extends AbstractApiController<UpdateBrandPropStatusForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, UpdateBrandPropStatusForm command) throws Exception {
        BrandService brandService = BeanFactory.getBean(BrandService.class);
        BrandDO brandDO = new BrandDO();
        brandDO.setId(command.getId());
        brandDO.setPropStatus(command.getPropStatus());
        return brandService.updatePropStatus(brandDO);
    }
}
