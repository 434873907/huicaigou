package com.chengzi.apiunion.procurement.admin.web.controllers.channel;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.service.ItemChannelTypeService;
import com.chengzi.apiunion.procurement.admin.web.formbean.channel.DeleteChannelForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 删除渠道
 *
 * @author 月汐
 * @date 2019/1/7 15:07
 */
public class DeleteChannelController extends AbstractApiController<DeleteChannelForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, DeleteChannelForm command) throws Exception {
        ItemChannelTypeService itemChannelTypeService = BeanFactory.getBean(ItemChannelTypeService.class);
        return itemChannelTypeService.delete(command.getId());
    }
}
