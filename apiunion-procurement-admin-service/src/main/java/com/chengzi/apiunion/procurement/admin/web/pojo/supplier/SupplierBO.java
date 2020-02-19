/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.pojo.supplier;

import java.util.List;

import com.chengzi.apiunion.item.pojo.ThirdSourceItemAttr;
import com.chengzi.apiunion.item.pojo.ThirdSyncApiAuth;
import com.chengzi.apiunion.item.pojo.ThirdTargetItemAttr;
import com.chengzi.apiunion.item.strategy.presetprice.PresetPriceStrategy;
import com.chengzi.apiunion.procurement.admin.web.pojo.expressfeetemplate.ExpressFeeTemplateInfo;
import com.chengzi.apiunion.procurement.admin.web.pojo.item.ItemForActBO.ChannelInfo;
import com.chengzi.common.data.beans.JsonPojo;

/**
 * @author Kolor
 *
 */
public class SupplierBO extends JsonPojo {
    private long                         id;
    private String                       supplierName;

    private List<ChannelInfo>            channelTypes;

    private int                          apiType;

    private ThirdSyncApiAuth             apiAuth;

    private ThirdSourceItemAttr          sourceItemAttr;

    private ThirdTargetItemAttr          targetItemAttr;

    private PresetPriceStrategy          priceStrategy;

    private List<ExpressFeeTemplateInfo> expressFeeTemplates;

    public List<ExpressFeeTemplateInfo> getExpressFeeTemplates() {
        return expressFeeTemplates;
    }

    public void setExpressFeeTemplates(List<ExpressFeeTemplateInfo> expressFeeTemplates) {
        this.expressFeeTemplates = expressFeeTemplates;
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
