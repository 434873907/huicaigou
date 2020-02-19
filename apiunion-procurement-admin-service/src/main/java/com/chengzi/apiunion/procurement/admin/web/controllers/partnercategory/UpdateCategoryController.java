package com.chengzi.apiunion.procurement.admin.web.controllers.partnercategory;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.partnercategory.constant.PartnerCategoryConstant;
import com.chengzi.apiunion.partnercategory.pojo.PartnerCategoryDO;
import com.chengzi.apiunion.partnercategory.service.PartnerCategoryService;
import com.chengzi.apiunion.procurement.admin.web.formbean.partnercategory.UpdateCategoryForm;
import com.chengzi.common.data.validate.ErrorConstants;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 更新类目
 *
 * @author 月汐
 * @date 2018/10/12 16:52
 */
public class UpdateCategoryController extends AbstractApiController<UpdateCategoryForm> {
    @Override
    protected Result<Long> doBiz(HttpServletRequest request, HttpServletResponse response, UpdateCategoryForm command) throws Exception {
        PartnerCategoryService service = BeanFactory.getBean(PartnerCategoryService.class);
        PartnerCategoryDO partnerCategoryDO = UpdateCategoryForm.convert(command);
        int result = service.update(partnerCategoryDO);
        if (result == PartnerCategoryConstant.NAME_REPEAT) {
            return Result.buildFailResult(ErrorConstants.ERR_ILLEGAL_ARGUMENT, "目录名在该层级下已存在");
        } else if (result == PartnerCategoryConstant.OP_FAILED) {
            return Result.buildFailResult(ErrorConstants.ERR_OP_FAILED, "操作失败");
        } else {
            return Result.buildSuccessResult(partnerCategoryDO.getId());
        }
    }
}
