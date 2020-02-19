package com.chengzi.apiunion.procurement.admin.web.formbean.brand;

import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.MinSize;
import net.sf.oval.constraint.NotNull;
import org.summercool.web.annotation.JsonField;

import java.util.List;

/**
 * @author 行者
 */
public class MoveBrandItemForm extends BaseForm {

    @NotNull(message = "原品牌ID不能为空")
    @MinSize(value = 1, message = "原品牌ID不能为空")
    @JsonField
    private List<Long> originalIds;

    @Min(value = 1, message = "目标品牌ID不能为空")
    private long targetId;

    /**
     * 是否需要删除原品牌
     */
    private int  needDelete;

    public List<Long> getOriginalIds() {
        return originalIds;
    }

    public void setOriginalIds(List<Long> originalIds) {
        this.originalIds = originalIds;
    }

    public long getTargetId() {
        return targetId;
    }

    public void setTargetId(long targetId) {
        this.targetId = targetId;
    }

    public int getNeedDelete() {
        return needDelete;
    }

    public void setNeedDelete(int needDelete) {
        this.needDelete = needDelete;
    }
}
