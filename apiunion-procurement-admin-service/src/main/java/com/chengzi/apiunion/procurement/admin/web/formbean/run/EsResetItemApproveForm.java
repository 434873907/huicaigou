/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.formbean.run;

import com.chengzi.common.web.formbean.BaseForm;

/**
 * @author Kolor
 */
public class EsResetItemApproveForm extends BaseForm {
    private boolean init = false;

    //0同步待审核，2同步下架商品
    private Integer status;

    public boolean isInit() {
        return init;
    }

    public void setInit(boolean init) {
        this.init = init;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
