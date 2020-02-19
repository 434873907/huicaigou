package com.chengzi.apiunion.procurement.admin.web.controllers.order;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.order.pojo.OrderDO;
import com.chengzi.apiunion.order.service.OrderService;
import com.chengzi.apiunion.procurement.admin.web.formbean.order.TagOrderForm;
import com.chengzi.common.data.validate.Result;
import com.chengzi.yuncang.common.util.StringUtil;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 将订单做标记
 */
public class TagOrderController extends AbstractApiController<TagOrderForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, TagOrderForm command) throws Exception {

        OrderService orderService = BeanFactory.getBean(OrderService.class);
        long routeId = RequestContext.getRouteId();
        long userId = command.getUserId();
        int tagStatus = command.getTagStatus();
        String orderNum = command.getOrderNum();

        OrderDO orderDO = orderService.getOrderByOrderNum(userId, orderNum);
        if (orderDO == null) {
            LOGGER.warn(StringUtil.buildStatLog("order not found", routeId, userId, orderNum));
            return Result.buildIllegalArgumentResult("该订单不存在");
        }
        Result<String> result = orderService.tagOrder(orderNum, userId, tagStatus);
        LOGGER.info("tagOrder " + result.toJsonString());
        return result;
    }
}
