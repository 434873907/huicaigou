package com.chengzi.apiunion.procurement.admin.web.pojo.expresscompany;

import com.chengzi.common.data.beans.JsonPojo;

/**
 * @author 月汐
 * @date 2018/12/10 10:19
 */
public class QueryExpressCompanyListBO extends JsonPojo {

    private long id;

    private String name;

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
}
