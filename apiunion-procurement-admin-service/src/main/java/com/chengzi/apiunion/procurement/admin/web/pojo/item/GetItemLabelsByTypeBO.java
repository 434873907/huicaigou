package com.chengzi.apiunion.procurement.admin.web.pojo.item;

import com.chengzi.common.data.beans.JsonPojo;

/**
 * @author 月汐
 * @date 2019/12/19 16:04
 */
public class GetItemLabelsByTypeBO extends JsonPojo {

    private long   id;

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
