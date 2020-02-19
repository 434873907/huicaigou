package com.chengzi.apiunion.procurement.admin.web.pojo.shop;

import com.chengzi.apiunion.common.module.image.annotation.ImageDecorater;
import com.chengzi.apiunion.common.module.image.enums.ImageBizType;
import com.chengzi.apiunion.common.module.image.util.ImageUtil;
import com.chengzi.apiunion.shop.pojo.ShopDO;
import com.chengzi.common.data.beans.JsonPojo;

/**
 * @author 月汐
 * @date 2018/11/15 20:21
 */
public class QueryShopBO extends JsonPojo {

    private Long id;

    /**
     * 店铺名
     */
    private String name;

    /**
     * 店铺logo
     */
    @ImageDecorater(ImageBizType.ADMIN_SHOP_LOGO)
    private String logoUrl;

    /**
     * 发货日期
     */
    private String deliverDays;

    /**
     * 到货日期
     */
    private String arrivalDays;

    /**
     * 店铺下商品数量
     */
    private int itemNumber;

    /**
     * 上架商品数量
     */
    private int onShelfItemNum;

    /**
     * 下架商品数量
     */
    private int offShelfItemNum;

    /**
     * 货币
     */
    private int currency;

    /**
     * 状态
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

    public int getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(int itemNumber) {
        this.itemNumber = itemNumber;
    }

    public int getOnShelfItemNum() {
        return onShelfItemNum;
    }

    public void setOnShelfItemNum(int onShelfItemNum) {
        this.onShelfItemNum = onShelfItemNum;
    }

    public int getOffShelfItemNum() {
        return offShelfItemNum;
    }

    public void setOffShelfItemNum(int offShelfItemNum) {
        this.offShelfItemNum = offShelfItemNum;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public static QueryShopBO convert(ShopDO shopDO, int itemNumber, int onShelfItemNum, int offShelfItemNum) {
        QueryShopBO bo = new QueryShopBO();
        bo.setId(shopDO.getId());
        bo.setName(shopDO.getName());
        bo.setLogoUrl(shopDO.getLogoUrl());
        bo.setDeliverDays(shopDO.getDeliverDays());
        bo.setArrivalDays(shopDO.getArrivalDays());
        bo.setStatus(shopDO.getStatus());
        bo.setCurrency(shopDO.getDefaultCurrency());
        bo.setItemNumber(itemNumber);
        bo.setOnShelfItemNum(onShelfItemNum);
        bo.setOffShelfItemNum(offShelfItemNum);
        return bo;
    }

}
