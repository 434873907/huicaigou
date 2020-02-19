/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.formbean.run;

import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;
import com.chengzi.common.web.formbean.BaseForm;

/**
 * @author Kolor
 */
public class SqlGenerateForm extends BaseForm {
    @NotBlankAndNull(message = "请输入sql")
    private String sql;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

}
