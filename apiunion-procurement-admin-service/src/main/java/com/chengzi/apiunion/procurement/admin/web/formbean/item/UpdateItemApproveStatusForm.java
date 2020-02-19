/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.formbean.item;

import java.util.Set;

import com.chengzi.common.web.formbean.BaseForm;

/**
 * @author Kolor
 */
public class UpdateItemApproveStatusForm extends BaseForm {
    private Set<Long> approvedIds;
    private Set<Long> refusedIds;

    public Set<Long> getApprovedIds() {
        return approvedIds;
    }

    public void setApprovedIds(Set<Long> approvedIds) {
        this.approvedIds = approvedIds;
    }

    public Set<Long> getRefusedIds() {
        return refusedIds;
    }

    public void setRefusedIds(Set<Long> refusedIds) {
        this.refusedIds = refusedIds;
    }

}
