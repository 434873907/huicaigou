package com.chengzi.apiunion.procurement.admin.web.pojo.expressfeetemplate;

import com.chengzi.apiunion.expressfeetemplate.pojo.ExpressFeeTemplateDO;
import com.chengzi.common.data.beans.JsonPojo;

/**
 * @author 月汐
 * @date 2018/10/19 15:02
 */
public class QueryExpressFeeTemplateBO extends JsonPojo {

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
     * 是否包邮
     */
    private Integer isFree;

    /**
     * 启用状态
     */
    private Integer status;

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

    public Integer getIsFree() {
        return isFree;
    }

    public void setIsFree(Integer isFree) {
        this.isFree = isFree;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public static QueryExpressFeeTemplateBO convert(ExpressFeeTemplateDO expressFeeTemplateDO) {
        QueryExpressFeeTemplateBO bo = new QueryExpressFeeTemplateBO();
        bo.setId(expressFeeTemplateDO.getId());
        bo.setTemplateName(expressFeeTemplateDO.getTemplateName());
        bo.setSupplierId(expressFeeTemplateDO.getSupplierId());
        bo.setIsFree(expressFeeTemplateDO.getIsFree());
        bo.setStatus(expressFeeTemplateDO.getStatus());
        return bo;
    }

}
