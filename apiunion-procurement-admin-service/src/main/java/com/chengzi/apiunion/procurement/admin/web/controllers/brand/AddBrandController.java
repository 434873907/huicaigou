package com.chengzi.apiunion.procurement.admin.web.controllers.brand;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.brand.constant.BrandConstant;
import com.chengzi.apiunion.brand.pojo.BrandDO;
import com.chengzi.apiunion.brand.service.BrandService;
import com.chengzi.apiunion.procurement.admin.web.formbean.brand.AddBrandForm;
import com.chengzi.common.data.validate.ErrorConstants;
import com.chengzi.common.data.validate.Result;
import org.apache.commons.lang3.StringUtils;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 新增品牌
 *
 * @author 月汐
 * @date 2018/10/15 16:00
 */
public class AddBrandController extends AbstractApiController<AddBrandForm> {

    @Override
    protected Result<Long> doBiz(HttpServletRequest request, HttpServletResponse response, AddBrandForm command) throws Exception {
        if (StringUtils.isBlank(command.getName()) && StringUtils.isBlank(command.getOriginalName())) {
            return Result.buildIllegalArgumentResult("品牌中文名和英文名至少存在一个");
        }

        BrandService service = BeanFactory.getBean(BrandService.class);
        BrandDO brandDO = AddBrandForm.convert(command);
        long result = service.add(brandDO);
        if (result > 0) {
            return Result.buildSuccessResult(result);
        } else if(BrandConstant.NAME_REPEAT == result) {
            return Result.buildFailResult(ErrorConstants.ERR_ILLEGAL_ARGUMENT, "该品牌已存在！");
        } else {
            return Result.buildOpFailedResult("添加品牌失败");
        }
    }

}
