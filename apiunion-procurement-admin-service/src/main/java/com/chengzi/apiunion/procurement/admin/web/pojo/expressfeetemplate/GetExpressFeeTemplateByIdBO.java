package com.chengzi.apiunion.procurement.admin.web.pojo.expressfeetemplate;

import com.chengzi.apiunion.expressfeetemplate.pojo.ExpressFeeTemplateDO;

/**
 * @author 月汐
 * @date 2018/10/19 14:38
 */
public class GetExpressFeeTemplateByIdBO {

    private Long id;

    /**
     * 模板名
     */
    private String templateName;

    /**
     * 供应商ID
     */
    private Long supplierId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 渠道
     */
    private Integer channelType;

    /**
     * 是否包邮
     */
    private Integer isFree;

    /**
     * 计费类型
     */
    private Integer valuationModel;

    /**
     * 是否存在按条件包邮
     */
    private Integer isFreeByIf;

    /**
     * 包邮条件
     */
    private String freeCondition;

    /**
     * 计费方式
     */
    private String carryMode;

    /**
     * 货币类型
     */
    private int currency;

    /**
     * 无法送达地区
     */
    private String undeliverableArea;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getChannelType() {
        return channelType;
    }

    public void setChannelType(Integer channelType) {
        this.channelType = channelType;
    }

    public Integer getIsFree() {
        return isFree;
    }

    public void setIsFree(Integer isFree) {
        this.isFree = isFree;
    }

    public Integer getValuationModel() {
        return valuationModel;
    }

    public void setValuationModel(Integer valuationModel) {
        this.valuationModel = valuationModel;
    }

    public Integer getIsFreeByIf() {
        return isFreeByIf;
    }

    public void setIsFreeByIf(Integer isFreeByIf) {
        this.isFreeByIf = isFreeByIf;
    }

    public String getFreeCondition() {
        return freeCondition;
    }

    public void setFreeCondition(String freeCondition) {
        this.freeCondition = freeCondition;
    }

    public String getCarryMode() {
        return carryMode;
    }

    public void setCarryMode(String carryMode) {
        this.carryMode = carryMode;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public String getUndeliverableArea() {
        return undeliverableArea;
    }

    public void setUndeliverableArea(String undeliverableArea) {
        this.undeliverableArea = undeliverableArea;
    }

    public static GetExpressFeeTemplateByIdBO convert(ExpressFeeTemplateDO expressFeeTemplateDO) {
        GetExpressFeeTemplateByIdBO bo = new GetExpressFeeTemplateByIdBO();
        bo.setId(expressFeeTemplateDO.getId());
        bo.setTemplateName(expressFeeTemplateDO.getTemplateName());
        bo.setSupplierId(expressFeeTemplateDO.getSupplierId());
        bo.setStatus(expressFeeTemplateDO.getStatus());
        bo.setChannelType(expressFeeTemplateDO.getChannelType());
        bo.setIsFree(expressFeeTemplateDO.getIsFree());
        bo.setValuationModel(expressFeeTemplateDO.getValuationModel());
        bo.setIsFreeByIf(expressFeeTemplateDO.getIsFreeByIf());
        bo.setFreeCondition(expressFeeTemplateDO.getFreeCondition());
        bo.setCarryMode(expressFeeTemplateDO.getCarryMode());
        bo.setCurrency(expressFeeTemplateDO.getCurrency());
        bo.setUndeliverableArea(expressFeeTemplateDO.getUndeliverableArea());
        return bo;
    }
}
