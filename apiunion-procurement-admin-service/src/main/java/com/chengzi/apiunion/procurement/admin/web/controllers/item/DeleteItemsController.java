/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.controllers.item;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.service.ItemService;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.IdsForm;

/**
 * 删除商品
 * 
 * @author Kolor
 */
public class DeleteItemsController extends AbstractApiController<IdsForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, IdsForm command) throws Exception {
        ItemService itemService = BeanFactory.getBean(ItemService.class);
        itemService.deleteByIds(command.getIds());
        return Result.buildSuccessMsg("删除成功");
    }

}
