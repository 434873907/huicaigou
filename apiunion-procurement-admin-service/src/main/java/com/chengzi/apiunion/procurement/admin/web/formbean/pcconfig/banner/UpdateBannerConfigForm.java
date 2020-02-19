package com.chengzi.apiunion.procurement.admin.web.formbean.pcconfig.banner;

import net.sf.oval.constraint.Min;

/**
 * @author 苏子
 * @date 2019年1月17日
 */
public class UpdateBannerConfigForm extends AddBannerConfigForm {

    @Min(value=1,message="数据不存在")
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
