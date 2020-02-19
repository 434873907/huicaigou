/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.formbean.item;

import com.chengzi.common.web.formbean.IdForm;

/**
 * @author Kolor
 */
public class GetItemDetailForm extends IdForm {
    public static final int FROM_PAGE_APPROVE = 1;

    /**
     * 来源页面
     * <br>审核页面：1
     */
    private int             fromPage;

    public int getFromPage() {
        return fromPage;
    }

    public void setFromPage(int fromPage) {
        this.fromPage = fromPage;
    }

}
