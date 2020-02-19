package com.chengzi.apiunion.procurement.admin.web.formbean.announcement;

import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;
import com.chengzi.common.web.formbean.BaseForm;
import net.sf.oval.constraint.Min;

import java.util.Date;

/**
 * @author 月汐
 * @date 2018/10/22 19:58
 */
public class UpdateAnnouncementForm extends BaseForm {

    @Min(value = 1, message = "请选择正确的公告")
    private long id;

    @NotBlankAndNull(message = "请输入标题")
    private String title;

    @NotBlankAndNull(message = "请输入内容")
    private String content;

    /**
     * 链接类型
     * {@link com.chengzi.apiunion.common.jump.JumpTypeEnum}
     */
    private Integer type;

    /**
     * 链接地址
     */
    private String url;

    @NotBlankAndNull(message = "请选择发布时间")
    private Date issueTime;

    @NotBlankAndNull(message = "失效时间不能为空")
    private Date            invalidTime;

    private Integer status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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
