package com.chengzi.apiunion.procurement.admin.web.controllers.order;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chengzi.apiunion.common.pay.PayPlatform;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.pojo.order.GetPayPlatformBO;
import com.chengzi.apiunion.procurement.admin.web.pojo.order.OrderCreatePlatformBO;
import com.chengzi.common.data.enums.PlatformEnum;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.EmptyForm;

/**
 * 获取订单创建平台列表
 *
 * @author 致远
 * @date 2020/1/6
 */
public class OrderCreatePlatformController extends AbstractApiController<EmptyForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {
        List<OrderCreatePlatformBO> boList = new ArrayList<>();
        for (PlatformEnum platformEnum : PlatformEnum.values()) {
            if(platformEnum == PlatformEnum.PROCUREMENT_ADMIN) {//过滤掉
                continue;
            }
            OrderCreatePlatformBO bo = new OrderCreatePlatformBO();
            bo.setCode(platformEnum.getCode());
            bo.setName(platformEnum.getName());
            boList.add(bo);
        }
        return Result.buildSuccessResult(boList);
    }
}
