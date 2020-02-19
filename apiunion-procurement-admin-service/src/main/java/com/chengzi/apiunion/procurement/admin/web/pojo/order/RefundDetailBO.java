package com.chengzi.apiunion.procurement.admin.web.pojo.order;

import com.alibaba.fastjson.JSONObject;
import com.chengzi.apiunion.common.data.enums.CurrencyEnum;
import com.chengzi.apiunion.common.module.currency.util.AmountUtil;
import com.chengzi.apiunion.order.enums.OrderRefundStatusEnum;
import com.chengzi.apiunion.order.pojo.OrderDO;
import com.chengzi.apiunion.order.pojo.OrderRefundDO;
import com.chengzi.apiunion.order.pojo.OrderRefundItemDO;
import com.chengzi.apiunion.order.pojo.snapshot.RefundItemSnapshot;
import com.chengzi.common.data.beans.JsonPojo;
import com.chengzi.common.util.DateUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 月汐
 * @date 2019/1/4 16:26
 */
public class RefundDetailBO extends JsonPojo {
    private String                           currency;

    private double                           payAmount;

    private double                           refundAmount;

    private double                           itemRefundAmount;

    private double                           extraRefundAmount;

    private List<RefundInfo>                 waitConfirmRefundList;

    private List<RefundInfo>                 completeRefundList;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(double payAmount) {
        this.payAmount = payAmount;
    }

    public double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public double getItemRefundAmount() {
        return itemRefundAmount;
    }

    public void setItemRefundAmount(double itemRefundAmount) {
        this.itemRefundAmount = itemRefundAmount;
    }

    public double getExtraRefundAmount() {
        return extraRefundAmount;
    }

    public void setExtraRefundAmount(double extraRefundAmount) {
        this.extraRefundAmount = extraRefundAmount;
    }

    public List<RefundInfo> getWaitConfirmRefundList() {
        return waitConfirmRefundList;
    }

    public void setWaitConfirmRefundList(List<RefundInfo> waitConfirmRefundList) {
        this.waitConfirmRefundList = waitConfirmRefundList;
    }

    public List<RefundInfo> getCompleteRefundList() {
        return completeRefundList;
    }

    public void setCompleteRefundList(List<RefundInfo> completeRefundList) {
        this.completeRefundList = completeRefundList;
    }

