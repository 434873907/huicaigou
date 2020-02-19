package com.chengzi.apiunion.procurement.admin.web.controllers.presell;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.presell.UpdatePresellStatusForm;
import com.chengzi.apiunion.presell.pojo.PresellDO;
import com.chengzi.apiunion.presell.service.PresellService;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 月汐
 * @date 2019/2/25 20:50
 */
public class UpdatePresellStatusController extends AbstractApiController<UpdatePresellStatusForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, UpdatePresellStatusForm command) throws Exception {
        PresellService presellService = BeanFactory.getBean(PresellService.class);

        PresellDO presellDO = new PresellDO();
        presellDO.setId(command.getId());
        presellDO.setStatus(command.getStatus());

        return presellService.updateStatus(presellDO);
    }
}
