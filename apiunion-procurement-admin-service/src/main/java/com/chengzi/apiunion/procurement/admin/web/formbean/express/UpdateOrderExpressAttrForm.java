package com.chengzi.apiunion.procurement.admin.web.formbean.express;

import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;

/**
 * @author 月汐
 * @date 2018/10/30 14:34
 */
public class UpdateOrderExpressAttrForm extends BaseForm {

    @Min(value = 1, message = "请选择正确的订单物流")
    private long id;

    @Min(value = 1, message = "请输入正确的用户ID")
    private long userId;

    private String attr;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }
}
