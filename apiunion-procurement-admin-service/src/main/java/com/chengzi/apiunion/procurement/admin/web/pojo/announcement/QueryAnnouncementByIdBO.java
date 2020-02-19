package com.chengzi.apiunion.procurement.admin.web.pojo.announcement;

import com.chengzi.apiunion.common.module.announcement.pojo.AnnouncementDO;
import com.chengzi.common.data.beans.JsonPojo;

import java.util.Date;

/**
 * @author 月汐
 * @date 2018/10/22 20:52
 */
public class QueryAnnouncementByIdBO extends JsonPojo {

    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 正文
     */
    private String content;

    /**
     * 跳转类型
     */
    private Integer type;

    /**
     * 跳转链接
     */
    private String url;

    /**
     * 发布日期
     */
    private Date issueTime;

    /**
     * 失效时间
     */
    private Date            invalidTime;

    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public static QueryAnnouncementByIdBO convert(AnnouncementDO announcementDO) {
        QueryAnnouncementByIdBO bo = new QueryAnnouncementByIdBO();
        bo.setId(announcementDO.getId());
        bo.setTitle(announcementDO.getTitle());
        bo.setContent(announcementDO.getContent());
        bo.setType(announcementDO.getType());
        bo.setUrl(announcementDO.getUrl());
        bo.setIssueTime(announcementDO.getIssueTime());
        bo.setInvalidTime(announcementDO.getInvalidTime());
        bo.setStatus(announcementDO.getStatus());
        return bo;
    }

}
