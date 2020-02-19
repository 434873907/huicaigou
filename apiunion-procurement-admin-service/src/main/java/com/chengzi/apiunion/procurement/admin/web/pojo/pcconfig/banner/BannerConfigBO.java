package com.chengzi.apiunion.procurement.admin.web.pojo.pcconfig.banner;

import java.util.Date;

import com.chengzi.apiunion.common.data.style.data.Image;
import com.chengzi.apiunion.common.jump.Jump;
import com.chengzi.apiunion.pcconfig.banner.pojo.BannerConfigDO;
import com.chengzi.common.data.beans.JsonPojo;

/**
 * @author 苏子
 * @date 2019年1月17日
 */
public class BannerConfigBO extends JsonPojo {

    private long   id;
    /**
     * 标题
     */
    private String title;
    /**
     * 图片链接
     */
    private String imageUrl;
    /**
     * 跳转
     */
    private Jump   jump;
    /**
     * 开始时间
     */
    private Date   startTime;
    /**
     * 结束时间
     */
    private Date   endTime;
    /**
     * 状态，0：禁用，1：启用
     */
    private int    status;

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Jump getJump() {
        return jump;
    }

    public void setJump(Jump jump) {
        this.jump = jump;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static BannerConfigBO convert(BannerConfigDO bannerConfig) {
        if (bannerConfig == null) {
            return null;
        }
        BannerConfigBO bo = new BannerConfigBO();
        bo.setId(bannerConfig.getId());
        Image image = Image.parse(bannerConfig.getImage());
        bo.setImageUrl(image.getUrl());
        bo.setTitle(bannerConfig.getTitle());
        bo.setJump(Jump.parse(bannerConfig.getJump()));
        bo.setStartTime(bannerConfig.getStartTime());
        bo.setEndTime(bannerConfig.getEndTime());
        bo.setStatus(bannerConfig.getStatus());
        return bo;
    }

}
