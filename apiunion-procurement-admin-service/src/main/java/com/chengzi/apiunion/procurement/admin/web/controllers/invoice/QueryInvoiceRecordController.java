package com.chengzi.apiunion.procurement.admin.web.controllers.invoice;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.invoice.pojo.InvoiceRecordDO;
import com.chengzi.apiunion.invoice.query.InvoiceRecordQuery;
import com.chengzi.apiunion.invoice.service.InvoiceRecordService;
import com.chengzi.apiunion.procurement.admin.web.formbean.invoice.QueryInvoiceRecordForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.invoice.InvoiceRecordListBO;
import com.chengzi.common.data.beans.PageDataList;
import com.chengzi.common.data.validate.Result;

/**
 * @author 苏子
 * @date 2019年1月29日
 * 申请发票列表
 */
public class QueryInvoiceRecordController extends AbstractApiController<QueryInvoiceRecordForm> {

    @Override
    protected Result<PageDataList<InvoiceRecordListBO>> doBiz(HttpServletRequest request, HttpServletResponse response, QueryInvoiceRecordForm command)
            throws Exception {

        InvoiceRecordService invoiceRecordService = BeanFactory.getBean(InvoiceRecordService.class);
        InvoiceRecordQuery query = new InvoiceRecordQuery();
        query.setLimit(command.getLimit());
        query.setOffset(command.getOffset());
        
        query.setInvoiceStatus(command.getInvoiceStatus());
        query.setOrderNum(command.getOrderNum());

        List<InvoiceRecordListBO> list = new ArrayList<>();

        int count = invoiceRecordService.queryInvoiceRecordCount(query);
        if (count > 0) {
            List<InvoiceRecordDO> invoiceRecordList = invoiceRecordService.queryInvoiceRecord(query);
            list = InvoiceRecordListBO.convert(invoiceRecordList);
        }

        return Result.buildSuccessResult(new PageDataList<>(count, command.getPage(), command.getLimit(), list));
    }
}
