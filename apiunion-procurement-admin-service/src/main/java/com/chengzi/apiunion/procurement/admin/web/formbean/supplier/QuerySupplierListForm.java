package com.chengzi.apiunion.procurement.admin.web.formbean.supplier;

import java.util.Set;

import com.chengzi.common.web.formbean.BasePageForm;

/**
 * @author zhiyuan
 * 供应商查询：供应商类型，供应商名字 查询
 */
public class QuerySupplierListForm extends BasePageForm {

    /**
     * 供应商类型
     */
    private Set<Integer> apiTypes;
    /**
     * 供应商名字
     */
    private String       supplierName;

    private Integer      status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Set<Integer> getApiTypes() {
        return apiTypes;
    }

    public void setApiTypes(Set<Integer> apiTypes) {
        this.apiTypes = apiTypes;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
}
