package com.chengzi.apiunion.procurement.admin.web.formbean.filefolder;

import com.chengzi.common.data.validate.oval.annonation.NotEmptyAndNull;
import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;

/**
 * @author ql
 * @date 2019/1/30
 * @description: 前端操作文件夹参数
 */
public class FileFolderForm extends BaseForm {

    @Min(value = 0, message = "无数据")
    private long id;
    private long pid;
    @NotEmptyAndNull(message = "文件夹名称不能为空")
    private String folderName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }
}
