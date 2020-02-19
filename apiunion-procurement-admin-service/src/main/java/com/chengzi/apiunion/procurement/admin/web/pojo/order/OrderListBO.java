package com.chengzi.apiunion.procurement.admin.web.pojo.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chengzi.apiunion.item.service.ItemChannelTypeService;
import com.chengzi.apiunion.order.enums.OrderAbnormalTypeEnum;
import com.chengzi.apiunion.order.enums.OrderPkgStatusEnum;
import com.chengzi.apiunion.order.enums.OrderStatusEnum;
import com.chengzi.apiunion.order.pojo.OrderAbnormalDO;
import com.chengzi.apiunion.order.pojo.OrderDO;
import com.chengzi.apiunion.order.pojo.OrderDeliveryInfo;
import com.chengzi.apiunion.order.pojo.OrderItemDO;
import com.chengzi.apiunion.order.pojo.snapshot.ItemSnapshot;
import com.chengzi.apiunion.user.pojo.UserDO;
import com.chengzi.common.data.beans.BaseDO;
import com.chengzi.common.data.beans.JsonPojo;
import com.chengzi.common.data.enums.PlatformEnum;
import com.chengzi.common.util.CollectionUtil;
import com.chengzi.common.util.EnumUtil;
import org.apache.commons.lang3.StringUtils;
import org.summercool.web.servlet.BeanFactory;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.lang.String.join;

/**
 * <p>
 * </p>
 * User: 摩天
 * Date: 2018-10-24 16:14
 */
public class OrderListBO extends JsonPojo implements Comparable<OrderListBO>{

    /**
     * 订单号
     */
    private String     orderNum;

    /**
     * 采购商ID
     */
    private long       userId;

    /**
     * 采购商
     */
    private String     buyer;

    /**
     * 采购商备注
     */
    private String     buyerRemark;

    /**
     * 渠道
     */
    private String     channel;

    /**
     * 供应商
     */
    private String     supplier;

    /**
     * 供应价
     */
    private BigDecimal supplyPrice;

    /**
     * 采购价
     */
    private BigDecimal purchasePrice;

    /**
     * 商品数
     */
    private int        itemCount;

    /**
     * 收货人
     */
    private String     receiver;

    /**
     * 下单时间
     */
    private Date       createTime;

    /**
     * 订单状态
     */
    private int        orderStatus;

    /**
     * 是否申请退款，0:是，1：否
     */
    private int        isApplyRefund;

    /**
     * 是否因退款关闭
     */
    private boolean    isClosedByRefund;

    private String     abnormals;

    /**
     * 用户订单号
     */
    private String     userOrderNum;

    private boolean    supportPushOrder;

    private String     desc;

    /**
     * 订单创建平台
     */
    private String createPlatform;
    /**
     * 订单标记状态
     */
    private Integer tagStatus;

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public BigDecimal getSupplyPrice() {
        return supplyPrice;
    }

