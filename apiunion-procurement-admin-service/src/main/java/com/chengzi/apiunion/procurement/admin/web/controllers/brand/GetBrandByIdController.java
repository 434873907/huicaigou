package com.chengzi.apiunion.procurement.admin.web.controllers.brand;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.brand.pojo.BrandDO;
import com.chengzi.apiunion.brand.service.BrandService;
import com.chengzi.apiunion.procurement.admin.web.formbean.brand.GetBrandByIdForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.brand.GetBrandByIdBO;
import com.chengzi.common.data.validate.ErrorConstants;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 获取品牌详情
 *
 * @author 月汐
 * @date 2018/10/15 16:57
 */
public class GetBrandByIdController extends AbstractApiController<GetBrandByIdForm> {

    @Override
    protected Result<GetBrandByIdBO> doBiz(HttpServletRequest request, HttpServletResponse response, GetBrandByIdForm command) throws Exception {
        BrandService service = BeanFactory.getBean(BrandService.class);
        BrandDO brandDO = service.getById(command.getId());
        if (brandDO != null && brandDO.getId() != 0) {
            return Result.buildSuccessResult(GetBrandByIdBO.convert(brandDO));
        }
        return Result.buildFailResult(ErrorConstants.ERR_OP_FAILED, "操作失败");
    }

}
