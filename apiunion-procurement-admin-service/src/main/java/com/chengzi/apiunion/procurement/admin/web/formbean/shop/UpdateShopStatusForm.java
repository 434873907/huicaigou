package com.chengzi.apiunion.procurement.admin.web.formbean.shop;

import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;

/**
 * @author 月汐
 * @date 2018/11/15 19:38
 */
public class UpdateShopStatusForm extends BaseForm {

    @Min(value = 1, message = "请选择正确的店铺")
    private long id;

    private Integer status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
