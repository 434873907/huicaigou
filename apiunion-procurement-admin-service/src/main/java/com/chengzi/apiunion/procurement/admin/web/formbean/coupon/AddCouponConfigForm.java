package com.chengzi.apiunion.procurement.admin.web.formbean.coupon;

import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.Range;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * </p>
 * User: 摩天
 * Date: 2018-10-19 14:04
 */
public class AddCouponConfigForm extends BaseForm {

    /**
     * 优惠券类型 0优惠券 1优惠码
     */
    @Min(0)
    private int        couponType;

    /**
     * 优惠码
     */
    private String     couponCode;

    /**
     * 优惠券名称
     */
    private String     couponName;

    /**
     * 优惠券券值类型（0：金额，1：折扣）
     */
    @Min(0)
    private int        couponValueType;

    /**
     * 优惠券券值
     */
    @Min(0)
    private BigDecimal couponValue;

    /**
     * 满减额度
     */
    @Min(0)
    private BigDecimal orderPriceLimit;

    /**
     * 总数量
     */
    @Min(0)
    private int        couponCount;

    /**
     * 条件限制JSON
     * @see com.chengzi.apiunion.coupon.enums.CouponConditionEnum
     */
    @Range(min = 0,max = 3,message = "限制类型不正确")
    private int        conditionType;

    /**
     * 条件限制JSON
     */
    private String     condition;

    /**
     * 期限类型  0、几天后截止 1、开始日期到结束日期
     */
    @Min(0)
    private int        timeType;

    /**
     * 几天后截止
     */
    private int        afterDays;

    /**
     * 开始时间
     */
    private Date       startTime;

    /**
     * 结束时间
     */
    private Date       endTime;

    /**
     * 优惠券说明
     */
    private String     desc;

    public int getCouponType() {
        return couponType;
    }

    public void setCouponType(int couponType) {
        this.couponType = couponType;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public int getCouponValueType() {
        return couponValueType;
    }

    public void setCouponValueType(int couponValueType) {
        this.couponValueType = couponValueType;
    }

    public BigDecimal getCouponValue() {
        return couponValue;
    }

    public void setCouponValue(BigDecimal couponValue) {
        this.couponValue = couponValue;
    }

    public BigDecimal getOrderPriceLimit() {
        return orderPriceLimit;
    }

    public void setOrderPriceLimit(BigDecimal orderPriceLimit) {
        this.orderPriceLimit = orderPriceLimit;
    }

    public int getCouponCount() {
        return couponCount;
    }

    public void setCouponCount(int couponCount) {
        this.couponCount = couponCount;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getTimeType() {
        return timeType;
    }

    public void setTimeType(int timeType) {
        this.timeType = timeType;
    }

    public int getAfterDays() {
        return afterDays;
    }

    public void setAfterDays(int afterDays) {
        this.afterDays = afterDays;
    }

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getConditionType() {
        return conditionType;
    }

    public void setConditionType(int conditionType) {
        this.conditionType = conditionType;
    }
}
