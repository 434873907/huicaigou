package com.chengzi.apiunion.procurement.admin.web.formbean.groupbuying;

import com.chengzi.common.web.formbean.BasePageForm;

/**
 * @author 月汐
 * @date 2019/2/18 20:22
 */
public class QueryGroupBuyingForm extends BasePageForm {

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
