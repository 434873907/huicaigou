/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.pojo.expressfeetemplate;

import com.chengzi.common.data.beans.JsonPojo;

/**
 * @author Kolor
 */
public class ExpressFeeTemplateInfo extends JsonPojo {
    private long   id;
    private String templateName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

}
