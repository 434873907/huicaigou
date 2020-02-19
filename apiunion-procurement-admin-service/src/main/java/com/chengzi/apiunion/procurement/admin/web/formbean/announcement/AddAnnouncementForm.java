package com.chengzi.apiunion.procurement.admin.web.formbean.announcement;

import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;
import com.chengzi.common.web.formbean.BaseForm;

import java.util.Date;

/**
 * @author 月汐
 * @date 2018/10/22 19:44
 */
public class AddAnnouncementForm extends BaseForm {

    @NotBlankAndNull(message = "标题不能为空")
    private String title;

    @NotBlankAndNull(message = "内容不能为空")
    private String content;

    /**
     * 公告类型
     */
    // @NotBlankAndNull(message = "跳转类型不能为空")
    private Integer type;

    /**
     * 链接地址
     */
    private String url;

    @NotBlankAndNull(message = "发布时间不能为空")
    private Date issueTime;

    @NotBlankAndNull(message = "失效时间不能为空")
    private Date            invalidTime;

    private Integer status;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(Date issueTime) {
        this.issueTime = issueTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getInvalidTime() {
        return invalidTime;
    }

    public void setInvalidTime(Date invalidTime) {
        this.invalidTime = invalidTime;
    }
}
