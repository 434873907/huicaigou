package com.chengzi.apiunion.procurement.admin.web.pojo.expressfeetemplate;

import com.chengzi.apiunion.expressfeetemplate.pojo.ExpressFeeTemplateDO;

/**
 * @author 月汐
 * @date 2018/10/19 16:54
 */
public class QueryTemplateListBySupplierBO {

    private Long id;

    private String templateName;

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

    public static QueryTemplateListBySupplierBO convert(ExpressFeeTemplateDO expressFeeTemplateDO) {
        QueryTemplateListBySupplierBO bo = new QueryTemplateListBySupplierBO();
        bo.setId(expressFeeTemplateDO.getId());
        bo.setTemplateName(expressFeeTemplateDO.getTemplateName());
        return bo;
    }
}
