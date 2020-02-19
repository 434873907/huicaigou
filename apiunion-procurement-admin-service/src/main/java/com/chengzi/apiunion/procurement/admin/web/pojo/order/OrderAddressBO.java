package com.chengzi.apiunion.procurement.admin.web.pojo.order;

import com.chengzi.apiunion.order.pojo.OrderDO;
import com.chengzi.apiunion.order.pojo.OrderDeliveryInfo;

/**
 * <p>
 * </p>
 * User: 摩天
 * Date: 2018-10-30 20:09
 */
public class OrderAddressBO {

    /**
     * 国家
     */
    private String country;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String district;

    /**
     * 地址1
     */
    private String address1;

    /**
     * 收货人姓名
     */
    private String receiver;

    /**
     * 收货地址
     */
    private String address;

    /**
     * 备注
     */
    private String remark;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 身份证号码
     */
    private String idCardNum;

    //是否是自提地址
    private boolean isSelfTake = false;


    /**
     * 自提点名称
     */
    private String selfTakeName;

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdCardNum() {
        return idCardNum;
    }

    public void setIdCardNum(String idCardNum) {
        this.idCardNum = idCardNum;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public boolean isSelfTake() {
        return isSelfTake;
    }

    public void setSelfTake(boolean selfTake) {
        isSelfTake = selfTake;
    }

    public String getSelfTakeName() {
        return selfTakeName;
    }

    public void setSelfTakeName(String selfTakeName) {
        this.selfTakeName = selfTakeName;
    }

    public static OrderAddressBO convert(OrderDO orderDO, OrderDeliveryInfo orderDeliveryInfo) {
        OrderAddressBO orderAddressInfo = new OrderAddressBO();
        orderAddressInfo.setCountry(orderDeliveryInfo.getCountry());
        orderAddressInfo.setProvince(orderDeliveryInfo.getProvince());
        orderAddressInfo.setCity(orderDeliveryInfo.getCity());
        orderAddressInfo.setDistrict(orderDeliveryInfo.getDistrict());
        orderAddressInfo.setAddress1(orderDeliveryInfo.getAddress1());
        orderAddressInfo
                .setAddress(orderDeliveryInfo.getProvince() + orderDeliveryInfo.getCity() + orderDeliveryInfo.getDistrict()
                        + orderDeliveryInfo.getAddress1());
        orderAddressInfo.setIdCardNum(orderDeliveryInfo.getIdcardNumber());
        orderAddressInfo.setPhone(orderDeliveryInfo.getPhone());
        orderAddressInfo.setReceiver(orderDeliveryInfo.getName());
        orderAddressInfo.setRemark(orderDO.getDesc());
        orderAddressInfo.setSelfTake(orderDeliveryInfo.isSelfTake());
        orderAddressInfo.setSelfTakeName(orderDeliveryInfo.getSelfTakeName());
        return orderAddressInfo;
    }
}
