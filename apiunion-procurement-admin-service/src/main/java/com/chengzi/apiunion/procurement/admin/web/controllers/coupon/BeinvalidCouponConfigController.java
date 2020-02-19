package com.chengzi.apiunion.procurement.admin.web.controllers.coupon;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.coupon.service.CouponConfigService;
import com.chengzi.apiunion.procurement.admin.web.formbean.coupon.InvalidCouponConfigForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 设置优惠券失效
 */
public class BeinvalidCouponConfigController extends AbstractApiController<InvalidCouponConfigForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, InvalidCouponConfigForm command) throws Exception {

        CouponConfigService couponConfigService = BeanFactory.getBean(CouponConfigService.class);
        int num = couponConfigService.invalidCouponConfig(command.getCouponConfigId());
        if (num > 0) {
            return Result.buildSuccessMsg("处理成功");
        }
        return Result.buildOpFailedResult("处理失败");
    }
}
