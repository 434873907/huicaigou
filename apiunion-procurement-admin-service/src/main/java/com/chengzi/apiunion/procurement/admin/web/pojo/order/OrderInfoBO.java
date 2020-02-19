package com.chengzi.apiunion.procurement.admin.web.pojo.order;

import com.chengzi.common.data.beans.JsonPojo;

import java.util.List;

/**
 * <p>
 * </p>
 * User: 摩天
 * Date: 2018-10-30 20:06
 */
public class OrderInfoBO extends JsonPojo {

    /**
     * 订单信息
     */
    private OrderBO           orderInfo;

    /**
     * 收货人信息
     */
    private OrderAddressBO    receiverInfo;

    /**
     * 原收货人信息
     */
    private OrderAddressBO    originalReceiverInfo;

    /**
     * 是否支持修改地址
     */
    private boolean           supportChangeAddress = true;

    /**
     * 商品信息
     */
    private List<OrderPkgBO> orderItemInfo;

    public OrderBO getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderBO orderInfo) {
        this.orderInfo = orderInfo;
    }

    public OrderAddressBO getReceiverInfo() {
        return receiverInfo;
    }

    public void setReceiverInfo(OrderAddressBO receiverInfo) {
        this.receiverInfo = receiverInfo;
    }

    public List<OrderPkgBO> getOrderItemInfo() {
        return orderItemInfo;
    }

    public void setOrderItemInfo(List<OrderPkgBO> orderItemInfo) {
        this.orderItemInfo = orderItemInfo;
    }

    public boolean isSupportChangeAddress() {
        return supportChangeAddress;
    }

    public void setSupportChangeAddress(boolean supportChangeAddress) {
        this.supportChangeAddress = supportChangeAddress;
    }

    public OrderAddressBO getOriginalReceiverInfo() {
        return originalReceiverInfo;
    }

    public void setOriginalReceiverInfo(OrderAddressBO originalReceiverInfo) {
        this.originalReceiverInfo = originalReceiverInfo;
    }
}
