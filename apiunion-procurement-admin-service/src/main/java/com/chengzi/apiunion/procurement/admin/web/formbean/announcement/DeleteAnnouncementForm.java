package com.chengzi.apiunion.procurement.admin.web.formbean.announcement;

import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;

/**
 * @author 月汐
 * @date 2018/10/22 19:51
 */
public class DeleteAnnouncementForm extends BaseForm {

    @Min(value = 1, message = "请选择正确的公告")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
