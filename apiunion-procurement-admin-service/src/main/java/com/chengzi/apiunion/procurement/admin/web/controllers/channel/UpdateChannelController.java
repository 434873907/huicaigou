package com.chengzi.apiunion.procurement.admin.web.controllers.channel;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.pojo.ItemChannelTypeDO;
import com.chengzi.apiunion.item.service.ItemChannelTypeService;
import com.chengzi.apiunion.procurement.admin.web.formbean.channel.UpdateChannelForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 更新渠道详情
 *
 * @author 月汐
 * @date 2019/1/7 15:09
 */
public class UpdateChannelController extends AbstractApiController<UpdateChannelForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, UpdateChannelForm command) throws Exception {
        ItemChannelTypeService itemChannelTypeService = BeanFactory.getBean(ItemChannelTypeService.class);
        ItemChannelTypeDO itemChannelTypeDO = new ItemChannelTypeDO();
        itemChannelTypeDO.setId(command.getId());
        itemChannelTypeDO.setChannelTypeName(command.getChannelName());
        return itemChannelTypeService.update(itemChannelTypeDO);
    }
}
