package com.chengzi.apiunion.procurement.admin.web.formbean.order;

import com.chengzi.common.web.formbean.BaseForm;
import org.summercool.web.annotation.JsonField;

import java.util.List;

/**
 * @author 月汐
 * @date 2019/4/10 17:07
 */
public class GetDifferenceExpressFeeForm extends BaseForm {

    private long userId;

    private String orderNum;

    @JsonField
    private List<RefundOrderItem> refundOrderItemList;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public List<RefundOrderItem> getRefundOrderItemList() {
        return refundOrderItemList;
    }

    public void setRefundOrderItemList(List<RefundOrderItem> refundOrderItemList) {
        this.refundOrderItemList = refundOrderItemList;
    }

    public static class RefundOrderItem {

        private long orderItemId;

        private int refundNum;

        public long getOrderItemId() {
            return orderItemId;
        }

        public void setOrderItemId(long orderItemId) {
            this.orderItemId = orderItemId;
        }

        public int getRefundNum() {
            return refundNum;
        }

        public void setRefundNum(int refundNum) {
            this.refundNum = refundNum;
        }
    }

}
