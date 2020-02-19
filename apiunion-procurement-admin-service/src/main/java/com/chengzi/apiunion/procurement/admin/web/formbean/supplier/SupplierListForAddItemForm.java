package com.chengzi.apiunion.procurement.admin.web.formbean.supplier;

import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;

/**
 * @author 月汐
 * @date 2019/09/25 16:20
 */
public class SupplierListForAddItemForm extends BaseForm {

    /**
     * 渠道类型
     */
    private int channelType;

    public int getChannelType() {
        return channelType;
    }

    public void setChannelType(int channelType) {
        this.channelType = channelType;
    }
}
