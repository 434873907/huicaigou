package com.chengzi.apiunion.procurement.admin.web.formbean.staticresource;

import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;

/**
 * <p>
 * </p>
 *
 * @author 爱夏
 * @date: 17/4/18
 */
public class GetStaticResourcesConfigForm extends BaseForm {

    /**
     * 配置id
     */
    @Min(value = 1, message = "配置id不能小于1")
    private long configId;

    public long getConfigId() {
        return configId;
    }

    public void setConfigId(long configId) {
        this.configId = configId;
    }
}
