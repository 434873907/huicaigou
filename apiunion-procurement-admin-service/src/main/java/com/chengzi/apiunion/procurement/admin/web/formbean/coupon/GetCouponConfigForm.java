package com.chengzi.apiunion.procurement.admin.web.formbean.coupon;

import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;

/**
 * <p>
 * </p>
 * User: 摩天
 * Date: 2018-10-19 14:05
 */
public class GetCouponConfigForm extends BaseForm {

    @Min(1)
    private long couponConfigId;

    public long getCouponConfigId() {
        return couponConfigId;
    }

    public void setCouponConfigId(long couponConfigId) {
        this.couponConfigId = couponConfigId;
    }
}
