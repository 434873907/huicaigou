package com.chengzi.apiunion.procurement.admin.web.controllers.partnercategory;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.partnercategory.constant.PartnerCategoryConstant;
import com.chengzi.apiunion.partnercategory.pojo.PartnerCategoryDO;
import com.chengzi.apiunion.partnercategory.service.PartnerCategoryService;
import com.chengzi.apiunion.procurement.admin.web.formbean.partnercategory.GetCategoryByIdForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.partnercategory.GetCategoryByIdBO;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 获取类目详情
 *
 * @author 月汐
 * @date 2018/10/15 10:09
 */
public class GetCategoryByIdController extends AbstractApiController<GetCategoryByIdForm> {

    @Override
    protected Result<GetCategoryByIdBO> doBiz(HttpServletRequest request, HttpServletResponse response, GetCategoryByIdForm command) throws Exception {
        PartnerCategoryService service = BeanFactory.getBean(PartnerCategoryService.class);
        PartnerCategoryDO partnerCategoryDO = service.getById(command.getId());
        GetCategoryByIdBO result;
        if (PartnerCategoryConstant.FIRST_CLASS_CATEGORY == partnerCategoryDO.getDepth()) {
            result = GetCategoryByIdBO.convert(partnerCategoryDO, null, null);
        } else if (PartnerCategoryConstant.SECOND_CLASS_CATEGORY  == partnerCategoryDO.getDepth()) {
            result = GetCategoryByIdBO.convert(partnerCategoryDO, partnerCategoryDO.getParentId(), null);
        } else {
            PartnerCategoryDO parent = service.getById(partnerCategoryDO.getParentId());
            result = GetCategoryByIdBO.convert(partnerCategoryDO, parent.getParentId(), parent.getId());
        }
        return Result.buildSuccessResult(result);
    }

}
