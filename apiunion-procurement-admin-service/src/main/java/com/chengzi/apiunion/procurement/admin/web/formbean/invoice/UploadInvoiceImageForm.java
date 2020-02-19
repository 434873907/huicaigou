package com.chengzi.apiunion.procurement.admin.web.formbean.invoice;

import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;
import com.chengzi.common.web.formbean.BaseForm;

import net.sf.oval.constraint.Min;

/**
 * @author 苏子
 * @date 2019年1月31日
 */
public class UploadInvoiceImageForm extends BaseForm {

    @Min(value = 1, message = "数据不存在")
    private long   id;
//    @NotBlankAndNull(message = "请选择图片")
    private String image;
    @NotBlankAndNull(message = "请填写发票代码")
    private String            invoiceCode;
    @NotBlankAndNull(message = "请填写发票号码")
    private String            invoiceNum;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public String getInvoiceNum() {
        return invoiceNum;
    }

    public void setInvoiceNum(String invoiceNum) {
        this.invoiceNum = invoiceNum;
    }

}
