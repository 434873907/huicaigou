package com.chengzi.apiunion.procurement.admin.web.controllers.run;

import com.chengzi.apiunion.common.event.EventBusFactory;
import com.chengzi.apiunion.common.web.controllers.AbstractManageController;
import com.chengzi.apiunion.order.event.pojo.OrderUpdateEvent;
import com.chengzi.apiunion.procurement.admin.web.controllers.run.form.EsRefreshOneOrderForm;
import com.chengzi.common.data.validate.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 月汐
 * @date 2019/12/27 10:41
 */
public class EsRefreshOneOrderController extends AbstractManageController<EsRefreshOneOrderForm> {
    @Override
    protected Result<?> doInnerBiz(HttpServletRequest request, HttpServletResponse response, EsRefreshOneOrderForm command) throws Exception {
        EventBusFactory.getSyncEventBus().post(new OrderUpdateEvent(command.getOrderNum(), command.getUserId()));
        return Result.buildSuccessResult("同步成功");
    }
}
