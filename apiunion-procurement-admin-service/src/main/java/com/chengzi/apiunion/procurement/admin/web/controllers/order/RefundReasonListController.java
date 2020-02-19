package com.chengzi.apiunion.procurement.admin.web.controllers.order;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.EmptyForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.chengzi.apiunion.order.util.OrderUtil.getRefundReasonList;

/**
 * 获取退款原因列表
 *
 * @author 月汐
 * @date 2019/1/4 11:12
 */
public class RefundReasonListController extends AbstractApiController<EmptyForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {
        return Result.buildSuccessResult(getRefundReasonList());
    }
}
