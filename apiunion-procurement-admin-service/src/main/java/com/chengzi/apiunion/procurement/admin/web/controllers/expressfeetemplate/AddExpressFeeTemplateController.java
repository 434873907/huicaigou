package com.chengzi.apiunion.procurement.admin.web.controllers.expressfeetemplate;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.expressfeetemplate.pojo.FreeCondition;
import com.chengzi.apiunion.expressfeetemplate.service.ExpressFeeTemplateService;
import com.chengzi.apiunion.procurement.admin.web.formbean.expressfeetemplate.AddExpressFeeTemplateForm;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import org.apache.commons.lang3.StringUtils;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 新增运费模板
 *
 * @author 月汐
 * @date 2018/10/19 13:57
 */
public class AddExpressFeeTemplateController extends AbstractApiController<AddExpressFeeTemplateForm> {

    @Override
    protected Result<Long> doBiz(HttpServletRequest request, HttpServletResponse response, AddExpressFeeTemplateForm command) throws Exception {
        ExpressFeeTemplateService service = BeanFactory.getBean(ExpressFeeTemplateService.class);

        Result result = command.verifyData();
        if (!result.isSuccess()) {
            return result;
        }

        return service.add(AddExpressFeeTemplateForm.convert(command));
    }
}
