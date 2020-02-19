package com.chengzi.apiunion.procurement.admin.web.pojo.order;

import com.alibaba.fastjson.JSONObject;
import com.chengzi.apiunion.common.data.enums.CurrencyEnum;
import com.chengzi.apiunion.common.module.currency.util.AmountUtil;
import com.chengzi.apiunion.order.enums.OrderRefundStatusEnum;
import com.chengzi.apiunion.order.enums.OrderStatusEnum;
import com.chengzi.apiunion.order.enums.RefundReasonEnum;
import com.chengzi.apiunion.order.pojo.OrderCouponDO;
import com.chengzi.apiunion.order.pojo.OrderItemDO;
import com.chengzi.apiunion.order.pojo.OrderRefundItemDO;
import com.chengzi.apiunion.order.pojo.snapshot.FeeSnapshot;
import com.chengzi.apiunion.order.pojo.snapshot.ItemFeeSnapshot;
import com.chengzi.apiunion.order.pojo.snapshot.ItemSnapshot;
import com.chengzi.apiunion.order.pojo.snapshot.RefundItemSnapshot;
import com.chengzi.common.data.beans.JsonPojo;
import com.chengzi.common.util.EnumUtil;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author 月汐
 * @date 2018/12/28 17:58
 */
public class RefundFromOrderBO extends JsonPojo {

    private List<OrderItem> orderItemList;

    private List<RefundInfo> refundInfoList;

    private List<ExtraRefundInfo> extraRefundInfoList;

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public List<RefundInfo> getRefundInfoList() {
        return refundInfoList;
    }

    public void setRefundInfoList(List<RefundInfo> refundInfoList) {
        this.refundInfoList = refundInfoList;
    }

    public List<ExtraRefundInfo> getExtraRefundInfoList() {
        return extraRefundInfoList;
    }

    public void setExtraRefundInfoList(List<ExtraRefundInfo> extraRefundInfoList) {
        this.extraRefundInfoList = extraRefundInfoList;
    }

