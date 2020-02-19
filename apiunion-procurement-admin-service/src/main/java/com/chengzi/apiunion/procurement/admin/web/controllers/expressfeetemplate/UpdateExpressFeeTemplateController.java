package com.chengzi.apiunion.procurement.admin.web.controllers.expressfeetemplate;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.expressfeetemplate.service.ExpressFeeTemplateService;
import com.chengzi.apiunion.procurement.admin.web.formbean.expressfeetemplate.UpdateExpressFeeTemplateForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 更新运费模板
 *
 * @author 月汐
 * @date 2018/10/25 15:49
 */
public class UpdateExpressFeeTemplateController extends AbstractApiController<UpdateExpressFeeTemplateForm> {

    @Override
    protected Result<String> doBiz(HttpServletRequest request, HttpServletResponse response, UpdateExpressFeeTemplateForm command) throws Exception {
        ExpressFeeTemplateService service = BeanFactory.getBean(ExpressFeeTemplateService.class);

        Result result = command.verifyData();
        if (!result.isSuccess()) {
            return result;
        }

        return service.update(UpdateExpressFeeTemplateForm.convert(command));
    }

}