    public void setSupplyPrice(BigDecimal supplyPrice) {
        this.supplyPrice = supplyPrice;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getIsApplyRefund() {
        return isApplyRefund;
    }

    public void setIsApplyRefund(int isApplyRefund) {
        this.isApplyRefund = isApplyRefund;
    }

    public boolean isClosedByRefund() {
        return isClosedByRefund;
    }

    public void setClosedByRefund(boolean closedByRefund) {
        isClosedByRefund = closedByRefund;
    }

    public String getAbnormals() {
        return abnormals;
    }

    public void setAbnormals(String abnormals) {
        this.abnormals = abnormals;
    }

    public String getBuyerRemark() {
        return buyerRemark;
    }

    public void setBuyerRemark(String buyerRemark) {
        this.buyerRemark = buyerRemark;
    }

    public String getUserOrderNum() {
        return userOrderNum;
    }

    public void setUserOrderNum(String userOrderNum) {
        this.userOrderNum = userOrderNum;
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

    public Integer getTagStatus() {
        return tagStatus;
    }

    public void setTagStatus(Integer tagStatus) {
        this.tagStatus = tagStatus;
    }

    @Override
    public int compareTo(OrderListBO o) {
        if (this.createTime.before(o.getCreateTime())){
            return 1;
        }else if (this.createTime.after(o.getCreateTime())){
            return -1;
        }
        return 0;
    }

    public static OrderListBO converter(OrderDO orderDO, UserDO userDO, List<OrderItemDO> orderItemDOS, List<OrderAbnormalDO> orderAbnormals) {
        OrderListBO orderListBO = new OrderListBO();
        orderListBO.setBuyer(userDO.getNickName());
        orderListBO.setBuyerRemark(userDO.getRemark());

        ItemChannelTypeService itemChannelTypeService = BeanFactory.getBean(ItemChannelTypeService.class);

        int itemCount = 0;
        Set<String> supplierNames = new HashSet<>();
        Set<String> channelNames = new HashSet<>();
        BigDecimal channelPrice = BigDecimal.ZERO;
        boolean supportPushOrder = false;
        for (OrderItemDO orderItemDO : orderItemDOS) {
            ItemSnapshot itemSnapshot = JSON.parseObject(orderItemDO.getItemSnapshot(), ItemSnapshot.class);
            if (itemSnapshot.getThirdAttr() != null && orderItemDO.getPkgStatus() != OrderPkgStatusEnum.ORDERED.getCode()) {
                supportPushOrder = true;
            }
            itemCount += orderItemDO.getBuyNum();
            String channelName = itemChannelTypeService.getChannelNameInCache(itemSnapshot.getChannelType());
            if (StringUtils.isNotEmpty(channelName)) {
                channelNames.add(channelName);
            }
            supplierNames.add(itemSnapshot.getSupplierName());
            channelPrice = channelPrice.add(itemSnapshot.getChannelPrice().multiply(BigDecimal.valueOf(orderItemDO.getBuyNum())));
        }
        orderListBO.setItemCount(itemCount);
        orderListBO.setChannel(join(",", channelNames));
        orderListBO.setSupplier(join(",", supplierNames));
        orderListBO.setPurchasePrice(channelPrice);
        OrderDeliveryInfo oderDeliveryInfo = JSONObject.parseObject(orderDO.getOrderDeliveryInfo(), OrderDeliveryInfo.class);
        orderListBO.setCreateTime(orderDO.getCreateTime());
        orderListBO.setOrderNum(orderDO.getOrderNum());
        orderListBO.setReceiver(oderDeliveryInfo.getName());
        orderListBO.setOrderStatus(orderDO.getStatus());
        orderListBO.setUserId(orderDO.getUserId());
//        orderListBO.setSupplyPrice(new BigDecimal(orderDO.getTotalAmount()));
        orderListBO.setSupplyPrice(new BigDecimal(orderDO.getPayAmount()));
        orderListBO.setIsApplyRefund(orderDO.getIsApplyRefund());
        orderListBO.setAbnormals(genAbnormals(orderAbnormals));
        orderListBO.setUserOrderNum(orderDO.getUserOrderNum());
        orderListBO.setSupportPushOrder(supportPushOrder);

        PlatformEnum platformEnum = PlatformEnum.parse(orderDO.getCreatePlatform());
        if (platformEnum != null) {
            orderListBO.setCreatePlatform(platformEnum.getName());
        }
        orderListBO.setTagStatus(orderDO.getTagStatus());
        if (orderDO.getStatus() == OrderStatusEnum.ORDER_STATUS_CLOSED.getCode()) {
            if (orderDO.getRefundAmount() > 0) {
                orderListBO.setClosedByRefund(true);
            } else {
                orderListBO.setClosedByRefund(false);
            }
        }

        if (orderDO.getIsDeleted() == BaseDO.DELETED) {
            orderListBO.setDesc("该订单已被用户删除");
        }

        return orderListBO;
    }

    private static String genAbnormals(List<OrderAbnormalDO> orderAbnormals) {
        if (CollectionUtil.isEmpty(orderAbnormals)) {
            return "";
        }

        Set<Integer> abnormals = new HashSet<>();

        StringBuilder builder = new StringBuilder();
        for (OrderAbnormalDO orderAbnormal : orderAbnormals) {
            if (!abnormals.contains(orderAbnormal.getType())) {
                builder.append(EnumUtil.parse(OrderAbnormalTypeEnum.class, orderAbnormal.getType()).getName()).append(",");
                abnormals.add(orderAbnormal.getType());
            }
        }

        return builder.deleteCharAt(builder.length() - 1).toString();
    }
}
