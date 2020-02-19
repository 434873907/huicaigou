package com.chengzi.apiunion.procurement.admin.web.controllers.presell;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.presell.service.PresellService;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.IdForm;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 月汐
 * @date 2019/2/25 20:42
 */
public class DeletePresellController extends AbstractApiController<IdForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, IdForm command) throws Exception {
        PresellService presellService = BeanFactory.getBean(PresellService.class);

        return presellService.delete(command.getId());
    }
}
