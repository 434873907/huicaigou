package com.chengzi.apiunion.procurement.admin.web.formbean.pcconfig.banner;

import java.util.List;

import com.chengzi.common.web.formbean.BaseForm;
import org.summercool.web.annotation.JsonField;

/**
 * @author 苏子
 * @date 2019年1月18日
 */
public class UpdateBannerConfigOrderForm extends BaseForm{

    @JsonField
    private List<Long> ids;

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
    
}
