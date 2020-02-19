package com.chengzi.apiunion.procurement.admin.web.pojo.coupon;

import com.chengzi.common.data.beans.JsonPojo;

import java.util.Date;

/**
 * <p>
 * </p>
 * User: 摩天
 * Date: 2018-10-19 14:44
 */
public class CouponConfigListBO extends JsonPojo {

    /**
     * 优惠券id
     */
    private long   id;

    /**
     * 优惠券名称
     */
    private String couponName;

    /**
     * 优惠券类型
     */
    private String couponType;

    /**
     * 优惠券数量
     */
    private int    couponCount;

    /**
     * 已领取数量
     */
    private int    sendCouponCount;

    /**
     * 已使用数量
     */
    private int    usedCouponCount;
    /**
     * 有效时间（需要根据实际情况进行拼接）
     * 领取后10天内有效
     * 2019-10-10 12:12:12至 2019-10-10 12:12:12
     */
    private String validateTime;
    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 优惠券状态
     * 需要根据时间判断 和 失效
     */
    private Integer couponStatus;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public int getCouponCount() {
        return couponCount;
    }

    public void setCouponCount(int couponCount) {
        this.couponCount = couponCount;
    }

    public int getSendCouponCount() {
        return sendCouponCount;
    }

    public void setSendCouponCount(int sendCouponCount) {
        this.sendCouponCount = sendCouponCount;
    }

    public int getUsedCouponCount() {
        return usedCouponCount;
    }

    public void setUsedCouponCount(int usedCouponCount) {
        this.usedCouponCount = usedCouponCount;
    }

    public String getValidateTime() {
        return validateTime;
    }

    public void setValidateTime(String validateTime) {
        this.validateTime = validateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCouponStatus() {
        return couponStatus;
    }

    public void setCouponStatus(Integer couponStatus) {
        this.couponStatus = couponStatus;
    }
}
