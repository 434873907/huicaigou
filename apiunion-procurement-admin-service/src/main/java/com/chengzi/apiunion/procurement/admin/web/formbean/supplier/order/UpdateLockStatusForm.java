package com.chengzi.apiunion.procurement.admin.web.formbean.supplier.order;

import java.util.List;

import com.chengzi.apiunion.supplier.common.order.enums.SupplierOrderLockStatusEnum;
import com.chengzi.common.data.validate.oval.annonation.NotEmptyAndNull;
import com.chengzi.common.web.formbean.BaseForm;

import net.sf.oval.constraint.MinSize;
import net.sf.oval.constraint.NotNull;

/**
 * @author 随风
 * @create 2020-01-15 20:13
 **/
public class UpdateLockStatusForm extends BaseForm {

    @NotNull(message = "包裹id不能为空")
    @MinSize(value = 1, message = "包裹id不能为空")
    private List<Long>                  ids;

    @NotEmptyAndNull(message = "订单号不能为空")
    private String                      orderNum;

    private SupplierOrderLockStatusEnum lock;

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public SupplierOrderLockStatusEnum getLock() {
        return lock;
    }

    public void setLock(SupplierOrderLockStatusEnum lock) {
        this.lock = lock;
    }
}
