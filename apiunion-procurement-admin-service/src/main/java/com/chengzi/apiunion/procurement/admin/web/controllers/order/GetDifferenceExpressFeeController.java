package com.chengzi.apiunion.procurement.admin.web.controllers.order;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.order.pojo.OrderDO;
import com.chengzi.apiunion.order.pojo.OrderItemDO;
import com.chengzi.apiunion.order.pojo.snapshot.FeeSnapshot;
import com.chengzi.apiunion.order.service.OrderService;
import com.chengzi.apiunion.order.util.OrderUtil;
import com.chengzi.apiunion.procurement.admin.web.formbean.order.GetDifferenceExpressFeeForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.order.GetDifferenceExpressFeeBO;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 月汐
 * @date 2019/4/10 17:04
 */
public class GetDifferenceExpressFeeController extends AbstractApiController<GetDifferenceExpressFeeForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, GetDifferenceExpressFeeForm command) throws Exception {
        OrderService orderService = BeanFactory.getBean(OrderService.class);
        List<OrderItemDO> orderItemDOList = orderService.queryByOrderNum(command.getUserId(), command.getOrderNum());
        OrderDO orderDO = orderService.getOrderByOrderNum(command.getUserId(), command.getOrderNum());

        Map<Long, Integer> refundItemNumMap = new HashMap<>();
        command.getRefundOrderItemList().forEach(item -> refundItemNumMap.put(item.getOrderItemId(), item.getRefundNum()));

        Result<BigDecimal> currentExpressFeeResult = OrderUtil.getCurrentExpressFee(orderDO, orderItemDOList, refundItemNumMap);
        if (!currentExpressFeeResult.isSuccess()) {
            return currentExpressFeeResult;
        }

        BigDecimal expFee = FeeSnapshot.parse(orderDO.getFeeSnapshot()).getExpAmount();

        GetDifferenceExpressFeeBO bo = new GetDifferenceExpressFeeBO();
        bo.setExpFee(expFee);
        bo.setCurrentExpFee(currentExpressFeeResult.getData());
        bo.setDifference(expFee.subtract(currentExpressFeeResult.getData()));

        return Result.buildSuccessResult(bo);
    }
}
