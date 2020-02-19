package com.chengzi.apiunion.procurement.admin.web.pojo.order;

import com.chengzi.apiunion.order.enums.OrderAddressVerifyStatusEnum;
import com.chengzi.common.data.beans.JsonPojo;

import java.util.Date;
import java.util.List;

/** 
* @author 苏子 
* @date 2018年10月17日
*/
public class OrderBO extends JsonPojo {
    /**
     * 订单号
     */
    private String        orderNum;

    /**
     * 用户定义订单号
     */
    private String        userOrderNum;

    /**
     * 商品金额
     */
    private double        totalAmount;

    /**
     * 支付金额
     */
    private double        payAmount;

    /**
     * 订单状态
     */
    private int           orderStatus;

    /**
     * 运费
     */
    private double        expAmount;

    /**
     * 优惠券抵用
     */
    private double        couponAmount;

    /**
     * 采购商
     */
    private String        buyer;

    /**
     * 采购商备注
     */
    private String        buyerRemark;

    /**
     * 商品总价
     */
    private double        itemAmount;

    /**
     * 下单日期
     */
    private String        createTime;

    /**
     * 支付信息
     */
    private List<PayInfo> payInfoList;

    /**
     * 地址验证状态
     * @see OrderAddressVerifyStatusEnum
     */
    private int           addressVerifyStatus;

    /**
     * 地址验证说明
     */
    private String        addressVerifyDesc;

    /**
     * 是否支持推单
     */
    private boolean       supportPushOrder = false;

    /**
     * 备注
     */
    private String        desc;

    /**
     * 订单创建平台
     * @see com.chengzi.common.data.enums.PlatformEnum
     */
    private String createPlatform;

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getUserOrderNum() {
        return userOrderNum;
    }

    public void setUserOrderNum(String userOrderNum) {
        this.userOrderNum = userOrderNum;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(double payAmount) {
        this.payAmount = payAmount;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public double getExpAmount() {
        return expAmount;
    }

    public void setExpAmount(double expAmount) {
        this.expAmount = expAmount;
    }

    public double getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(double couponAmount) {
        this.couponAmount = couponAmount;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public double getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(double itemAmount) {
        this.itemAmount = itemAmount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<PayInfo> getPayInfoList() {
        return payInfoList;
    }

    public String getBuyerRemark() {
        return buyerRemark;
    }

    public void setBuyerRemark(String buyerRemark) {
        this.buyerRemark = buyerRemark;
    }

    public void setPayInfoList(List<PayInfo> payInfoList) {
        this.payInfoList = payInfoList;
    }

    public int getAddressVerifyStatus() {
        return addressVerifyStatus;
    }

    public void setAddressVerifyStatus(int addressVerifyStatus) {
        this.addressVerifyStatus = addressVerifyStatus;
    }

    public String getAddressVerifyDesc() {
        return addressVerifyDesc;
    }

    public void setAddressVerifyDesc(String addressVerifyDesc) {
        this.addressVerifyDesc = addressVerifyDesc;
    }

    public boolean isSupportPushOrder() {
        return supportPushOrder;
    }

    public void setSupportPushOrder(boolean supportPushOrder) {
        this.supportPushOrder = supportPushOrder;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCreatePlatform() {
        return createPlatform;
    }

    public void setCreatePlatform(String createPlatform) {
        this.createPlatform = createPlatform;
    }

    public static class PayInfo {
        private String payAmount;
        private String payMethod;
        private String payTradeNo;
        private String payTradeAccount;
        private Date   payTime;

        public String getPayAmount() {
            return payAmount;
        }

        public void setPayAmount(String payAmount) {
            this.payAmount = payAmount;
        }

        public String getPayMethod() {
            return payMethod;
        }

        public void setPayMethod(String payMethod) {
            this.payMethod = payMethod;
        }

        public String getPayTradeNo() {
            return payTradeNo;
        }

        public void setPayTradeNo(String payTradeNo) {
            this.payTradeNo = payTradeNo;
        }

        public String getPayTradeAccount() {
            return payTradeAccount;
        }

        public void setPayTradeAccount(String payTradeAccount) {
            this.payTradeAccount = payTradeAccount;
        }

        public Date getPayTime() {
            return payTime;
        }

        public void setPayTime(Date payTime) {
            this.payTime = payTime;
        }
    }
}
