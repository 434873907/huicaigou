package com.chengzi.apiunion.procurement.admin.web.formbean.filefolder;

import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.MinSize;
import org.summercool.web.annotation.JsonField;

import java.util.List;

/**
 * @author 月汐
 * @date 2019/1/31 16:08
 */
public class DeleteFileForm extends BaseForm {

    @JsonField
    private List<Long> deleteFileFolderIds;

    @JsonField
    private List<String> deleteImageMd5s;

    public List<Long> getDeleteFileFolderIds() {
        return deleteFileFolderIds;
    }

    public void setDeleteFileFolderIds(List<Long> deleteFileFolderIds) {
        this.deleteFileFolderIds = deleteFileFolderIds;
    }

    public List<String> getDeleteImageMd5s() {
        return deleteImageMd5s;
    }

    public void setDeleteImageMd5s(List<String> deleteImageMd5s) {
        this.deleteImageMd5s = deleteImageMd5s;
    }
}
