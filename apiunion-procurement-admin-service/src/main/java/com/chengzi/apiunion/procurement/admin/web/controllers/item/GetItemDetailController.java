/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.controllers.item;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chengzi.apiunion.procurement.admin.web.helper.order.item.ItemHelper;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.item.GetItemDetailForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.item.ItemDetailBO;
import com.chengzi.common.data.validate.Result;

/**
 * 获取商品详情
 * 
 * @author Kolor
 *
 */
public class GetItemDetailController extends AbstractApiController<GetItemDetailForm> {

    @Override
    protected Result<ItemDetailBO> doBiz(HttpServletRequest request, HttpServletResponse response, GetItemDetailForm command) throws Exception {
        ItemDetailBO itemDetailBO = ItemHelper.buildItemDetailWithSupplierChanges(command.getId());
        return Result.buildSuccessResult(itemDetailBO);
    }
}
