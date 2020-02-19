package com.chengzi.apiunion.procurement.admin.web.controllers.expressfeetemplate;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.expressfeetemplate.service.ExpressFeeTemplateService;
import com.chengzi.apiunion.procurement.admin.web.formbean.expressfeetemplate.UpdateExpressFeeTemplateStatusForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 更新运费模板状态
 *
 * @author 月汐
 * @date 2018/10/19 15:18
 */
public class UpdateExpressFeeTemplateStatusController extends AbstractApiController<UpdateExpressFeeTemplateStatusForm> {
    @Override
    protected Result<String> doBiz(HttpServletRequest request, HttpServletResponse response, UpdateExpressFeeTemplateStatusForm command) throws Exception {
        ExpressFeeTemplateService service = BeanFactory.getBean(ExpressFeeTemplateService.class);
        return service.updateStatus(command.getId(), command.getStatus());
    }
}
