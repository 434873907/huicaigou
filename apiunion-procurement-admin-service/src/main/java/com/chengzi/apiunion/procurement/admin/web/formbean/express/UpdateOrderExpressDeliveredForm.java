package com.chengzi.apiunion.procurement.admin.web.formbean.express;

import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;

/**
 * @author 月汐
 * @date 2018/10/30 15:08
 */
public class UpdateOrderExpressDeliveredForm extends BaseForm {

    @Min(value = 1, message = "请选择正确的订单物流")
    private long id;

    @Min(value = 1, message = "请输入正确的用户ID")
    private long userId;

    @Min(value = 0, message = "请选择正确的签收状态")
    private int delivered;

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

    public int getDelivered() {
        return delivered;
    }

    public void setDelivered(int delivered) {
        this.delivered = delivered;
    }
}
