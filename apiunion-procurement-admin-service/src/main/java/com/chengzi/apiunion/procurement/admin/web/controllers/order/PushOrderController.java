package com.chengzi.apiunion.procurement.admin.web.controllers.order;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.data.rocketmq.RocketMQProducer;
import com.chengzi.apiunion.common.data.rocketmq.pojo.RocketMQRouteMsg;
import com.chengzi.apiunion.common.event.EventBusFactory;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.enums.ThirdSyncItemTypeEnum;
import com.chengzi.apiunion.order.enums.OrderOperateRecordEnum;
import com.chengzi.apiunion.order.enums.OrderPkgStatusEnum;
import com.chengzi.apiunion.order.event.pojo.OrderOperateEvent;
import com.chengzi.apiunion.order.pojo.OrderDO;
import com.chengzi.apiunion.order.pojo.OrderItemDO;
import com.chengzi.apiunion.order.pojo.snapshot.ItemSnapshot;
import com.chengzi.apiunion.order.service.OrderService;
import com.chengzi.apiunion.procurement.admin.partneruser.pojo.PartnerUserDO;
import com.chengzi.apiunion.procurement.admin.partneruser.service.PartnerUserService;
import com.chengzi.apiunion.procurement.admin.web.formbean.order.PushOrderForm;
import com.chengzi.apiunion.thirdparty.order.event.ThirdOrderPushEvent;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import com.chengzi.common.util.EnumUtil;
import com.chengzi.yuncang.common.util.StringUtil;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author 月汐
 * @date 2019/11/15 13:33
 */
public class PushOrderController extends AbstractApiController<PushOrderForm> {

    private static final int[]        API_TYPE_ARR      = new int[] {
            ThirdSyncItemTypeEnum.SUPER_EXP.getCode(),
            ThirdSyncItemTypeEnum.SUPER_EXP_STORAGE.getCode(),
            ThirdSyncItemTypeEnum.SHEPI.getCode(),
            ThirdSyncItemTypeEnum.TIANMA.getCode(),
            ThirdSyncItemTypeEnum.KAOLA.getCode(),
            ThirdSyncItemTypeEnum.KELIN.getCode()
    };

    @Autowired
    private PartnerUserService partnerUserService;
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, PushOrderForm command) throws Exception {
        long userId = command.getUserId();
        String orderNum = command.getOrderNum();
        long routeId = RequestContext.getRouteId();

        RocketMQProducer thirdOrderSyncMQProducer = BeanFactory.getBean("thirdOrderSyncMQProducer", RocketMQProducer.class);
        OrderService orderService = BeanFactory.getBean(OrderService.class);

        OrderDO orderDO = orderService.getOrderByOrderNum(userId, orderNum);
        if (orderDO == null) {
            LOGGER.warn(StringUtil.buildStatLog("order not found", routeId, userId, orderNum));
            return Result.buildIllegalArgumentResult("该订单不存在");
        }
        List<OrderItemDO> orderItemList = orderService.queryByOrderNum(userId, orderNum);
        if (CollectionUtil.isEmpty(orderItemList)) {
            LOGGER.warn(StringUtil.buildStatLog("order item not found", routeId, userId, orderNum));
            return Result.buildIllegalArgumentResult("该订单不存在");
        }
        // 按包裹组装对象
        Map<String, List<OrderItemDO>> pkgMap = new HashMap<>();
        Set<String> pkgNoQueue = new HashSet<>();
        for (OrderItemDO orderItemDO : orderItemList) {
            ItemSnapshot snapshot = ItemSnapshot.parse(orderItemDO.getItemSnapshot());
            if (snapshot.getThirdAttr() != null) {
                boolean supportPush = false;
                for (int apiType : API_TYPE_ARR) {
                    if (snapshot.getThirdAttr().getApiType() == apiType) {
                        supportPush = true;
                        break;
                    }
                }
                if (supportPush) {
                    List<OrderItemDO> pkgItems = pkgMap.get(orderItemDO.getPkgNo());
                    if (pkgItems == null) {
                        pkgMap.put(orderItemDO.getPkgNo(), pkgItems = new ArrayList<>());
                    }
                    pkgItems.add(orderItemDO);
                    // 判断状态，状态为未推送和推送失败的允许重推
                    if (EnumUtil.isMatch(orderItemDO.getPkgStatus(), OrderPkgStatusEnum.DEFAULT, OrderPkgStatusEnum.ORDER_FAILED)) {
                        pkgNoQueue.add(orderItemDO.getPkgNo());
                    }
                }
            }
        }
        //
        List<Message> mqMsgs = new ArrayList<>();
        for (String pkgNo : pkgNoQueue) {
            List<OrderItemDO> list = pkgMap.get(pkgNo);
            List<Long> orderItemIds = CollectionUtil.getFieldValueList(list, "id");
            mqMsgs.add(new Message(null, null, RocketMQRouteMsg.KRYO.serialize(new ThirdOrderPushEvent(userId, orderItemIds))));
            LOGGER.warn(StringUtil.buildStatLog("SendMQMsg", "THIRD-ORDER", userId, orderNum, pkgNo));
        }
        if (!mqMsgs.isEmpty()) {
            thirdOrderSyncMQProducer.send(mqMsgs);
        }
        //记录订单操作
        for (String pkgNo : pkgNoQueue) {
            List<OrderItemDO> orderItemDOS = pkgMap.get(pkgNo);
            int apiType = 0;
            if(orderItemDOS!= null && orderItemDOS.size() > 0) {
                OrderItemDO orderItemDO = orderItemDOS.get(0);
                ItemSnapshot snapshot = ItemSnapshot.parse(orderItemDO.getItemSnapshot());
                if (snapshot.getThirdAttr() != null) {
                    apiType = snapshot.getThirdAttr().getApiType();
                }
            }
            buildPushOrderOperateEvent(orderNum, userId, pkgNo, apiType);
        }

        return Result.buildSuccessResult("推单成功，请耐心等待结果！");
    }

    private void buildPushOrderOperateEvent(String orderNum, Long userId, String pkgNo, int apiType) {
        OrderOperateEvent event = new OrderOperateEvent(orderNum, userId, OrderOperateRecordEnum.PUSH_ORDER_HUMAN);
        OrderOperateEvent.OperateParam operateParam = new OrderOperateEvent.OperateParam();
        long operatorId = RequestContext.getUserId();
        PartnerUserDO partnerUserDO = partnerUserService.getUserById(operatorId);
        operateParam.setPkgNo(pkgNo);
        operateParam.setApiType(apiType);
        operateParam.setOperator(partnerUserDO.getNickName());
        event.setOperateParam(operateParam);
        EventBusFactory.getAsyncEventBus().post(event);
    }
}
