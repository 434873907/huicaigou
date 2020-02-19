package com.chengzi.apiunion.procurement.admin.web.controllers.item;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.service.ItemService;
import com.chengzi.apiunion.procurement.admin.web.formbean.item.RemoveFromGroupForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 月汐
 * @date 2019/07/11 11:22
 */
public class RemoveFromGroupController extends AbstractApiController<RemoveFromGroupForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, RemoveFromGroupForm command) throws Exception {
        ItemService itemService = BeanFactory.getBean(ItemService.class);
        return itemService.removeFromGroup(command.getItemIds());
    }
}
