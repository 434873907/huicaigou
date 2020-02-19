package com.chengzi.apiunion.procurement.admin.web.controllers.presell;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.presell.UpdatePresellForm;
import com.chengzi.apiunion.presell.pojo.PresellDO;
import com.chengzi.apiunion.presell.service.PresellService;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 月汐
 * @date 2019/2/25 20:58
 */
public class UpdatePresellController extends AbstractApiController<UpdatePresellForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, UpdatePresellForm command) throws Exception {
        PresellService presellService = BeanFactory.getBean(PresellService.class);

        PresellDO presellDO = new PresellDO();
        presellDO.setId(command.getId());
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

        return presellService.update(presellDO);
    }
}
