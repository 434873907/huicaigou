/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.pojo.item.third;

import java.util.Date;

import com.chengzi.apiunion.item.enums.ThirdSyncItemTypeEnum;
import com.chengzi.apiunion.item.pojo.ThirdSyncApiAuth;
import com.chengzi.apiunion.item.pojo.ThirdTargetItemAttr;
import com.chengzi.apiunion.item.strategy.presetprice.PresetPriceStrategy;
import com.chengzi.common.data.beans.JsonPojo;

/**
 * @author Kolor
 */
public class ThirdSyncItemAuthBO extends JsonPojo {
    private long                  id;

    /**
     * 创建时间
     */
    private Date                  createTime;
    /**
     * 修改时间
     */
    private Date                  modifyTime;

    /**
    * 名称
    */
    private String                name;

    /**
    * 供应商ID
    */
    private long                  supplierId;

    /**
    * API授权信息
    */
    private ThirdSyncApiAuth      apiAuth;

    /**
    * 类型，1：超级快递，2：橙子
    */
    private ThirdSyncItemTypeEnum apiType;

    /**
    * 商品目标
    */
    private ThirdTargetItemAttr   itemAttr;

    /**
    * 价格策略
    */
    private PresetPriceStrategy   priceStrategy;
    /**
    * 状态，0：禁用，1：启用
    */
    private int                   status;
    /**
     * 创建者
     */
    private long                  createPartnerUserId;
    /**
     * 创建者名称
     */
    private String                createPartnerUserName;
    /**
     * 修改者
     */
    private long                  modifyPartnerUserId;
    /**
     * 修改者名称
     */
    private String                modifyPartnerUserName;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public long getCreatePartnerUserId() {
        return createPartnerUserId;
    }

    public void setCreatePartnerUserId(long createPartnerUserId) {
        this.createPartnerUserId = createPartnerUserId;
    }

    public String getCreatePartnerUserName() {
        return createPartnerUserName;
    }

    public void setCreatePartnerUserName(String createPartnerUserName) {
        this.createPartnerUserName = createPartnerUserName;
    }

    public long getModifyPartnerUserId() {
        return modifyPartnerUserId;
    }

    public void setModifyPartnerUserId(long modifyPartnerUserId) {
        this.modifyPartnerUserId = modifyPartnerUserId;
    }

    public String getModifyPartnerUserName() {
        return modifyPartnerUserName;
    }

    public void setModifyPartnerUserName(String modifyPartnerUserName) {
        this.modifyPartnerUserName = modifyPartnerUserName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(long supplierId) {
        this.supplierId = supplierId;
    }

    public ThirdSyncApiAuth getApiAuth() {
        return apiAuth;
    }

    public void setApiAuth(ThirdSyncApiAuth apiAuth) {
        this.apiAuth = apiAuth;
    }

    public ThirdSyncItemTypeEnum getApiType() {
        return apiType;
    }

    public void setApiType(ThirdSyncItemTypeEnum apiType) {
        this.apiType = apiType;
    }

    public ThirdTargetItemAttr getItemAttr() {
        return itemAttr;
    }

    public void setItemAttr(ThirdTargetItemAttr itemAttr) {
        this.itemAttr = itemAttr;
    }

    public PresetPriceStrategy getPriceStrategy() {
        return priceStrategy;
    }

    public void setPriceStrategy(PresetPriceStrategy priceStrategy) {
        this.priceStrategy = priceStrategy;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
