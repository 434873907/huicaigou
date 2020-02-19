package com.chengzi.apiunion.procurement.admin.web.formbean.staticresource;

import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.Range;

/**
 * <p>
 * </p>
 *
 * @author 爱夏
 * @date 2018/1/16
 */
public class UpdateConfigStatusForm extends BaseForm {

    /**
     * 配置id
     */
    @Min(value = 1, message = "配置id不能为空")
    private long configId;

    @Range(min = 0, max = 1)
    private int  configStatus;

    public long getConfigId() {
        return configId;
    }

    public void setConfigId(long configId) {
        this.configId = configId;
    }

    public int getConfigStatus() {
        return configStatus;
    }

    public void setConfigStatus(int configStatus) {
        this.configStatus = configStatus;
    }
}