    public static RefundFromOrderBO convert(List<OrderItemDO> orderItemDOList, List<OrderRefundItemDO> orderRefundItemDOS, List<OrderCouponDO> orderCouponDOS) {
        RefundFromOrderBO bo = new RefundFromOrderBO();

        // 1、计算每种商品

        //获得商品的销售总金额 skuid
        Map<Long, BigDecimal> orderItemPriceMap = new HashMap<>();
        //记录每种商品购买数量 skuid
        Map<Long, Integer> orderItemBuynumMap = new HashMap<>();

        //记录每种商品优惠金额 skuid
        Map<Long, BigDecimal> orderItemDeduct = new HashMap<>();

        for (OrderItemDO orderItemDO : orderItemDOList) {
           int buyNum = orderItemDO.getBuyNum();
//            BigDecimal orderPriceSum = orderItemPriceMap.get(orderItemDO.getItemId());
            BigDecimal orderPriceSum = orderItemPriceMap.get(orderItemDO.getSkuId());
            if (orderPriceSum == null) {
                orderPriceSum = BigDecimal.ZERO;
//                orderItemPriceMap.put(orderItemDO.getSkuId(), orderPriceSum);
            }
//            FeeSnapshot feeSnapshot = JSONObject.parseObject(orderItemDO.getFeeSnapshot(), FeeSnapshot.class);
            ItemSnapshot itemSnapshot = JSONObject.parseObject(orderItemDO.getItemSnapshot(), ItemSnapshot.class);
            BigDecimal buyNumBigDecimal = new BigDecimal(buyNum);
            orderPriceSum = orderPriceSum.add( (itemSnapshot.getPrice().multiply(buyNumBigDecimal)) );
            orderItemPriceMap.put(orderItemDO.getSkuId(), orderPriceSum);
        }
        for (OrderItemDO orderItemDO : orderItemDOList) {
            ItemFeeSnapshot itemFeeSnapshot = JSONObject.parseObject(orderItemDO.getFeeSnapshot(), ItemFeeSnapshot.class);
            BigDecimal actDeduct = itemFeeSnapshot.getActDeduct();
            BigDecimal couponDeduct = itemFeeSnapshot.getCouponDeduct();
            //单个商品全部的优惠金额
            BigDecimal itemDeductTotal = actDeduct.add(couponDeduct);
//            orderItemDeduct.put(orderItemDO.getItemId(), itemDeductTotal);
            orderItemDeduct.put(orderItemDO.getSkuId(), itemDeductTotal);
        }

        //获得每个商品的真实总金额
        for (Long skuId : orderItemPriceMap.keySet()) {
           BigDecimal totalAmount = orderItemPriceMap.get(skuId);
           totalAmount = totalAmount.subtract(orderItemDeduct.get(skuId));
           if(totalAmount.compareTo(BigDecimal.ZERO) > 0) {
               //减掉优惠金额，重新赋值
               orderItemPriceMap.put(skuId, totalAmount);
           } else {
               orderItemPriceMap.put(skuId, new BigDecimal(0.01));
           }
        }

        for(OrderItemDO orderItemDO : orderItemDOList) {
            Integer itemSum = orderItemBuynumMap.get(orderItemDO.getSkuId());
            if (itemSum == null) {
                itemSum = 0;
            }
            itemSum += orderItemDO.getBuyNum();
            orderItemBuynumMap.put(orderItemDO.getSkuId(), itemSum);
        }
        // 2、退款列表
        List<RefundInfo> refundInfoList = new ArrayList<>();
        List<ExtraRefundInfo> extraRefundInfoList = new ArrayList<>();
        Map<Long, Integer> refundNumInApplyMap = new HashMap<>();

        for (OrderRefundItemDO orderRefundItemDO : orderRefundItemDOS) {
            if (orderRefundItemDO.getRefundReasonType() != 0) {
                if (orderRefundItemDO.getRefundStatus() == OrderRefundStatusEnum.REFUND_APPLY.getCode()) {
                    refundNumInApplyMap.put(orderRefundItemDO.getOrderItemId(), orderRefundItemDO.getRefundNum());
                }

                RefundInfo refundInfo = new RefundInfo();
                RefundItemSnapshot itemSnapshot = JSONObject.parseObject(orderRefundItemDO.getItemSnapshot(), RefundItemSnapshot.class);
                if (itemSnapshot!=null){
                    refundInfo.setMainImg(itemSnapshot.getMainImg());
                    refundInfo.setItemName(itemSnapshot.getItemName());
                    refundInfo.setSkuValue(itemSnapshot.getSkuInfo());
                    refundInfo.setSupplierId(itemSnapshot.getSupplierId());
                    refundInfo.setSupplierName(itemSnapshot.getSupplierName());
                    refundInfo.setItemPrice(itemSnapshot.getItemPrice());
                }
                refundInfo.setId(orderRefundItemDO.getId());
                refundInfo.setNum(orderRefundItemDO.getRefundNum());
                refundInfo.setCurrency(AmountUtil.getSettlementCurrency().getSymbol());
                refundInfo.setRefundType(orderRefundItemDO.getRefundReasonType());
                refundInfo.setRefundTypeName(EnumUtil.parse(RefundReasonEnum.class, orderRefundItemDO.getRefundReasonType()).getRemark());
                refundInfo.setRefundAmount(orderRefundItemDO.getRefundAmount());
                refundInfo.setRefundStatus(orderRefundItemDO.getRefundStatus());
                refundInfoList.add(refundInfo);
            } else {
                ExtraRefundInfo extraRefundInfo = new ExtraRefundInfo();
                extraRefundInfo.setId(orderRefundItemDO.getId());
                extraRefundInfo.setReceiveAccount(orderRefundItemDO.getReceiveAccount());
                extraRefundInfo.setReceivePlatform(orderRefundItemDO.getReceivePlatform());
                extraRefundInfo.setRefundAmount(orderRefundItemDO.getRefundAmount());
                extraRefundInfo.setReceiveUserName(orderRefundItemDO.getReceiveUserName());
                extraRefundInfo.setRemark(orderRefundItemDO.getRemark());
                extraRefundInfoList.add(extraRefundInfo);
            }
        }

        // 3、生成订单返回值列表
        //OrderItem 按照 pkgNo 进行聚合
        Map<String, List<RefundInfo>> pkgNoOrderItemMap = new HashMap<>();
        for (OrderItemDO orderItemDO : orderItemDOList) {
            if (orderItemDO.getStatus() == OrderStatusEnum.ORDER_STATUS_REFUND.getCode()) {
                continue;
            }
            Long skuId = orderItemDO.getSkuId();

            ItemSnapshot itemSnapshot = JSONObject.parseObject(orderItemDO.getItemSnapshot(), ItemSnapshot.class);
            List<RefundInfo> orderItems = pkgNoOrderItemMap.computeIfAbsent(orderItemDO.getPkgNo(), k -> new ArrayList<>());
            RefundInfo refundInfo = new RefundInfo();
            refundInfo.setId(orderItemDO.getId());
            refundInfo.setMainImg(itemSnapshot.getMainImg());
            refundInfo.setItemName(itemSnapshot.getItemName());
            refundInfo.setSkuValue(itemSnapshot.getSkuInfo());
//            if (refundNumInApplyMap.get(orderItemDO.getId()) != null) {
//                orderItem.setNum(orderItemDO.getBuyNum() - refundNumInApplyMap.get(orderItemDO.getId()));
//            } else {
            refundInfo.setNum(orderItemDO.getBuyNum());
//            }
            refundInfo.setSupplierId(itemSnapshot.getSupplierId());
            refundInfo.setSupplierName(itemSnapshot.getSupplierName());
            refundInfo.setCurrency(AmountUtil.getSettlementCurrency().getSymbol());
            refundInfo.setRefundStatus(-3);
            //特殊处理 0.01元的数据
            BigDecimal itemPayAmount = orderItemPriceMap.get(skuId);
            if(itemPayAmount.compareTo(new BigDecimal(0.01)) == 0) {
//                orderItem.setItemPrice(AmountUtil.formatDisplayDecimal(orderItemPriceMap.get(itemId).divide( new BigDecimal(orderItemBuynumMap.get(itemId)), BigDecimal.ROUND_HALF_UP  )));
                refundInfo.setItemPrice(new BigDecimal(0));
            } else {
                refundInfo.setItemPrice(AmountUtil.formatDisplayDecimal(orderItemPriceMap.get(skuId).divide( new BigDecimal(orderItemBuynumMap.get(skuId)), BigDecimal.ROUND_HALF_UP  )));
            }
            orderItems.add(refundInfo);
        }
        List<OrderItem> orderItemList = new ArrayList<>();
        //重新按照包裹号pkgNo，组装未退款的 OrderItem
        for (Map.Entry<String, List<RefundInfo>> entry : pkgNoOrderItemMap.entrySet()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setPkgNo(entry.getKey());
            orderItem.setPkgOrderItems(entry.getValue());
            orderItemList.add(orderItem);
        }
        bo.setOrderItemList(orderItemList);
        bo.setRefundInfoList(refundInfoList);
        bo.setExtraRefundInfoList(extraRefundInfoList);
        return bo;
    }

