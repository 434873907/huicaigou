package com.chengzi.apiunion.procurement.admin.web.pojo.shop;

import com.chengzi.apiunion.shop.pojo.ShopDO;
import com.chengzi.common.data.beans.JsonPojo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 月汐
 * @date 2018/11/16 11:52
 */
public class QueryShopListBO extends JsonPojo {

    private Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<QueryShopListBO> convert(List<ShopDO> shopDOList) {
        List<QueryShopListBO> boList = new ArrayList<>();
        for (ShopDO shopDO : shopDOList) {
            QueryShopListBO bo = new QueryShopListBO();
            bo.setId(shopDO.getId());
            bo.setName(shopDO.getName());
            boList.add(bo);
        }
        return boList;
    }
}
