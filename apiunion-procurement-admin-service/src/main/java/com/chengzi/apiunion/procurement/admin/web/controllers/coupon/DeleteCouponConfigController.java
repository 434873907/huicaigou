package com.chengzi.apiunion.procurement.admin.web.controllers.coupon;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.coupon.service.CouponConfigService;
import com.chengzi.apiunion.procurement.admin.web.formbean.coupon.DeleteCouponConfigForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * </p>
 * User: 摩天
 * Date: 2018-10-19 13:56
 */
public class DeleteCouponConfigController extends AbstractApiController<DeleteCouponConfigForm> {

    @Override
    protected Result doBiz(HttpServletRequest request, HttpServletResponse response, DeleteCouponConfigForm command)
            throws Exception {
        CouponConfigService couponConfigService = BeanFactory.getBean(CouponConfigService.class);
        int num = couponConfigService.deleteCouponConfig(command.getCouponConfigId());
        if (num > 0) {
            return Result.buildSuccessMsg("保存成功");
        }
        return Result.buildOpFailedResult("保存失败");
    }
}
