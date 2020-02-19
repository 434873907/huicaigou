/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.formbean.supplier;

import com.chengzi.common.web.formbean.BaseForm;

/**
 * @author Kolor
 *
 */
public class SupplierListForm extends BaseForm {
    /**
     * 渠道类型
     */
    private int channelType;

    /**
     * 供应商名字
     */
    private String name;

    public int getChannelType() {
        return channelType;
    }

    public void setChannelType(int channelType) {
        this.channelType = channelType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
