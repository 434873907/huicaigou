package com.chengzi.apiunion.procurement.admin.web.controllers.run;

import com.chengzi.apiunion.brand.util.BrandEsUtil;
import com.chengzi.apiunion.common.web.controllers.AbstractManageController;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.EmptyForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 月汐
 * @date 2019/08/12 16:58
 */
public class EsClearAndRefreshBrandController extends AbstractManageController<EmptyForm> {
    @Override
    protected Result<?> doInnerBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {
        return Result.buildSuccessResult(BrandEsUtil.clearAndRefreshEs());
    }
}
