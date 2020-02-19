package com.chengzi.apiunion.procurement.admin.web.formbean.staticresource;

import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.Range;

/**
 * <p>
 * </p>
 *
 * @author 爱夏
 * @date 2017/5/17
 */
public class UpdateLabelConfigImageForm extends BaseForm {

    @Min(value = 1, message = "配置id必须大于0")
    private long configId;

    @Range(min = 1, max = 4, message = "type范围为[1-4]")
    private int  type;

    public long getConfigId() {
        return configId;
    }

    public void setConfigId(long configId) {
        this.configId = configId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
