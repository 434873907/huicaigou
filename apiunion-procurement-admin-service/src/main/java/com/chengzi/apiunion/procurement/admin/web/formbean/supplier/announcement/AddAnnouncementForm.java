package com.chengzi.apiunion.procurement.admin.web.formbean.supplier.announcement;

import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;
import com.chengzi.common.web.formbean.BaseForm;

/**
 * @author 月汐
 * @date 2020/01/16 15:48
 */
public class AddAnnouncementForm extends BaseForm {

    /**
     * 公告标题
     */
    @NotBlankAndNull(message = "请输入公告标题")
    private String title;

    /**
     * 公告内容
     */
    @NotBlankAndNull(message = "请输入公告内容")
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