    public static RefundDetailBO convert(List<OrderRefundItemDO> orderRefundItemDOS,
                                         List<OrderRefundDO> orderRefundDOS, OrderDO order) {
        RefundDetailBO bo = new RefundDetailBO();

        double itemRefundAmount = 0.0;
        double extRefundAmount = 0.0;
        String currency = CurrencyEnum.getSymbol(order.getCurrency());

        Map<Long, List<OrderRefundItemDO>> refundItemsMap = new HashMap<>();

        for (OrderRefundItemDO refundItemDO : orderRefundItemDOS) {
            if (refundItemDO.getRefundStatus() == OrderRefundStatusEnum.REFUND_COMPLETE.getCode()) {
                if (refundItemDO.getOrderItemId() > 0) {
                    itemRefundAmount += refundItemDO.getRefundAmount().doubleValue();
                } else {
                    extRefundAmount += refundItemDO.getRefundAmount().doubleValue();
                }
            }
            List<OrderRefundItemDO> refundItemDOS = refundItemsMap.computeIfAbsent(refundItemDO.getOrderRefundId(), k -> new ArrayList<>());
            refundItemDOS.add(refundItemDO);
        }

        List<RefundInfo>                 waitConfirmRefundList = new ArrayList<>();
        List<RefundInfo>                 completeRefundList    = new ArrayList<>();

        bo.setCurrency(currency);
        bo.setPayAmount(order.getPayAmount());
        bo.setRefundAmount(AmountUtil.formatResultDecimal(BigDecimal.valueOf(itemRefundAmount + extRefundAmount)).doubleValue());
        bo.setItemRefundAmount(AmountUtil.formatResultDecimal(BigDecimal.valueOf(itemRefundAmount)).doubleValue());
        bo.setExtraRefundAmount(AmountUtil.formatResultDecimal(BigDecimal.valueOf(extRefundAmount)).doubleValue());
        bo.setWaitConfirmRefundList(waitConfirmRefundList);
        bo.setCompleteRefundList(completeRefundList);

        for (OrderRefundDO orderRefundDO : orderRefundDOS) {
            RefundInfo refundInfo = new RefundInfo();
            refundInfo.setCreateTime(DateUtil.formatDate(orderRefundDO.getCreateTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
            refundInfo.setRefundAmount(orderRefundDO.getRefundAmount().doubleValue());
            refundInfo.setRefundStatus(orderRefundDO.getRefundStatus());
            refundInfo.setOperator(orderRefundDO.getOperator());
            refundInfo.setId(orderRefundDO.getId());
            refundInfo.setCurrency(currency);

            double itemAmount = 0.0;

            List<OrderRefundItemDO> refundItemDOList = refundItemsMap.get(orderRefundDO.getId());
            List<RefundItemInfo> refundItemInfoList = new ArrayList<>();

            for (OrderRefundItemDO refundItemDO : refundItemDOList) {
                RefundItemInfo refundItemInfo = new RefundItemInfo();
                refundItemInfo.setRefundAmount(refundItemDO.getRefundAmount().doubleValue());
                refundItemInfo.setRefundItemId(refundItemDO.getId());

                if (refundItemDO.getOrderItemId() > 0) {
                    RefundItemSnapshot itemSnapshot = JSONObject.parseObject(refundItemDO.getItemSnapshot(), RefundItemSnapshot.class);
                    refundItemInfo.setItemAmount(itemSnapshot.getItemPrice().doubleValue() * refundItemDO.getRefundNum());
                    refundItemInfo.setItemName(itemSnapshot.getItemName());
                    refundItemInfo.setSupplierName(itemSnapshot.getSupplierName());
                    refundItemInfo.setRefundType("商品退款");
                    itemAmount = itemAmount + itemSnapshot.getItemPrice().doubleValue() * refundItemDO.getRefundNum();
                } else {
                    refundItemInfo.setItemAmount(0.0);
                    refundItemInfo.setRefundType("额外退款");
                    refundItemInfo.setRemark(refundItemDO.getRemark());
                }
                refundItemInfoList.add(refundItemInfo);
            }

            refundInfo.setItemAmount(itemAmount);
            refundInfo.setRefundItemInfos(refundItemInfoList);
            if (refundInfo.getRefundStatus() == OrderRefundStatusEnum.REFUND_APPLY.getCode()) {
                waitConfirmRefundList.add(refundInfo);
            } else {
                completeRefundList.add(refundInfo);
            }
        }
        return bo;
    }

    public static class RefundInfo {

        private long id;

        private String createTime;

        private String currency;

        private double itemAmount;

        private double refundAmount;

        private int    refundStatus;

        private String operator;

        private List<RefundItemInfo> refundItemInfos;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public double getItemAmount() {
            return itemAmount;
        }

        public void setItemAmount(double itemAmount) {
            this.itemAmount = itemAmount;
        }

        public double getRefundAmount() {
            return refundAmount;
        }

        public void setRefundAmount(double refundAmount) {
            this.refundAmount = refundAmount;
        }

        public int getRefundStatus() {
            return refundStatus;
        }

        public void setRefundStatus(int refundStatus) {
            this.refundStatus = refundStatus;
        }

        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }

        public List<RefundItemInfo> getRefundItemInfos() {
            return refundItemInfos;
        }

        public void setRefundItemInfos(List<RefundItemInfo> refundItemInfos) {
            this.refundItemInfos = refundItemInfos;
        }
    }

    public static class RefundItemInfo {

        private long       refundItemId;

        private String     itemName;

        private String     supplierName;

        private String     refundType;

        private double     itemAmount;

        private double     refundAmount;

        private String     remark;

        public long getRefundItemId() {
            return refundItemId;
        }

        public void setRefundItemId(long refundItemId) {
            this.refundItemId = refundItemId;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getSupplierName() {
            return supplierName;
        }

        public void setSupplierName(String supplierName) {
            this.supplierName = supplierName;
        }

        public String getRefundType() {
            return refundType;
        }

        public void setRefundType(String refundType) {
            this.refundType = refundType;
        }

        public double getItemAmount() {
            return itemAmount;
        }

        public void setItemAmount(double itemAmount) {
            this.itemAmount = itemAmount;
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
    }
}
