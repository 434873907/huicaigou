package com.chengzi.apiunion.procurement.admin.web.controllers.item;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.item.GroupItemListForm;
import com.chengzi.apiunion.procurement.admin.web.helper.order.item.ItemListHelper;
import com.chengzi.common.data.validate.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 月汐
 * @date 2019/07/17 19:53
 */
public class GroupItemListController extends AbstractApiController<GroupItemListForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, GroupItemListForm command) throws Exception {
        return ItemListHelper.searchAndBuildItemList(command);
    }
}
