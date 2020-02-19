/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.controllers.item;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chengzi.apiunion.common.event.EventBusFactory;
import com.chengzi.apiunion.item.event.pojo.ItemFlowUpdateEvent;
import com.chengzi.apiunion.item.event.pojo.ItemUpdatedEvent;
import com.chengzi.apiunion.item.pojo.search.enums.ItemSortTypeEnum;
import com.chengzi.apiunion.item.pojo.search.enums.OperateTypeEnum;
import com.chengzi.apiunion.procurement.admin.web.helper.order.item.ItemListHelper;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.item.ItemListForm;
import com.chengzi.common.data.validate.Result;

/**
 * 商品列表
 * 
 * @author Kolor
 *
 */
public class ItemListController extends AbstractApiController<ItemListForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, ItemListForm command) throws Exception {
        command.setOrderByField(ItemSortTypeEnum.ID_DESC.getCode());
        return ItemListHelper.searchAndBuildItemList(command);
    }

}