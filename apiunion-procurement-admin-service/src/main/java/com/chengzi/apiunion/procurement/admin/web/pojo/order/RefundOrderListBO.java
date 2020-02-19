package com.chengzi.apiunion.procurement.admin.web.pojo.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chengzi.apiunion.item.service.ItemChannelTypeService;
import com.chengzi.apiunion.order.pojo.OrderDO;
import com.chengzi.apiunion.order.pojo.OrderDeliveryInfo;
import com.chengzi.apiunion.order.pojo.OrderItemDO;
import com.chengzi.apiunion.order.pojo.snapshot.ItemSnapshot;
import com.chengzi.apiunion.user.pojo.UserDO;
import com.chengzi.common.data.beans.BaseDO;
import com.chengzi.common.data.enums.PlatformEnum;
import org.apache.commons.lang3.StringUtils;
import org.summercool.web.servlet.BeanFactory;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.String.join;

/**
 * <p>
 * </p>
 * User: 摩天
 * Date: 2019-02-12 17:16
 */
public class RefundOrderListBO extends OrderListBO {
    private int initiator;

    private int refundStatus;

    private long orderRefundId;

    public int getInitiator() {
        return initiator;
    }

    public void setInitiator(int initiator) {
        this.initiator = initiator;
    }

    public int getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(int refundStatus) {
        this.refundStatus = refundStatus;
    }

    public long getOrderRefundId() {
        return orderRefundId;
    }

    public void setOrderRefundId(long orderRefundId) {
        this.orderRefundId = orderRefundId;
    }

    public static RefundOrderListBO converter(OrderDO orderDO, UserDO userDO, List<OrderItemDO> orderItemDOS) {
        RefundOrderListBO refundOrderListBO = new RefundOrderListBO();
        refundOrderListBO.setBuyer(userDO.getNickName());
        OrderItemDO firstOrderItemDO = orderItemDOS.get(0);
        refundOrderListBO.setBuyerRemark(userDO.getRemark());

        ItemChannelTypeService itemChannelTypeService = BeanFactory.getBean(ItemChannelTypeService.class);

        int itemCount = 0;
        Set<String> supplierNames = new HashSet<>();
        Set<String> channelNames = new HashSet<>();
        BigDecimal channelPrice = BigDecimal.ZERO;
        for (OrderItemDO orderItemDO : orderItemDOS) {
            ItemSnapshot itemSnapshot = JSON.parseObject(firstOrderItemDO.getItemSnapshot(), ItemSnapshot.class);
            itemCount += orderItemDO.getBuyNum();
            String channelName = itemChannelTypeService.getChannelNameInCache(itemSnapshot.getChannelType());
            if (StringUtils.isNotEmpty(channelName)) {
                channelNames.add(channelName);
            }
            supplierNames.add(itemSnapshot.getSupplierName());
            channelPrice = channelPrice.add(itemSnapshot.getChannelPrice().multiply(BigDecimal.valueOf(orderItemDO.getBuyNum())));
        }
        refundOrderListBO.setItemCount(itemCount);
        refundOrderListBO.setChannel(join(",", channelNames));
        refundOrderListBO.setSupplier(join(",", supplierNames));
        refundOrderListBO.setPurchasePrice(channelPrice);
        OrderDeliveryInfo oderDeliveryInfo = JSONObject.parseObject(orderDO.getOrderDeliveryInfo(), OrderDeliveryInfo.class);
        refundOrderListBO.setCreateTime(orderDO.getCreateTime());
        refundOrderListBO.setOrderNum(orderDO.getOrderNum());
        refundOrderListBO.setReceiver(oderDeliveryInfo.getName());
        refundOrderListBO.setOrderStatus(orderDO.getStatus());
        refundOrderListBO.setUserId(orderDO.getUserId());
//        refundOrderListBO.setSupplyPrice(new BigDecimal(orderDO.getTotalAmount()));
        refundOrderListBO.setSupplyPrice( new BigDecimal(orderDO.getPayAmount()));
        refundOrderListBO.setIsApplyRefund(orderDO.getIsApplyRefund() == 1 && orderDO.getRefundAmount() == 0 ? 1 : 0);

        if (orderDO.getIsDeleted() == BaseDO.DELETED) {
            refundOrderListBO.setDesc("该订单已被用户删除");
        }
        PlatformEnum platformEnum = PlatformEnum.parse(orderDO.getCreatePlatform());
        if (platformEnum != null) {
            refundOrderListBO.setCreatePlatform(platformEnum.getName());
        }
        refundOrderListBO.setTagStatus(orderDO.getTagStatus());

        return refundOrderListBO;
    }
}
