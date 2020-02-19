package com.chengzi.apiunion.procurement.admin.web.pojo.areaaddress;

import com.chengzi.apiunion.common.module.areaadress.pojo.AreaAddressDO;
import com.chengzi.common.data.beans.JsonPojo;

import java.util.List;

/**
 * @author 月汐
 * @date 2018/10/20 10:55
 */
public class QueryAreaAddressListBO extends JsonPojo {

    private Long id;

    /**
     * 地址名
     */
    private String areaName;

    /**
     * 地址层级
     */
    private Integer level;

    private List<QueryAreaAddressListBO> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public List<QueryAreaAddressListBO> getChildren() {
        return children;
    }

    public void setChildren(List<QueryAreaAddressListBO> children) {
        this.children = children;
    }

    public static QueryAreaAddressListBO convert(AreaAddressDO areaAddressDO, List<QueryAreaAddressListBO> childrenList) {
        QueryAreaAddressListBO bo = new QueryAreaAddressListBO();
        bo.setId(areaAddressDO.getId());
        bo.setAreaName(areaAddressDO.getAreaName());
        bo.setLevel(areaAddressDO.getLevel());
        bo.setChildren(childrenList);
        return bo;
    }
}
