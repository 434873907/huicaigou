package com.chengzi.apiunion.procurement.admin.web.pojo.shop;

import com.chengzi.apiunion.common.module.image.annotation.ImageDecorater;
import com.chengzi.apiunion.common.module.image.enums.ImageBizType;
import com.chengzi.apiunion.common.module.image.util.ImageUtil;
import com.chengzi.apiunion.shop.pojo.ShopDO;
import com.chengzi.common.data.beans.JsonPojo;

/**
 * @author 月汐
 * @date 2018/11/15 17:13
 */
public class ShopBO extends JsonPojo {

    /**
     * 店铺ID
     */
    private Long id;

    /**
     * 店铺名
     */
    private String name;


    /**
     * logo链接地址
     */
    @ImageDecorater(ImageBizType.ADMIN_SHOP_LOGO)
    private String logoUrl;

    /**
     * 描述
     */
    private String desc;

    /**
     * 是否自营
     */
    private Integer isSelf;

    /**
     * 发货期限
     */
    private String deliverDays;

    /**
     * 到货期限
     */
    private String arrivalDays;

    /**
     * 默认货币单位
     * {@link com.chengzi.apiunion.common.data.enums.CurrencyEnum}
     */
    private Integer currency;

    /**
     * 启用状态
     */
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getIsSelf() {
        return isSelf;
    }

    public void setIsSelf(Integer isSelf) {
        this.isSelf = isSelf;
    }

    public String getDeliverDays() {
        return deliverDays;
    }

    public void setDeliverDays(String deliverDays) {
        this.deliverDays = deliverDays;
    }

    public String getArrivalDays() {
        return arrivalDays;
    }

    public void setArrivalDays(String arrivalDays) {
        this.arrivalDays = arrivalDays;
    }

    public Integer getCurrency() {
        return currency;
    }

    public void setCurrency(Integer currency) {
        this.currency = currency;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public static ShopBO convert(ShopDO shopDO) {
        ShopBO bo = new ShopBO();
        bo.setId(shopDO.getId());
        bo.setName(shopDO.getName());
        bo.setLogoUrl(shopDO.getLogoUrl());
        bo.setDesc(shopDO.getDesc());
        bo.setIsSelf(shopDO.getIsSelf());
        bo.setDeliverDays(shopDO.getDeliverDays());
        bo.setArrivalDays(shopDO.getArrivalDays());
        bo.setCurrency(shopDO.getDefaultCurrency());
        bo.setStatus(shopDO.getStatus());
        return bo;
    }

}
