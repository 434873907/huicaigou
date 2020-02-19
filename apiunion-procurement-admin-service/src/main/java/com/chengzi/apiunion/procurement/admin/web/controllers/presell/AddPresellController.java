package com.chengzi.apiunion.procurement.admin.web.controllers.presell;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.pojo.cache.ItemCO;
import com.chengzi.apiunion.item.service.ItemService;
import com.chengzi.apiunion.procurement.admin.web.formbean.presell.AddPresellForm;
import com.chengzi.apiunion.presell.pojo.PresellDO;
import com.chengzi.apiunion.presell.service.PresellService;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

/**
 * @author 月汐
 * @date 2019/2/25 20:19
 */
public class AddPresellController extends AbstractApiController<AddPresellForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, AddPresellForm command) throws Exception {
        PresellService presellService = BeanFactory.getBean(PresellService.class);
        ItemService itemService = BeanFactory.getBean(ItemService.class);

        ItemCO itemCO = itemService.getItemWithCache(command.getItemId());
        if (!itemCO.isPresell()) {
            return Result.buildIllegalArgumentResult("该商品无法参加预售");
        }

        PresellDO presellDO = new PresellDO();
        presellDO.setName(command.getName());
        presellDO.setDepositStartTime(command.getDepositStartTime());
        presellDO.setDepositEndTime(command.getDepositEndTime());
        presellDO.setTailStartTime(command.getTailStartTime());
        presellDO.setTailEndTime(command.getTailEndTime());
        presellDO.setItemId(command.getItemId());
        presellDO.setSkuLimitList(command.getSkuSellLimit());
        presellDO.setChannelType(command.getChannelType());
        presellDO.setCurrency(command.getCurrency());
        presellDO.setDepositAmount(command.getDepositAmount());
        presellDO.setTailAmount(command.getTailAmount());
        presellDO.setLimit(command.getLimit());
        presellDO.setLimitNum(command.getLimitNum());
        presellDO.setStatusAfterAct(command.getStatusAfterAct());
        presellDO.setDepositPaidNum(0);
        presellDO.setTailPaidNum(0);
        presellDO.setActualPaidAmount(BigDecimal.ZERO);

        return presellService.add(presellDO);
    }
}
