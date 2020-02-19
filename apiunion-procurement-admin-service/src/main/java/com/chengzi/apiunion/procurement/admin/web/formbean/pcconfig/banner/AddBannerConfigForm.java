package com.chengzi.apiunion.procurement.admin.web.formbean.pcconfig.banner;

import java.util.Date;

import org.summercool.web.annotation.JsonField;

import com.chengzi.apiunion.common.jump.Jump;
import com.chengzi.common.data.validate.oval.annonation.NotBlankAndNull;
import com.chengzi.common.web.formbean.BaseForm;

/**
 * @author 苏子
 * @date 2019年1月17日
 */
public class AddBannerConfigForm extends BaseForm {

    /**
     * 标题
     */
    @NotBlankAndNull(message = "请输入标题")
    private String title;
    /**
     * 图片链接
     */
    @NotBlankAndNull(message="请上传图片")
    private String imageUrl;
    /**
     * 跳转
     */
    @JsonField
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
    private int    status = 0;

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
}
