package com.chengzi.apiunion.procurement.admin.web.pojo.channel;

import java.util.List;

import com.chengzi.apiunion.procurement.admin.web.pojo.supplier.SupplierBO;
import com.chengzi.common.data.beans.JsonPojo;

/**
 * @author 
 */
public class ItemChannelTypeBO extends JsonPojo {
    /**
     * 渠道类型
     */
    private int              channelType;

    /**
     * 渠道名称
     */
    private String           channelTypeName;

    /**
     * 供应商
     */
    private List<SupplierBO> supplierList;

    public List<SupplierBO> getSupplierList() {
        return supplierList;
    }

    public void setSupplierList(List<SupplierBO> supplierList) {
        this.supplierList = supplierList;
    }

    public int getChannelType() {
        return channelType;
    }

    public void setChannelType(int channelType) {
        this.channelType = channelType;
    }

    public String getChannelTypeName() {
        return channelTypeName;
    }

    public void setChannelTypeName(String channelTypeName) {
        this.channelTypeName = channelTypeName;
    }

}