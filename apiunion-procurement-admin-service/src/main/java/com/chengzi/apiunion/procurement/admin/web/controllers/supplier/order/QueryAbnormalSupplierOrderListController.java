package com.chengzi.apiunion.procurement.admin.web.controllers.supplier.order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.supplier.order.QuerySupplierOrderListForm;
import com.chengzi.apiunion.supplier.common.order.strategy.AbstractSupplierOrderSearchStrategy;
import com.chengzi.apiunion.supplier.common.order.strategy.pojo.SupplierOrderSearchParam;
import com.chengzi.common.data.validate.Result;

/**
 * @author 随风
 * @create 2020-01-09 20:12
 **/
public class QueryAbnormalSupplierOrderListController extends AbstractApiController<QuerySupplierOrderListForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, QuerySupplierOrderListForm command) throws Exception {
        SupplierOrderSearchParam searchParam = new SupplierOrderSearchParam();
        BeanUtils.copyProperties(command, searchParam);
        searchParam.setAbnormalStatus(command.getStatus().getCode());

        return Result.buildSuccessResult(AbstractSupplierOrderSearchStrategy.getInstance(command.getStatus()).searchPageList(searchParam));
    }
}
