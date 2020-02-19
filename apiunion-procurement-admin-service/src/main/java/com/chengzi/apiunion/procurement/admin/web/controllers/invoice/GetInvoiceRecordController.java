package com.chengzi.apiunion.procurement.admin.web.controllers.invoice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.invoice.pojo.InvoiceRecordDO;
import com.chengzi.apiunion.invoice.service.InvoiceRecordService;
import com.chengzi.apiunion.procurement.admin.web.pojo.invoice.InvoiceRecordBO;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.IdForm;

/**
 * @author 苏子
 * @date 2019年2月18日
 */
public class GetInvoiceRecordController extends AbstractApiController<IdForm>{

    @Override
    protected Result<InvoiceRecordBO> doBiz(HttpServletRequest request, HttpServletResponse response, IdForm command) throws Exception {
        InvoiceRecordService invoiceRecordService = BeanFactory.getBean(InvoiceRecordService.class);
        InvoiceRecordDO invoiceRecord = invoiceRecordService.get(command.getId());
        return Result.buildSuccessResult(InvoiceRecordBO.convert(invoiceRecord));
    }

}
