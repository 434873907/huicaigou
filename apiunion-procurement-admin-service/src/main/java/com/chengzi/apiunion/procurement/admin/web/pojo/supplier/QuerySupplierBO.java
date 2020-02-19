package com.chengzi.apiunion.procurement.admin.web.pojo.supplier;

import java.util.Date;

import com.chengzi.common.data.beans.JsonPojo;

/**
 * @author 月汐
 * @date 2018/11/23 19:45
 */
public class QuerySupplierBO extends JsonPojo {

    private long   id;

    private String supplierName;

    private String channelTypeNames;

    private int    status;

    private int    itemNum;

    private int    apiType;

    private String apiTypeName;

    private String phone;

    private Date   createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getChannelTypeNames() {
        return channelTypeNames;
    }

    public void setChannelTypeNames(String channelTypeNames) {
        this.channelTypeNames = channelTypeNames;
    }

    public int getItemNum() {
        return itemNum;
    }

    public void setItemNum(int itemNum) {
        this.itemNum = itemNum;
    }

    public int getApiType() {
        return apiType;
    }

    public void setApiType(int apiType) {
        this.apiType = apiType;
    }

    public String getApiTypeName() {
        return apiTypeName;
    }

    public void setApiTypeName(String apiTypeName) {
        this.apiTypeName = apiTypeName;
    }
}
