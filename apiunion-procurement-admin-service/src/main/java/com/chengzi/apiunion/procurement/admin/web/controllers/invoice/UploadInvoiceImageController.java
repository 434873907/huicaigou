package com.chengzi.apiunion.procurement.admin.web.controllers.invoice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chengzi.apiunion.common.util.RegexUtil;
import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.invoice.pojo.UpdateImage;
import com.chengzi.apiunion.invoice.service.InvoiceRecordService;
import com.chengzi.apiunion.procurement.admin.web.formbean.invoice.UploadInvoiceImageForm;
import com.chengzi.common.data.validate.Result;

/**
 * @author 苏子
 * @date 2019年1月31日
 * 上传发票图片
 */
public class UploadInvoiceImageController extends AbstractApiController<UploadInvoiceImageForm> {

    @Override
    protected Result<String> doBiz(HttpServletRequest request, HttpServletResponse response, UploadInvoiceImageForm command) throws Exception {

        if (!RegexUtil.isInvoiceNumber(command.getInvoiceNum())) {
            return Result.buildIllegalArgumentResult("请输入正确的发票号码");
        }

        UpdateImage param = new UpdateImage();
        param.setId(command.getId());
        param.setImage(command.getImage());
        param.setInvoiceCode(command.getInvoiceCode());
        param.setInvoiceNum(command.getInvoiceNum());
        
        InvoiceRecordService invoiceRecordService = BeanFactory.getBean(InvoiceRecordService.class);
        int row = invoiceRecordService.updateImage(param);
        if (row > 0) {
            return Result.buildSuccessMsg("成功");
        }
        return Result.buildSuccessMsg("失败");
    }

}
