package com.chengzi.apiunion.procurement.admin.web.controllers.run;

import com.chengzi.apiunion.common.web.controllers.AbstractManageController;
import com.chengzi.apiunion.order.service.OrderService;
import com.chengzi.apiunion.procurement.admin.web.controllers.run.form.EsOrderRefreshForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 月汐
 * @date 2019/10/16 10:44
 */
public class UpdateOrderController extends AbstractManageController<EsOrderRefreshForm> {
    @Override
    protected Result<?> doInnerBiz(HttpServletRequest request, HttpServletResponse response, EsOrderRefreshForm command) throws Exception {
        OrderService orderService = BeanFactory.getBean(OrderService.class);
        return orderService.updateOrderEs(command.getTableNum(), true);
    }
}
