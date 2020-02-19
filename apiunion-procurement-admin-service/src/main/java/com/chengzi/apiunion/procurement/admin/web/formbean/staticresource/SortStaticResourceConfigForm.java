package com.chengzi.apiunion.procurement.admin.web.formbean.staticresource;

import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.MinSize;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author 爱夏
 * @date 2018/1/16
 */
public class SortStaticResourceConfigForm extends BaseForm {

    @MinSize(value = 1)
    private List<Long> sortIds;

    public List<Long> getSortIds() {
        return sortIds;
    }

    public void setSortIds(List<Long> sortIds) {
        this.sortIds = sortIds;
    }
}
