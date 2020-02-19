/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.formbean.unit;

import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;
import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Size;

import java.util.List;

/**
 * @author Kolor
 *
 */
public class AddUnitForm extends BaseForm {
    @NotBlankAndNull(message = "单位不能为空")
    private String                unit;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
