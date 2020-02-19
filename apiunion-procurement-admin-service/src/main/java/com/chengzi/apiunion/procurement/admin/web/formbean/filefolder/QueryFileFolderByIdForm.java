package com.chengzi.apiunion.procurement.admin.web.formbean.filefolder;

import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;

/**
 * @author 月汐
 * @date 2019/2/18 19:53
 */
public class QueryFileFolderByIdForm extends BaseForm {

    @Min(value = 0, message = "请选择正确的文件夹")
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
