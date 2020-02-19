package com.chengzi.apiunion.procurement.admin.web.formbean.order;

import com.chengzi.common.web.formbean.MultipartFilesForm;

/**
 * @author 月汐
 * @date 2018/12/12 16:36
 */
public class UploadExpressForm extends MultipartFilesForm {

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
