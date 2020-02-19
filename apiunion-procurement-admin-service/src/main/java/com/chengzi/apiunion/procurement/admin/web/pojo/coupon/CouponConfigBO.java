package com.chengzi.apiunion.procurement.admin.web.pojo.coupon;

import com.chengzi.apiunion.brand.pojo.BrandDO;
import com.chengzi.apiunion.item.pojo.cache.ItemCO;
import com.chengzi.apiunion.partnercategory.pojo.PartnerCategoryDO;
import com.chengzi.common.data.beans.JsonPojo;
import com.chengzi.common.util.CollectionUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * </p>
 * User: 摩天
 * Date: 2018-10-19 14:43
 */
public class CouponConfigBO extends JsonPojo {

    /**
     * 优惠券类型 0优惠券 1优惠码
     */
    private int                 couponType;

    /**
     * 优惠码
     */
    private String              couponCode;

    /**
     * 优惠券名称
     */
    private String              couponName;

    /**
     * 优惠券券值类型（0：金额，1：折扣）
     */
    private int                 couponValueType;

    /**
     * 优惠券券值
     */
    private BigDecimal          couponValue;

    /**
     * 满减额度
     */
    private BigDecimal          orderPriceLimit;

    /**
     * 总数量
     */
    private int                 couponCount;

    /**
     * 发出总数量
     */
    private int                 sendCouponCount;

    /**
     * 条件限制JSON
     * @see com.chengzi.apiunion.coupon.enums.CouponConditionEnum
     */
    private int                 conditionType;

    /**
     * 条件限制JSON
     */
    private List<ConditionInfo> condition;

    /**
     * 期限类型  0、几天后截止 1、开始日期到结束日期
     */
    private int                 timeType;

    /**
     * 几天后截止
     */
    private int                 afterDays;

    /**
     * 开始时间
     */
    private Date                startTime;

    /**
     * 结束时间
     */
    private Date                endTime;

    /**
     * 优惠券说明
     */
    private String              desc;

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

    public int getSendCouponCount() {
        return sendCouponCount;
    }

    public void setSendCouponCount(int sendCouponCount) {
        this.sendCouponCount = sendCouponCount;
    }

    public List<ConditionInfo> getCondition() {
        return condition;
    }

    public void setCondition(List<ConditionInfo> condition) {
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

    public static CouponConfigBO brandConverCondition(CouponConfigBO couponConfigBO, List<BrandDO> brandDOS) {
        List<ConditionInfo> conditionInfos = new ArrayList<>();
        if (!CollectionUtil.isEmpty(brandDOS)) {
            brandDOS.forEach(x -> {
                ConditionInfo conditionInfo = new ConditionInfo();
                conditionInfo.setId(x.getId());
                conditionInfo.setName(x.getBrandNameOrOriginalName());
                conditionInfos.add(conditionInfo);
            });
        }
        couponConfigBO.setCondition(conditionInfos);
        return couponConfigBO;
    }

    public static CouponConfigBO itemConverCondition(CouponConfigBO couponConfigBO, List<ItemCO> itemCOS) {
        List<ConditionInfo> conditionInfos = new ArrayList<>();
        if (!CollectionUtil.isEmpty(itemCOS)) {
            itemCOS.forEach(x -> {
                ConditionInfo conditionInfo = new ConditionInfo();
                conditionInfo.setId(x.getId());
                conditionInfo.setName(x.getName());
                conditionInfo.setMainImageUrl(x.getMainImageUrl());
                conditionInfos.add(conditionInfo);
            });
        }
        couponConfigBO.setCondition(conditionInfos);
        return couponConfigBO;
    }

    public static CouponConfigBO cateConverCondition(CouponConfigBO couponConfigBO, List<PartnerCategoryDO> categoryDOS) {
        List<ConditionInfo> conditionInfos = new ArrayList<>();
        if (!CollectionUtil.isEmpty(categoryDOS)) {
            categoryDOS.forEach(x -> {
                ConditionInfo conditionInfo = new ConditionInfo();
                conditionInfo.setId(x.getId());
                conditionInfo.setName(x.getName());
                conditionInfos.add(conditionInfo);
            });
        }
        couponConfigBO.setCondition(conditionInfos);
        return couponConfigBO;
    }
}

class ConditionInfo extends JsonPojo {
    private long   id;
    private String name;
    private String mainImageUrl;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public void setMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
    }
}
