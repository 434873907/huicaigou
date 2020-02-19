package com.chengzi.apiunion.procurement.admin.web.pojo.order;

import com.chengzi.common.data.beans.JsonPojo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * </p>
 * User: 摩天
 * Date: 2018-11-15 21:10
 */
public class OrderItemListBO extends JsonPojo {

    /**
     * 商品图片
     */
    private String itemImg;

    /**
     * 商品标题
     */
    private String itemTitle;

    /**
     * 商品编号
     */
    private String itemNum;

    /**
     * 数量
     */
    private int    count;

    /**
     * 原供应商
     */
    private String supplierName;

    /**
     * 可选供应商
     */
    private List<OtherSupplier> otherSuppliers;

    public String getItemImg() {
        return itemImg;
    }

    public void setItemImg(String itemImg) {
        this.itemImg = itemImg;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemNum() {
        return itemNum;
    }

    public void setItemNum(String itemNum) {
        this.itemNum = itemNum;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public void addOtherSupplier(long id,String name) {
        this.otherSuppliers.add(new OtherSupplier(id,name));
    }
}

class OtherSupplier extends JsonPojo{
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

    public OtherSupplier(long id, String name) {
        this.id = id;
        this.name = name;
    }
}