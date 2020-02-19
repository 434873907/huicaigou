package com.chengzi.apiunion.procurement.admin.web.pojo.supplier;

import java.util.Date;
import java.util.List;

import com.chengzi.apiunion.item.pojo.ThirdSourceItemAttr;
import com.chengzi.apiunion.item.pojo.ThirdSyncApiAuth;
import com.chengzi.apiunion.item.pojo.ThirdTargetItemAttr;
import com.chengzi.apiunion.item.strategy.presetprice.PresetPriceStrategy;
import com.chengzi.apiunion.procurement.admin.web.pojo.item.ItemForActBO.ChannelInfo;
import com.chengzi.common.data.beans.JsonPojo;

/**
 * @author 月汐
 * @date 2018/11/23 19:55
 */
public class GetSupplierDetailBO extends JsonPojo {

    private long                id;

    private String              supplierName;

    private List<ChannelInfo>   channelTypes;

    private int                 apiType;

    private String              apiTypeName;

    private ThirdSyncApiAuth    apiAuth;

    private ThirdSourceItemAttr sourceItemAttr;

    private ThirdTargetItemAttr targetItemAttr;

    private PresetPriceStrategy priceStrategy;

    private int                 status;

    private String              account;

    private String              contacts;

    private String              phone;

    private Date                createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getApiTypeName() {
        return apiTypeName;
    }

    public void setApiTypeName(String apiTypeName) {
        this.apiTypeName = apiTypeName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
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

    public List<ChannelInfo> getChannelTypes() {
        return channelTypes;
    }

    public void setChannelTypes(List<ChannelInfo> channelTypes) {
        this.channelTypes = channelTypes;
    }

    public int getApiType() {
        return apiType;
    }

    public void setApiType(int apiType) {
        this.apiType = apiType;
    }

    public ThirdSyncApiAuth getApiAuth() {
        return apiAuth;
    }

    public void setApiAuth(ThirdSyncApiAuth apiAuth) {
        this.apiAuth = apiAuth;
    }

    public ThirdSourceItemAttr getSourceItemAttr() {
        return sourceItemAttr;
    }

    public void setSourceItemAttr(ThirdSourceItemAttr sourceItemAttr) {
        this.sourceItemAttr = sourceItemAttr;
    }

    public ThirdTargetItemAttr getTargetItemAttr() {
        return targetItemAttr;
    }

    public void setTargetItemAttr(ThirdTargetItemAttr targetItemAttr) {
        this.targetItemAttr = targetItemAttr;
    }

    public PresetPriceStrategy getPriceStrategy() {
        return priceStrategy;
    }

    public void setPriceStrategy(PresetPriceStrategy priceStrategy) {
        this.priceStrategy = priceStrategy;
    }
}
