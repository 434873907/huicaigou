package com.chengzi.apiunion.procurement.admin.web.controllers.order;

import com.alibaba.fastjson.JSON;
import com.chengzi.apiunion.common.data.beans.SimpleRouteOperate;
import com.chengzi.apiunion.common.event.EventBusFactory;
import com.chengzi.apiunion.common.module.config.pojo.ExpressConfigDO;
import com.chengzi.apiunion.common.module.config.service.ExpressConfigService;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.express.enums.DeliveredEnum;
import com.chengzi.apiunion.express.enums.ExpressStatusEnum;
import com.chengzi.apiunion.express.pojo.OrderExpressDO;
import com.chengzi.apiunion.express.service.OrderExpressService;
import com.chengzi.apiunion.expresscompany.pojo.ExpressCompanyDO;
import com.chengzi.apiunion.expresscompany.service.ExpressCompanyService;
import com.chengzi.apiunion.order.dao.OrderItemMapper;
import com.chengzi.apiunion.order.dao.OrderMapper;
import com.chengzi.apiunion.order.enums.OrderOperateRecordEnum;
import com.chengzi.apiunion.order.enums.OrderStatusEnum;
import com.chengzi.apiunion.order.event.pojo.OrderOperateEvent;
import com.chengzi.apiunion.order.event.pojo.OrderUpdateEvent;
import com.chengzi.apiunion.order.event.pojo.TrackUpdateEvent;
import com.chengzi.apiunion.order.helper.OrderExpressHelper;
import com.chengzi.apiunion.order.pojo.OrderDO;
import com.chengzi.apiunion.order.pojo.OrderItemDO;
import com.chengzi.apiunion.order.service.OrderItemService;
import com.chengzi.apiunion.order.service.OrderService;
import com.chengzi.apiunion.procurement.admin.web.formbean.order.SyncOrderExpressForm;
//import com.chengzi.apiunion.thirdparty.express.enums.SpiderStrategyEnum;
//import com.chengzi.apiunion.thirdparty.express.pojo.ExpressSpiderBO;
//import com.chengzi.apiunion.thirdparty.express.spider.ExpressSpider;
import com.chengzi.client.util.MD5Util;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import com.chengzi.common.util.EnumUtil;
import com.chengzi.yuncang.common.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class SyncOrderExpressController extends AbstractApiController<SyncOrderExpressForm> {

    @Autowired
    private ExpressCompanyService expressCompanyService;

    @Autowired
    private ExpressConfigService expressConfigService;
    @Autowired
    private OrderExpressService orderExpressService;
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, SyncOrderExpressForm command) throws Exception {
        List<ExpressCompanyDO> expressCompanyList = expressCompanyService.queryExpressCompanyList();
        Map<Long, ExpressCompanyDO> expressCompanyMap = CollectionUtil.toMap(expressCompanyList, "id");

        List<ExpressConfigDO> expressConfigList = expressConfigService.selectAll();
        Map<Long, ExpressConfigDO> expressConfigMap = CollectionUtil.toMap(expressConfigList, "id");


        String orderNum = command.getOrderNum();
        Long userId = command.getUserId();

        Map<String, List<OrderExpressDO>> pkgOrderExpressMap =  OrderExpressHelper.pkgOrderExpressDOS(orderNum, userId);

            for (List<OrderExpressDO> orderExpressList : pkgOrderExpressMap.values()) {
                if (CollectionUtil.isEmpty(orderExpressList)) {
                    continue;
                }
                for (OrderExpressDO orderExpress : orderExpressList) {
                    if (orderExpress.IsDeliver()) {
                        //已投递的不用查物流
                        continue;
                    }
                    long expressCompanyId = orderExpress.getExpressCompany();
                    ExpressCompanyDO expressCompany = expressCompanyMap.get(expressCompanyId);
                    if (expressCompany == null) {
                        LOGGER.error("expressCompany is null !! id：" + expressCompanyId);
                        continue;
                    }
                    ExpressConfigDO expressConfig = expressConfigMap.get(expressCompany.getExpressConfigId());
                    if (expressConfig == null) {
                        LOGGER.error("expressConfig is null !! id：" + expressCompany.getExpressConfigId());
                        continue;
                    }
//                    String expressCompanyName = expressConfig.getCompanyName();
//                    String expressNum = orderExpress.getExpressNum();
                    try {
//                        SpiderStrategyEnum spiderStrategyEnum = EnumUtil.parse(SpiderStrategyEnum.class, expressConfig.getSpiderStrategy());
//                        ExpressSpider expressSpider = spiderStrategyEnum.getSpiderClass().newInstance();
//                        Result<ExpressSpiderBO> res = expressSpider.expressSpider(orderExpress, expressConfig.getSpiderStrategyParam());
//                        if (!res.isSuccess()) {
//                            LOGGER.error( StringUtil.buildStatLog("expressSpider is failed", res, expressCompanyId));
////                            continue;
//
//                            return Result.buildOpFailedResult("同步失败");
//                        }
////                        LOGGER.warn(
////                                StringUtil.buildStatLog("expressSpider ok", res, expressCompanyName, expressCompanyId, expressNum));
//                        ExpressSpiderBO expressSpiderBO = res.getData();
//                        if (expressSpiderBO == null || (StringUtils.isBlank(expressSpiderBO.getExpressNum())
//                                && expressConfig.isHide())) {
////                            LOGGER.warn(
////                                    StringUtil.buildStatLog("尚未发货", res, expressCompanyName, expressCompanyId, expressNum));
//                            continue;
//                        }
//                        LOGGER.info("同步轨迹数据为:" + JSON.toJSONString(expressSpiderBO));

                        //快递信息修改
//                        if (!StringUtils.equals(orderExpress.getRealExpressCompanyName(), expressSpiderBO.getExpressCompanyName())
//                                || !StringUtils.equals(orderExpress.getRealExpressNum(), expressSpiderBO.getExpressNum())) {
//                            orderExpress.setRealExpressNum(expressSpiderBO.getExpressNum());
//                            orderExpress.setRealExpressCompanyName(expressSpiderBO.getExpressCompanyName());
//                            orderExpressService.updateRealExpress(orderExpress);
//                        }

//                        OrderDO order = orderService.getOrderByOrderNum(orderExpress.getUserId(), orderExpress.getOrderNum());
                        OrderDO orderDO = orderService.getOrderByOrderNum(userId, orderNum);
//                        List<OrderItemDO> orderItemList = orderItemMapper.selectByOrderNum(SimpleRouteOperate.of(orderExpress.getOrderNum()),
//                                orderExpress.getUserId());
                        List<OrderItemDO> orderItemList = orderItemService.queryByOrderNum(userId, orderNum);

                        boolean isUpdateOrderStatus = false;
                        for (OrderItemDO orderItem : orderItemList) {
                            if (orderItem.getPkgNo().equals(orderExpress.getPkgNo())) {//包裹有物流轨迹
                                if (orderItem.getStatus() == OrderStatusEnum.ORDER_STATUS_WAIT_SEND.getCode()) {
                                    orderItem.setStatus(OrderStatusEnum.ORDER_STATUS_WAIT_CONFIRM.getCode());
                                    orderItem.setExpressStatus(ExpressStatusEnum.SHIPPED.getCode());
                                    orderItem.setDeliveryTime(new Date());
                                    orderItem.setExpressId(orderExpress.getId());
                                    if (orderItemMapper.updateOrderItemStatusByOrderNum(orderItem) <= 0) {
                                        LOGGER.error("更新包裹状态失败", orderItem.getId());
                                        isUpdateOrderStatus = false;
                                    } else {
                                        isUpdateOrderStatus = true;
//                                        EventBusFactory.getSyncEventBus().post(new OrderUpdateEvent(order.getOrderNum(), order.getUserId()));
                                        EventBusFactory.getSyncEventBus().post(new OrderUpdateEvent(orderNum, userId));

                                        EventBusFactory.getSyncEventBus()
                                                .post(new TrackUpdateEvent(orderExpress.getOrderNum(), orderExpress.getUserId()));
                                        buildOrderOperatePkgWaitConfirm(orderExpress.getOrderNum(), orderExpress.getUserId(), orderItem.getPkgNo(),
                                                orderExpress.getRealExpressNum(), orderExpress.getRealExpressCompanyName());
                                    }
                                }
                            }
                        }
                        if (isUpdateOrderStatus && orderDO.getStatus() != OrderStatusEnum.ORDER_STATUS_COMPLETE.getCode()) {
                            OrderDO orderDO_new = new OrderDO();
                            orderDO_new.setUserId(orderExpress.getUserId());
                            orderDO_new.setStatus(OrderStatusEnum.ORDER_STATUS_WAIT_CONFIRM.getCode());
                            orderDO_new.setOrderNum(orderExpress.getOrderNum());
//                            if (orderMapper.updateStatusByOrderNum(orderDO_new) != 1) {
//                                LOGGER.error("更新订单状态失败", orderDO.getId());
//                            } else {
//                                EventBusFactory.getAsyncEventBus()
//                                        .post(new OrderUpdateEvent(orderExpress.getOrderNum(), orderExpress.getUserId()));
//                            }
                        }
//                        if (StringUtils.equalsIgnoreCase(orderExpress.getDetail(), expressSpiderBO.getExpressDetail())
//                                && (getDeliver(expressSpiderBO.getExpressDetail()) == orderExpress.getDelivered())) {
//                            LOGGER.error("物流轨迹无更新 id：" + orderExpress.getId() + " oldDetail：" + orderExpress.getDetail()
//                                    + "&" + orderExpress.getPacketExpressDetail() + " newDetail："
//                                    + expressSpiderBO.getExpressDetail() + "&" + expressSpiderBO.getPacketExpressDetail());
//                            continue;
//                        }
//                        String oldDetail = orderExpress.getDetail();
//                        orderExpress.setDetail(expressSpiderBO.getExpressDetail());
//                        int row = orderExpressService.updateDetail(orderExpress);
//                        if (row <= 0) {
//                            LOGGER.error("无物流更新成功 id：" + orderExpress.getId() + " oldDetail："
//                                    + orderExpress.getDetail() + " newDetail：" + expressSpiderBO.getExpressDetail());
//                        } else if (!StringUtils.equalsIgnoreCase(MD5Util.getMD5Format(oldDetail),
//                                MD5Util.getMD5Format(expressSpiderBO.getExpressDetail()))
//                                || (getDeliver(expressSpiderBO.getExpressDetail()) != orderExpress.getDelivered())) {
//                            int expressStatus = getExpressStatus(orderExpress.getDetail());
//                            updateExpressStatus(orderExpress, expressStatus);
//
//                            // 代表轨迹发生变化
//                            EventBusFactory.getSyncEventBus()
//                                    .post(new TrackUpdateEvent(orderExpress.getOrderNum(), orderExpress.getUserId()));
//                            if (expressStatus == ExpressStatusEnum.SIGNED.getCode()) {
//                                // 查询是否所有订单都完成推送了
//                                // asyncOrderStatus(orderExpress, order);
//                            }
//                        }
                    } catch (Exception e) {
                        LOGGER.error("处理物流轨迹异常", e);
                    }

                }
            }
//        }
        return Result.buildSuccessMsg("同步成功");
    }


    private void buildOrderOperatePkgWaitConfirm(String orderNum, Long userId, String pkgNo, String expressNum, String companyName) {

        OrderOperateEvent event = new OrderOperateEvent(orderNum, userId, OrderOperateRecordEnum.PKG_SEND_EXPRESS);

        OrderOperateEvent.OperateParam operateParam = new OrderOperateEvent.OperateParam();
        operateParam.setPkgNo(pkgNo);
        operateParam.setExpressCompanyName(companyName);
        operateParam.setExpressNum(expressNum);
        event.setOperateParam(operateParam);

        EventBusFactory.getAsyncEventBus().post(event);
    }

    private void updateExpressStatus(OrderExpressDO orderExpress, int expressStatus) {
        OrderItemDO orderItem = new OrderItemDO();
        orderItem.setExpressStatus(expressStatus);
        orderItem.setPkgNo(orderExpress.getPkgNo());
        orderItem.setRouteId(orderExpress.getRouteId());
        if (expressStatus == ExpressStatusEnum.SIGNED.getCode()) {
            orderItem.setStatus(OrderStatusEnum.ORDER_STATUS_COMPLETE.getCode());
//            orderItem.setSignTime(getSginTime(orderExpress.getDetail()));
        }
        orderItemMapper.updateOrderItemStatusByPkgNo(orderItem);
        if (expressStatus == ExpressStatusEnum.SIGNED.getCode()) {
            orderExpress.setDelivered(DeliveredEnum.DELIVERED.getCode());
            orderExpressService.updateDelivered(orderExpress);
        }

    }
}
