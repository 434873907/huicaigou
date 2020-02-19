package com.chengzi.apiunion.procurement.admin.web.controllers.groupbuying;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.groupbuying.AddGroupBuyingForm;
import com.chengzi.apiunion.groupbuying.pojo.GroupBuyingDO;
import com.chengzi.apiunion.groupbuying.service.GroupBuyingService;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 新增团购
 *
 * @author 月汐
 * @date 2019/2/18 17:02
 */
public class AddGroupBuyingController extends AbstractApiController<AddGroupBuyingForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, AddGroupBuyingForm command) throws Exception {
        GroupBuyingService groupBuyingService = BeanFactory.getBean(GroupBuyingService.class);

        GroupBuyingDO groupBuyingDO = new GroupBuyingDO();
        groupBuyingDO.setName(command.getName());
        groupBuyingDO.setStartTime(command.getStartTime());
        groupBuyingDO.setEndTime(command.getEndTime());
        groupBuyingDO.setBanner(command.getBanner());
        groupBuyingDO.setDisableCoupons(command.isDisableCoupons());
        groupBuyingDO.setItemId(command.getItemId());
        groupBuyingDO.setSkuIds(command.getSkuIds());
        groupBuyingDO.setLimit(command.isLimit());
        groupBuyingDO.setLimitNum(command.getLimitNum());
        groupBuyingDO.setStockNum(command.getStockNum());
        groupBuyingDO.setGroupPrice(command.getGroupPrice());
        groupBuyingDO.setChannelType(command.getChannelType());
        groupBuyingDO.setCurrency(command.getCurrency().getCode());

        return groupBuyingService.add(groupBuyingDO);
    }
}
