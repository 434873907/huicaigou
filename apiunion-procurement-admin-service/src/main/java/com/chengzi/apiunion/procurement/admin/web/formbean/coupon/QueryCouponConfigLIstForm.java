package com.chengzi.apiunion.procurement.admin.web.formbean.coupon;

import com.chengzi.common.web.formbean.BasePageForm;

/**
 * <p>
 * </p>
 * User: 摩天
 * Date: 2018-10-19 14:06
 */
public class QueryCouponConfigLIstForm extends BasePageForm {
    /**
     * 券名称
     */
    private String keyWord;

    /**
     * 券状态1:使用中 2：未开始 3：已结束，4：已失效
     */
    private int    couponStatus;

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public int getCouponStatus() {
        return couponStatus;
    }

    public void setCouponStatus(int couponStatus) {
        this.couponStatus = couponStatus;
    }
}
