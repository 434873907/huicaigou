package com.chengzi.apiunion.procurement.admin.web.formbean.activity;

import com.chengzi.apiunion.common.data.enums.CurrencyEnum;
import com.chengzi.apiunion.sales.pojo.ParticipateItem;
import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;
import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.NotNull;
import net.sf.oval.constraint.Range;

import java.util.Date;

/**
 * <p>
 * </p>
 * User: 摩天
 * Date: 2019-02-20 15:48
 */
public class AddFlashSaleActivityForm extends BaseForm {
    /**
     * 开始时间
     */
    @NotNull(message = "开始时间不能为空")
    private Date   startTime;

    /**
     * 结束时间
     */
    @NotNull(message = "结束时间不能为空")
    private Date   endTime;

    /**
     * 货币
     * {@link CurrencyEnum}
     */
    @Range(min = 1,max =6, message = "货币不能为空")
    private int    currency;

    /**
     * 是否禁用优惠券，0：否，1：是
     */
    @Range(min = 0, max = 1, message = "请选择是否禁用优惠券")
    private int    isDisableCoupon;

    /**
     * 参与商品id及数量
     * @see ParticipateItem
     */
    @NotBlankAndNull(message = "活动商品不能为空")
    private String participateItemNum;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public int getIsDisableCoupon() {
        return isDisableCoupon;
    }

    public void setIsDisableCoupon(int isDisableCoupon) {
        this.isDisableCoupon = isDisableCoupon;
    }

    public String getParticipateItemNum() {
        return participateItemNum;
    }

    public void setParticipateItemNum(String participateItemNum) {
        this.participateItemNum = participateItemNum;
    }
}
