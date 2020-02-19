package com.chengzi.apiunion.procurement.admin.web.formbean.order;

import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;
import com.chengzi.common.web.formbean.BaseForm;

/**
 * @author 月汐
 * @date 2019/1/22 15:01
 */
public class UpdateOrderAddressForm extends BaseForm {

    @NotBlankAndNull(message = "用户Id不能为空")
    protected Long userId;

    @NotBlankAndNull(message = "订单号不能为空")
    private String orderNum;

    @NotBlankAndNull(message = "请输入收件人名字")
    private String name;

    @NotBlankAndNull(message = "请输入收件人手机号")
    private String phoneNum;

    private String idCardNum;

    private String country = "中国";

    @NotBlankAndNull(message = "请选择省份")
    private String province;

    @NotBlankAndNull(message = "请选择市区")
    private String city;

    @NotBlankAndNull(message = "请选择区域")
    private String district;

    @NotBlankAndNull(message = "请输入详细地址")
    private String address1;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
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

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
