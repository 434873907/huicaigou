package com.chengzi.apiunion.procurement.admin.web.controllers.run;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.chengzi.apiunion.common.event.EventBusFactory;
import com.chengzi.apiunion.common.web.controllers.AbstractManageController;
import com.chengzi.apiunion.express.dao.OrderExpressMapper;
import com.chengzi.apiunion.express.enums.ExpressStatusEnum;
import com.chengzi.apiunion.express.pojo.ExpressPageQuery;
import com.chengzi.apiunion.express.pojo.OrderExpressDO;
import com.chengzi.apiunion.order.dao.OrderItemMapper;
import com.chengzi.apiunion.order.enums.OrderStatusEnum;
import com.chengzi.apiunion.order.event.pojo.OrderUpdateEvent;
import com.chengzi.apiunion.order.pojo.OrderItemDO;
import com.chengzi.common.data.enums.BooleanStatusEnum;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import com.chengzi.common.util.DateUtil;
import com.chengzi.common.web.formbean.EmptyForm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 行者
 */
public class FixOrderSignTimeController extends AbstractManageController<EmptyForm> {

    @Autowired
    private OrderExpressMapper orderExpressMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    private static final int              BATCH_NUM             = 500;
    @Override
    protected Result<?> doInnerBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {

        ExpressPageQuery query = new ExpressPageQuery();
        query.setDelivered(BooleanStatusEnum.YES.getCode());
        query.setLimit(BATCH_NUM);
        query.setOffset(0);
        query.setUserId(1);
        List<OrderExpressDO> allOrderExpressDOList = new ArrayList<>();
        while (true) {
            List<OrderExpressDO> orderExpressDOList = orderExpressMapper.selectByQuery(query);
            allOrderExpressDOList.addAll(orderExpressDOList);
            if (orderExpressDOList.size() < BATCH_NUM) {
                break;
            }
            query.setOffset(query.getOffset() + BATCH_NUM);
        }
        if (allOrderExpressDOList.isEmpty()) {
            return Result.buildOpFailedResult("没有要处理的数据");
        }

        Map<String, List<OrderExpressDO>> pkgNoMap = CollectionUtil.toListMap(allOrderExpressDOList, "pkgNo");
        for (Map.Entry<String, List<OrderExpressDO>> entry : pkgNoMap.entrySet()) {
            List<OrderExpressDO> expressDOList = entry.getValue();
            for (OrderExpressDO orderExpressDO : expressDOList) {
                String detail = orderExpressDO.getDetail();
                if (isDelivered(detail)) {
                    Date sginTime = getSginTime(detail);
                    if (sginTime != null) {
                        OrderItemDO orderItemDO = new OrderItemDO();
                        orderItemDO.setUserId(orderExpressDO.getUserId());
                        orderItemDO.setOrderNum(orderExpressDO.getOrderNum());
                        orderItemDO.setPkgNo(orderExpressDO.getPkgNo());
                        orderItemDO.setRouteId(orderExpressDO.getRouteId());
                        orderItemDO.setStatus(OrderStatusEnum.ORDER_STATUS_COMPLETE.getCode());
                        orderItemDO.setExpressStatus(ExpressStatusEnum.SIGNED.getCode());
                        orderItemDO.setSignTime(sginTime);
                        orderItemMapper.updateOrderItemStatusByPkgNo(orderItemDO);
                        EventBusFactory.getSyncEventBus().post(new OrderUpdateEvent(orderExpressDO.getOrderNum(), orderExpressDO.getUserId()));
                    }
                }
            }
        }

        return Result.buildSuccessMsg("处理完成");
    }

    private boolean isDelivered(String detail){
        if (StringUtils.isBlank(detail)) {
            return false;
        }

        JSONArray arr = JSON.parseArray(detail);
        if (CollectionUtil.isEmpty(arr)){
            return false;
        }

        for (int i = 0; i < arr.size(); i++) {
            if (StringUtils.containsIgnoreCase(arr.getString(i), "签收")
                    || StringUtils.containsIgnoreCase(arr.getString(i), "已投递")) {
                return true;
            } else if (StringUtils.containsIgnoreCase(arr.getString(i), "已送达")
                    && StringUtils.containsIgnoreCase(arr.getString(i), "保管")) {
                return true;
            } else if (StringUtils.containsIgnoreCase(arr.getString(i), "快件")
                    && StringUtils.containsIgnoreCase(arr.getString(i), "代收")){
                return true;
            }
        }

        return false;
    }

    private Date getSginTime(String detail) {
        if (StringUtils.isBlank(detail)) {
            return null;
        }
        JSONArray arr = JSON.parseArray(detail);
        if (CollectionUtil.isEmpty(arr)){
            return null;
        }
        Map map = (Map)arr.getJSONObject(arr.size() - 1);
        String sginTimeStr = (String) map.keySet().iterator().next();
        return DateUtil.parseDate(sginTimeStr, DateUtil.YYYYMMDD_HH_MM_SS);
    }
}
