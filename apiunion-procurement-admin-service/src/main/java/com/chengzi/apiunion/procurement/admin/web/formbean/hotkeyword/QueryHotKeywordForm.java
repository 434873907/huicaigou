package com.chengzi.apiunion.procurement.admin.web.formbean.hotkeyword;

import com.chengzi.common.web.formbean.BasePageForm;

/**
 * @author 月汐
 * @date 2018/12/4 15:22
 */
public class QueryHotKeywordForm extends BasePageForm {

    private Integer status;

    private String name;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
