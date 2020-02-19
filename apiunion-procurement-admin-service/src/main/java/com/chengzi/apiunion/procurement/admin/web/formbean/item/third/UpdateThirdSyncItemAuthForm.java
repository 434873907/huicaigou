/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.formbean.item.third;

import net.sf.oval.constraint.Min;

/**
 * @author Kolor
 */
public class UpdateThirdSyncItemAuthForm extends AddThirdSyncItemAuthForm {

    @Min(value = 1, message = "ID不能为空")
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
