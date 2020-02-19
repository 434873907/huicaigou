/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.formbean.item.third;

import com.chengzi.common.web.formbean.IdForm;

import net.sf.oval.constraint.Range;

/**
 * @author Kolor
 */
public class UpdateThirdSyncItemAuthStatusForm extends IdForm {
    /**
     * 状态，0：禁用，1：启用
     */
    @Range(min = 0, max = 1, message = "状态不合法")
    private int status = -1;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
