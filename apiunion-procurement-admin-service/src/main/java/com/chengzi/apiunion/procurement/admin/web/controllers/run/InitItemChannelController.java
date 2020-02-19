/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.controllers.run;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.web.controllers.AbstractManageController;
import com.chengzi.apiunion.item.pojo.ItemChannelTypeDO;
import com.chengzi.apiunion.item.service.ItemChannelTypeService;
import com.chengzi.apiunion.procurement.admin.web.formbean.run.InitItemChannelForm;
import com.chengzi.common.data.validate.Result;

/**
 * 初始化商品渠道
 * 
 * @author Kolor
 */
public class InitItemChannelController extends AbstractManageController<InitItemChannelForm> {

    @Override
    protected Result<?> doInnerBiz(HttpServletRequest request, HttpServletResponse response, InitItemChannelForm command) throws Exception {
        ItemChannelTypeService itemChannelTypeService = BeanFactory.getBean(ItemChannelTypeService.class);
        for (Long routeId : command.getRouteIds()) {
            RequestContext.prepareByRouteId(routeId);
            for (String channelName : command.getChannels()) {
                if (StringUtils.isNotBlank(channelName)) {
                    ItemChannelTypeDO itemChannelTypeDO = new ItemChannelTypeDO();
                    itemChannelTypeDO.setChannelTypeName(channelName);
                    itemChannelTypeDO.setNeedVerifyIdentity(0);
                    itemChannelTypeDO.setRouteId(routeId);

                    itemChannelTypeService.add(itemChannelTypeDO);
                }
            }
        }
        return Result.buildSuccessMsg("添加成功");
    }

}
