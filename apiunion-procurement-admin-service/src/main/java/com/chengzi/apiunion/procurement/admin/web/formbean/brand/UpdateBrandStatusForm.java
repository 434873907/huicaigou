package com.chengzi.apiunion.procurement.admin.web.formbean.brand;

import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.NotNull;

/**
 * @author 月汐
 * @date 2018/10/31 9:53
 */
public class UpdateBrandStatusForm extends BaseForm {

    @Min(value = 1, message = "请选择正确的品牌")
    private long id;

    @NotNull(message = "请选择正确的状态")
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
