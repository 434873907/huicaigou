package com.chengzi.apiunion.procurement.admin.web.controllers.order;

import com.chengzi.apiunion.common.pay.PayPlatform;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.pojo.order.GetPayPlatformBO;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.EmptyForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取支付平台列表
 *
 * @author 月汐
 * @date 2019/1/4 14:12
 */
public class GetPayPlatformController extends AbstractApiController<EmptyForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {
        List<GetPayPlatformBO> boList = new ArrayList<>();
        for (PayPlatform payPlatform : PayPlatform.values()) {
            GetPayPlatformBO bo = new GetPayPlatformBO();
            bo.setCode(payPlatform.getCode());
            bo.setRemark(payPlatform.getName());
            boList.add(bo);
        }
        return Result.buildSuccessResult(boList);
    }
}
