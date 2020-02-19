package com.chengzi.apiunion.procurement.admin.web.formbean.pcconfig.tab;

import net.sf.oval.constraint.Min;

/**
 * @author 苏子
 * @date 2019年1月18日
 */
public class UpdateTabConfigForm extends AddTabConfigForm {

    @Min(value=1,message="数据不存在")
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
