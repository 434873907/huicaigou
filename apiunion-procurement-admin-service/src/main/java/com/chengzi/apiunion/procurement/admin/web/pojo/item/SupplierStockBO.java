/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.pojo.item;

import com.chengzi.apiunion.item.enums.ItemApproveTypeEnum;
import com.chengzi.apiunion.item.pojo.ItemSkuInfo.SupplierStock;

/**
 * @author Kolor
 */
public class SupplierStockBO extends SupplierStock {
    private static final long serialVersionUID = 3393579553579732902L;

    /**
     * 利润率
     */
    private String            profitRate;

    /**
     * 审核类型、
     * @see ItemApproveTypeEnum
     */
    private int               approveType;

    public String getProfitRate() {
        return profitRate;
    }

    public void setProfitRate(String profitRate) {
        this.profitRate = profitRate;
    }

    public int getApproveType() {
        return approveType;
    }

    public void setApproveType(int approveType) {
        this.approveType = approveType;
    }
}
