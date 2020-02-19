package com.chengzi.apiunion.procurement.admin.web.formbean.filefolder;

import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;
import org.summercool.web.annotation.JsonField;

import java.util.List;

/**
 * @author 月汐
 * @date 2019/1/31 15:41
 */
public class MoveFileForm extends BaseForm {

    @JsonField
    private List<Long> fileFolderIds;

    @JsonField
    private List<String> imageMd5s;

    @Min(value = 0, message = "请选择父类目")
    private long parentId;

    public List<Long> getFileFolderIds() {
        return fileFolderIds;
    }

    public void setFileFolderIds(List<Long> fileFolderIds) {
        this.fileFolderIds = fileFolderIds;
    }

    public List<String> getImageMd5s() {
        return imageMd5s;
    }

    public void setImageMd5s(List<String> imageMd5s) {
        this.imageMd5s = imageMd5s;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }
}
