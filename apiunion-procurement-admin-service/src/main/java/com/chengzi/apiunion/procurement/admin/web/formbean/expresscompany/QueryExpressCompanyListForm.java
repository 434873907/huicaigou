package com.chengzi.apiunion.procurement.admin.web.formbean.expresscompany;

import com.chengzi.common.web.formbean.BasePageForm;

/**
 * @author 月汐
 * @date 2020/01/10 11:56
 */
public class QueryExpressCompanyListForm extends BasePageForm {

    private int hideNonExpressCompany;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHideNonExpressCompany() {
        return hideNonExpressCompany;
    }

    public void setHideNonExpressCompany(int hideNonExpressCompany) {
        this.hideNonExpressCompany = hideNonExpressCompany;
    }
}
