/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.formbean.upload;

import com.chengzi.apiunion.common.module.image.enums.ImageUploadTypeEnum;
import com.chengzi.common.web.formbean.MultipartFileForm;

import net.sf.oval.constraint.NotNull;

/**
 * @author Kolor
 */
public class FileUploadForm extends MultipartFileForm {
    @NotNull(message = "文件上传类型未设置")
    private ImageUploadTypeEnum uploadType;

    private long fileFolderId;

    public ImageUploadTypeEnum getUploadType() {
        return uploadType;
    }

    public void setUploadType(ImageUploadTypeEnum uploadType) {
        this.uploadType = uploadType;
    }

    public long getFileFolderId() {
        return fileFolderId;
    }

    public void setFileFolderId(long fileFolderId) {
        this.fileFolderId = fileFolderId;
    }
}
