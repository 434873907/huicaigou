/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.formbean.item;

import com.chengzi.apiunion.item.enums.ItemApproveTypeEnum;

/**
 * @author Kolor
 */
public class UnapprovedItemSkuListForm extends ItemListForm {

    /**
     * 审核类型
     * @see ItemApproveTypeEnum
     */
    private int approveType;

    public int getApproveType() {
        return approveType;
    }

    public void setApproveType(int approveType) {
        this.approveType = approveType;
    }
}
