package com.chengzi.apiunion.procurement.admin.web.formbean.filefolder;

import com.chengzi.apiunion.common.module.image.enums.FileTypeEnum;
import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;
import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.NotNull;

/**
 * @author 月汐
 * @date 2019/1/31 15:59
 */
public class RenameFileForm extends BaseForm {

    @NotNull(message = "请选择需要重命名的文件类型")
    private FileTypeEnum fileType;

    @NotBlankAndNull(message = "请选择需要重命名的文件或文件夹")
    private String key;

    @NotBlankAndNull(message = "请输入文件或文件夹名称")
    private String name;

    public FileTypeEnum getFileType() {
        return fileType;
    }

    public void setFileType(FileTypeEnum fileType) {
        this.fileType = fileType;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
