package com.chengzi.apiunion.procurement.admin.web.controllers.channel;

import com.chengzi.apiunion.common.data.beans.RouteQuery;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.pojo.ItemChannelTypeDO;
import com.chengzi.apiunion.item.service.ItemChannelTypeService;
import com.chengzi.apiunion.procurement.admin.web.pojo.channel.ChannelBO;
import com.chengzi.common.data.beans.PageDataList;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.BasePageForm;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 渠道列表
 *
 * @author 月汐
 * @date 2019/1/7 15:18
 */
public class QueryChannelController extends AbstractApiController<BasePageForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, BasePageForm command) throws Exception {
        ItemChannelTypeService itemChannelTypeService = BeanFactory.getBean(ItemChannelTypeService.class);
        RouteQuery query = new RouteQuery();
        query.setOffset(command.getOffset());
        query.setLimit(command.getLimit());
        List<ItemChannelTypeDO> itemChannelTypeDOS = itemChannelTypeService.selectByQuery(query);
        List<ChannelBO> boList = new ArrayList<>();
        for (ItemChannelTypeDO itemChannelTypeDO : itemChannelTypeDOS) {
            ChannelBO bo = new ChannelBO();
            bo.setChannelId(itemChannelTypeDO.getId());
            bo.setChannelType(itemChannelTypeDO.getChannelType());
            bo.setChannelTypeName(itemChannelTypeDO.getChannelTypeName());
            boList.add(bo);
        }
        long total = itemChannelTypeService.countByQuery();
        return Result.buildSuccessResult(new PageDataList<>(total, command.getPage(), command.getLimit(), boList));
    }
}
