package com.chengzi.apiunion.procurement.admin.web.formbean.presell;

import com.chengzi.common.web.formbean.BasePageForm;

/**
 * @author 月汐
 * @date 2019/2/26 9:57
 */
public class QueryPresellForm extends BasePageForm {

    private int status;

    private String keyword;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

}