    static class OrderItem {

        private String pkgNo;

        private List<RefundInfo> pkgOrderItems;

        public String getPkgNo() {
            return pkgNo;
        }

        public void setPkgNo(String pkgNo) {
            this.pkgNo = pkgNo;
        }

        public List<RefundInfo> getPkgOrderItems() {
            return pkgOrderItems;
        }

        public void setPkgOrderItems(List<RefundInfo> pkgOrderItems) {
            this.pkgOrderItems = pkgOrderItems;
        }
    }

    static class RefundInfo {
        private long id;

        private String mainImg;

        private String itemName;

        private String skuValue;

        private int num;

        private long supplierId;

        private String supplierName;

        private String currency;

        private BigDecimal itemPrice;

        private int refundType;

        private String refundTypeName;

        private BigDecimal refundAmount;

        /**
         * @see OrderRefundStatusEnum
         */
        private int refundStatus;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getMainImg() {
            return mainImg;
        }

        public void setMainImg(String mainImg) {
            this.mainImg = mainImg;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getSkuValue() {
            return skuValue;
        }

        public void setSkuValue(String skuValue) {
            this.skuValue = skuValue;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public long getSupplierId() {
            return supplierId;
        }

        public void setSupplierId(long supplierId) {
            this.supplierId = supplierId;
        }

        public String getSupplierName() {
            return supplierName;
        }

        public void setSupplierName(String supplierName) {
            this.supplierName = supplierName;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public BigDecimal getItemPrice() {
            return itemPrice;
        }

        public void setItemPrice(BigDecimal itemPrice) {
            this.itemPrice = itemPrice;
        }

        public int getRefundType() {
            return refundType;
        }

        public void setRefundType(int refundType) {
            this.refundType = refundType;
        }

        public String getRefundTypeName() {
            return refundTypeName;
        }

        public void setRefundTypeName(String refundTypeName) {
            this.refundTypeName = refundTypeName;
        }

        public BigDecimal getRefundAmount() {
            return refundAmount;
        }

        public void setRefundAmount(BigDecimal refundAmount) {
            this.refundAmount = refundAmount;
        }

        public int getRefundStatus() {
            return refundStatus;
        }

        public void setRefundStatus(int refundStatus) {
            this.refundStatus = refundStatus;
        }
    }

    static class ExtraRefundInfo {

        private long id;

        private BigDecimal refundAmount;

        private int receivePlatform;

        private String receiveAccount;

        private String receiveUserName;

        private String remark;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public BigDecimal getRefundAmount() {
            return refundAmount;
        }

        public void setRefundAmount(BigDecimal refundAmount) {
            this.refundAmount = refundAmount;
        }

        public int getReceivePlatform() {
            return receivePlatform;
        }

        public void setReceivePlatform(int receivePlatform) {
            this.receivePlatform = receivePlatform;
        }

        public String getReceiveAccount() {
            return receiveAccount;
        }

        public void setReceiveAccount(String receiveAccount) {
            this.receiveAccount = receiveAccount;
        }

        public String getReceiveUserName() {
            return receiveUserName;
        }

        public void setReceiveUserName(String receiveUserName) {
            this.receiveUserName = receiveUserName;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }

}
