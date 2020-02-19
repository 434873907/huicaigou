package com.chengzi.apiunion.procurement.admin.web.formbean.coupon;

import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;

/**
 * @author 致远
 * 设置优惠券失效请求bean
 */
public class InvalidCouponConfigForm extends BaseForm {

    /**
     * 优惠券ID
     */
    @Min(1)
    private long couponConfigId;

    public long getCouponConfigId() {
        return couponConfigId;
    }

    public void setCouponConfigId(long couponConfigId) {
        this.couponConfigId = couponConfigId;
    }
}
