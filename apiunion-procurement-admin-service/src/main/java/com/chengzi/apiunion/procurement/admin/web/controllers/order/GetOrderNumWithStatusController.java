package com.chengzi.apiunion.procurement.admin.web.controllers.order;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.order.enums.OrderStatusForStatisticEnum;
import com.chengzi.apiunion.procurement.admin.web.formbean.order.OrderListForm;
import com.chengzi.apiunion.procurement.admin.web.helper.order.AbstractOrderListHelper;
import com.chengzi.apiunion.procurement.admin.web.pojo.order.GetOrderNumWithStatusBO;
import com.chengzi.common.data.validate.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 月汐
 * @date 2019/11/14 09:58
 */
public class GetOrderNumWithStatusController extends AbstractApiController<OrderListForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, OrderListForm command) throws Exception {
        Map<OrderStatusForStatisticEnum, Long> resultMap = AbstractOrderListHelper.getInstance(command.getStatus()).statisticByQuery(command);

        GetOrderNumWithStatusBO bo = new GetOrderNumWithStatusBO();
        bo.setStatusOrderNumMap(new HashMap<>());

        for (OrderStatusForStatisticEnum statusForStatisticEnum : OrderStatusForStatisticEnum.values()) {
            bo.getStatusOrderNumMap().put(String.valueOf(statusForStatisticEnum.getCode()),
                    resultMap.getOrDefault(statusForStatisticEnum, 0L));
        }

        return Result.buildSuccessResult(bo);
    }
}
