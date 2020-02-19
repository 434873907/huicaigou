package com.chengzi.apiunion.procurement.admin.web.controllers.expressfeetemplate;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.expressfeetemplate.service.ExpressFeeTemplateService;
import com.chengzi.apiunion.procurement.admin.web.formbean.expressfeetemplate.DeleteExpressFeeTemplateForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 删除运费模板
 *
 * @author 月汐
 * @date 2018/10/19 14:34
 */
public class DeleteExpressFeeTemplateController extends AbstractApiController<DeleteExpressFeeTemplateForm> {

    @Override
    protected Result<String> doBiz(HttpServletRequest request, HttpServletResponse response, DeleteExpressFeeTemplateForm command) throws Exception {
        ExpressFeeTemplateService service = BeanFactory.getBean(ExpressFeeTemplateService.class);
        return service.delete(command.getId());
    }

}
