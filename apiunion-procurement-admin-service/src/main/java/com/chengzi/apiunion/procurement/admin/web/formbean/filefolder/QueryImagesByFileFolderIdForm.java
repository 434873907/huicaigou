package com.chengzi.apiunion.procurement.admin.web.formbean.filefolder;

import com.chengzi.common.web.formbean.BasePageForm;
import net.sf.oval.constraint.Min;

/**
 * @author 月汐
 * @date 2019/2/11 11:09
 */
public class QueryImagesByFileFolderIdForm extends BasePageForm {

    @Min(value = 0, message = "请选择目录")
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
