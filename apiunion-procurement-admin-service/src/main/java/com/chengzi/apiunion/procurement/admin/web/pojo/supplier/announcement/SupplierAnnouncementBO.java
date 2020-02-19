package com.chengzi.apiunion.procurement.admin.web.pojo.supplier.announcement;

import com.chengzi.common.data.beans.JsonPojo;

import java.util.Date;

/**
 * @author 月汐
 * @date 2020/01/16 14:42
 */
public class SupplierAnnouncementBO extends JsonPojo {

    /**
     * id
     */
    private long   id;

    /**
     * 公告标题
     */
    private String title;

    /**
     * 发布时间
     */
    private Date   createTime;

    /**
     * 发布人
     */
    private String creator;

    /**
     * 公告内容
     */
    private String content;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
