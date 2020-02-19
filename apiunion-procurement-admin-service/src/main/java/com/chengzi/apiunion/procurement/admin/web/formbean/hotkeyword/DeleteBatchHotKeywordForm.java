package com.chengzi.apiunion.procurement.admin.web.formbean.hotkeyword;

import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.MinSize;

import java.util.List;

/**
 * @author 月汐
 * @date 2018/12/4 14:37
 */
public class DeleteBatchHotKeywordForm extends BaseForm {

    @MinSize(value = 1, message = "请选择正确的热门关键词")
    private List<Long> ids;

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}
