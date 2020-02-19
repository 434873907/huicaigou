package com.chengzi.apiunion.procurement.admin.web.controllers.channel;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.pojo.ItemChannelTypeDO;
import com.chengzi.apiunion.item.service.ItemChannelTypeService;
import com.chengzi.apiunion.procurement.admin.web.formbean.channel.AddChannelForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 新增渠道
 *
 * @author 月汐
 * @date 2019/1/7 14:53
 */
public class AddChannelController extends AbstractApiController<AddChannelForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, AddChannelForm command) throws Exception {
        ItemChannelTypeService itemChannelTypeService = BeanFactory.getBean(ItemChannelTypeService.class);
        ItemChannelTypeDO itemChannelTypeDO = new ItemChannelTypeDO();
        itemChannelTypeDO.setChannelTypeName(command.getChannelName());
        return itemChannelTypeService.add(itemChannelTypeDO);
    }
}
