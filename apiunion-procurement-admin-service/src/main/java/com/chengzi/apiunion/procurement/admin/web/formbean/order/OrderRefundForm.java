package com.chengzi.apiunion.procurement.admin.web.formbean.order;

import com.alibaba.fastjson.JSONArray;
import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;
import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;

import java.util.List;

/**
 * @author 月汐
 * @date 2018/12/25 11:45
 */
public class OrderRefundForm extends BaseForm {

    @NotBlankAndNull(message = "订单号不能为空")
    private String orderNum;

    @Min(value = 1, message = "请输入正确的用户编号")
    private long userId;

    @NotBlankAndNull(message = "退货退款内容不能为空")
    private String refundOrderItems;

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getRefundOrderItems() {
        return refundOrderItems;
    }

    public void setRefundOrderItems(String refundOrderItems) {
        this.refundOrderItems = refundOrderItems;
    }

    public static List<RefundOrderItem> parse(String json) {
        return JSONArray.parseArray(json, OrderRefundForm.RefundOrderItem.class);
    }

    public static class RefundOrderItem {

        private long orderItemId;

        private int refundReason;

        private int refundNum;

        private double refundAmount;

        private String remark;

        private String receiveUserName;

        private Integer receivePlatform;

        private String receiveAccount;

        public long getOrderItemId() {
            return orderItemId;
        }

        public void setOrderItemId(long orderItemId) {
            this.orderItemId = orderItemId;
        }

        public int getRefundReason() {
            return refundReason;
        }

        public void setRefundReason(int refundReason) {
            this.refundReason = refundReason;
        }

        public int getRefundNum() {
            return refundNum;
        }

        public void setRefundNum(int refundNum) {
            this.refundNum = refundNum;
        }

        public double getRefundAmount() {
            return refundAmount;
        }

        public void setRefundAmount(double refundAmount) {
            this.refundAmount = refundAmount;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getReceiveUserName() {
            return receiveUserName;
        }

        public void setReceiveUserName(String receiveUserName) {
            this.receiveUserName = receiveUserName;
        }

        public Integer getReceivePlatform() {
            return receivePlatform;
        }

        public void setReceivePlatform(Integer receivePlatform) {
            this.receivePlatform = receivePlatform;
        }

        public String getReceiveAccount() {
            return receiveAccount;
        }

        public void setReceiveAccount(String receiveAccount) {
            this.receiveAccount = receiveAccount;
        }
    }

}
