package com.chengzi.apiunion.procurement.admin.web.controllers.coupon;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.coupon.CouponConstants;
import com.chengzi.apiunion.coupon.enums.CouponConditionEnum;
import com.chengzi.apiunion.coupon.pojo.CouponConfigDO;
import com.chengzi.apiunion.coupon.service.CouponConfigService;
import com.chengzi.apiunion.procurement.admin.web.formbean.coupon.AddCouponConfigForm;
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
public class AddCouponConfigController extends AbstractApiController<AddCouponConfigForm> {

    @Override
    protected Result<String> doBiz(HttpServletRequest request, HttpServletResponse response, AddCouponConfigForm command)
            throws Exception {
        if (command.getTimeType() == CouponConstants.AFTER_DAYS && command.getAfterDays() <= 0) {
            return Result.buildIllegalArgumentResult("天数不能小于1");
        }
        if (command.getTimeType() == CouponConstants.TIME_LIMIT && (command.getStartTime() == null || command.getEndTime() == null)) {
            return Result.buildIllegalArgumentResult("开始时间或结束时间不能为空");
        }
        CouponConfigService couponConfigService = BeanFactory.getBean(CouponConfigService.class);
        CouponConfigDO couponConfigDO = new CouponConfigDO();
        couponConfigDO.setCouponType(command.getCouponType());
        couponConfigDO.setCouponCode(command.getCouponCode());
        couponConfigDO.setCouponName(command.getCouponName());
        couponConfigDO.setCouponValue(command.getCouponValue());
        couponConfigDO.setCouponValueType(command.getCouponValueType());
        couponConfigDO.setOrderPriceLimit(command.getOrderPriceLimit());
        couponConfigDO.setTimeType(command.getTimeType());
        couponConfigDO.setAfterDays(command.getAfterDays());
        couponConfigDO.setStartTime(command.getStartTime());
        couponConfigDO.setEndTime(command.getEndTime());
        couponConfigDO.setCreateUserId(RequestContext.getUserId());
        couponConfigDO.setModifyUserId(RequestContext.getUserId());
        couponConfigDO.setCouponCount(command.getCouponCount());
        couponConfigDO.setDesc(command.getDesc());
        couponConfigDO.setSendCouponCount(0);
        couponConfigDO.setConditionType(command.getConditionType());
        couponConfigDO.setStatus(0);
        if (CouponConditionEnum.NONE_CONDITION.getCode() != command.getConditionType()) {
            couponConfigDO.setCondition(command.getCondition());
        }
        long id = couponConfigService.addCouponConfig(couponConfigDO);

        if (id > 0) {
            return Result.buildSuccessMsg("保存成功");
        }
        return Result.buildOpFailedResult("保存失败");
    }
}
