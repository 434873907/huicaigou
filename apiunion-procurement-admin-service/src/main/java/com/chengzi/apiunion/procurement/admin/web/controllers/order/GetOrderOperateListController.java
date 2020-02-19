package com.chengzi.apiunion.procurement.admin.web.controllers.order;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.order.pojo.OrderOperateListQuery;
import com.chengzi.apiunion.order.pojo.OrderOperateRecordDO;
import com.chengzi.apiunion.order.service.OrderOperateService;
import com.chengzi.apiunion.procurement.admin.web.formbean.order.OrderOperateListForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.order.OrderOperateListBO;
import com.chengzi.common.data.beans.PageDataList;
import com.chengzi.common.data.validate.Result;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取订单的操作记录
 */
public class GetOrderOperateListController extends AbstractApiController<OrderOperateListForm> {

    @Autowired
    private OrderOperateService orderOperateService;
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, OrderOperateListForm command) throws Exception {


        Long userId = command.getUserId();
        String orderNum = command.getOrderNum();

        OrderOperateListQuery query = new OrderOperateListQuery();
        query.setUserId(userId);
        query.setOrderNum(orderNum);
        query.setLimit(command.getLimit());
        query.setOffset((command.getPage() - 1) * command.getLimit());
        query.setOrderBy("create_time desc");
        List<OrderOperateRecordDO> recordDOS = orderOperateService.selectOperateRecord(query);
        Long total = orderOperateService.selectOperateRecordCount(query);

        PageDataList pageDataList = new PageDataList();
        pageDataList.setTotal(total);
        pageDataList.setTotalPage(getTotalPage(total, command.getLimit()));


        List<OrderOperateListBO> dataList = new ArrayList<>();
        pageDataList.setDataList(dataList);

        recordDOS.forEach(recordDO -> {
            OrderOperateListBO operateListBO = converter(recordDO);
            dataList.add(operateListBO);
        });

        return Result.buildSuccessResult(pageDataList);
    }

    private OrderOperateListBO converter(OrderOperateRecordDO operateRecordDO) {
        OrderOperateListBO operateListBO = new OrderOperateListBO();

        operateListBO.setCreateTime(operateRecordDO.getCreateTime());
        //操作来源 1用户，2系统，3商户管理员
        if(operateRecordDO.getSource() == 1 || operateRecordDO.getSource() == 2) {
            operateListBO.setSourceDesc(operateRecordDO.getSourceDesc());
        } else {
            String operator = operateRecordDO.getOperator() != null ? operateRecordDO.getOperator() : operateRecordDO.getSourceDesc();
            operateListBO.setSourceDesc(operator);
        }
        operateListBO.setRemark(operateRecordDO.getRemark());
        operateListBO.setOperator(operateRecordDO.getOperator());
        return operateListBO;
    }
}
