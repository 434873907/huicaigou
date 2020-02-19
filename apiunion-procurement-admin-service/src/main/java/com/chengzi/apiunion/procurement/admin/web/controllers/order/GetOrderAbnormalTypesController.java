package com.chengzi.apiunion.procurement.admin.web.controllers.order;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.order.enums.OrderAbnormalTypeEnum;
import com.chengzi.apiunion.order.service.OrderSearchService;
import com.chengzi.apiunion.procurement.admin.web.pojo.order.OrderAbnormalTypeBO;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.EmptyForm;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 月汐
 * @date 2019/07/04 20:14
 */
public class GetOrderAbnormalTypesController extends AbstractApiController<EmptyForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {
        OrderSearchService orderSearchService = BeanFactory.getBean(OrderSearchService.class);

        Map<Integer, Long> abnormalTypeOrderCountMap = orderSearchService.aggByAbnormal();
        List<OrderAbnormalTypeBO> boList = new ArrayList<>();

        for (OrderAbnormalTypeEnum abnormalType : OrderAbnormalTypeEnum.values()) {
            OrderAbnormalTypeBO bo = new OrderAbnormalTypeBO();
            bo.setAbnormalType(abnormalType.getCode());
            bo.setAbnormalTypeName(abnormalType.getName());
            bo.setOrderNum(abnormalTypeOrderCountMap.getOrDefault(abnormalType.getCode(), 0L));
            boList.add(bo);
        }

        return Result.buildSuccessResult(boList);
    }
}
