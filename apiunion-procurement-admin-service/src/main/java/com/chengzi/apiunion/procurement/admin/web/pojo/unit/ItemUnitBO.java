package com.chengzi.apiunion.procurement.admin.web.pojo.unit;

import com.chengzi.apiunion.common.data.beans.RouteBaseDO;
import com.chengzi.common.data.beans.JsonPojo;

/**
 * 商品单位
 * @author 行者
 */
public class ItemUnitBO extends JsonPojo {

    private Long                 id;

    /**
     * 单位名称
     */
    private String               unit;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}