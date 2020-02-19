package com.chengzi.apiunion.procurement.admin.web.controllers.order;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.order.OrderListForm;
import com.chengzi.apiunion.procurement.admin.web.helper.order.AbstractOrderListHelper;
import com.chengzi.apiunion.procurement.admin.web.pojo.order.OrderListBO;
import com.chengzi.common.data.beans.PageDataList;
import com.chengzi.common.data.validate.Result;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 
* @author 苏子 
* @date 2018年10月17日
*/
public class OrderListController extends AbstractApiController<OrderListForm> {

    @Override
    protected Result<PageDataList<OrderListBO>> doBiz(HttpServletRequest request, HttpServletResponse response, OrderListForm command)
            throws Exception {
        if (command.getKeywordType() == OrderListForm.OrderListKeywordTypeEnum.ITEM_ID.getCode()
                && !StringUtils.isNumeric(command.getKeyword())) {
            return Result.buildIllegalArgumentResult("请输入正确的商品ID");
        }

        PageDataList pageDataList = AbstractOrderListHelper.getInstance(command.getStatus()).searchOrderList(command);
        if (pageDataList == null) {
            return Result.buildOpFailedResult("订单列表查询出错");
        }
        return Result.buildSuccessResult(pageDataList);
    }
}
