package com.chengzi.apiunion.procurement.admin.web.pojo.order;

import com.chengzi.common.data.beans.JsonPojo;

import java.util.Map;

/**
 * @author 月汐
 * @date 2019/11/14 13:43
 */
public class GetOrderNumWithStatusBO extends JsonPojo {

    private Map<String, Long> statusOrderNumMap;

    public Map<String, Long> getStatusOrderNumMap() {
        return statusOrderNumMap;
    }

    public void setStatusOrderNumMap(Map<String, Long> statusOrderNumMap) {
        this.statusOrderNumMap = statusOrderNumMap;
    }
}
