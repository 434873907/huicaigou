package com.chengzi.apiunion.procurement.admin.web.pojo.agreement;

import com.chengzi.common.data.beans.JsonPojo;

/**
 * @author 月汐
 * @date 2019/1/14 11:29
 */
public class GetAgreementBO extends JsonPojo {

    private Long id;

    private Integer type;

    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
