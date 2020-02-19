package com.chengzi.apiunion.procurement.admin.web.controllers.partnercategory;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.partnercategory.pojo.PartnerCategoryDO;
import com.chengzi.apiunion.partnercategory.service.PartnerCategoryService;
import com.chengzi.apiunion.procurement.admin.web.formbean.partnercategory.AddCategoryForm;
import com.chengzi.common.data.validate.ErrorConstants;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 新增类目
 *
 * @author 月汐
 * @date 2018/10/12 14:52
 */
public class AddCategoryController extends AbstractApiController<AddCategoryForm> {

    @Override
    protected Result<Long> doBiz(HttpServletRequest request, HttpServletResponse response, AddCategoryForm command) throws Exception {
        PartnerCategoryDO partnerCategoryDO = AddCategoryForm.convert(command);
        PartnerCategoryService service = BeanFactory.getBean(PartnerCategoryService.class);
        Long result = service.add(partnerCategoryDO);
        if (result != null && result > 0) {
            return Result.buildSuccessResult(result);
        } else {
            return Result.buildFailResult(ErrorConstants.ERR_OP_FAILED, "操作失败");
        }
    }

}
