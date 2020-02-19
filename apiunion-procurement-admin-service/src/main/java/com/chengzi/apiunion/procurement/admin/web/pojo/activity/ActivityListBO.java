package com.chengzi.apiunion.procurement.admin.web.pojo.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.chengzi.apiunion.sales.enums.ActTypeEnum;
import com.chengzi.apiunion.sales.enums.DisplayTerminalEnum;
import com.chengzi.apiunion.sales.pojo.ActivityDO;
import com.chengzi.apiunion.sales.pojo.BannerImage;
import com.chengzi.common.data.beans.JsonPojo;
import com.chengzi.common.util.CollectionUtil;
import com.chengzi.common.util.EnumUtil;
import com.chengzi.common.util.StringUtil;
import org.apache.commons.lang3.StringUtils;

/** 
* @author 苏子 
* @date 2018年10月19日
*/
public class ActivityListBO extends JsonPojo {

    /**
     * 主键
     */
    private long   id;
    /**
     * 活动名称
     */
    private String actName;
    /**
     * 活动类型 {@link ActTypeEnum}
     */
    private String actTypeStr;
    /**
     * 开始时间
     */
    private Date   startTime;
    /**
     * 结束时间
     */
    private Date   endTime;
    /**
     * 活动状态 0：未启用，1：启用
     */
    private int    status;
    /**
     * 活动状态 0：未启用，1：启用
     */
    private String statusStr;
    /**
     * PC显示 0：否，1：是
     */
    private int    displayPC;
    /**
     * APP显示 0：否，1：是
     */
    private int    displayAPP;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public String getActTypeStr() {
        return actTypeStr;
    }

    public void setActTypeStr(String actTypeStr) {
        this.actTypeStr = actTypeStr;
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

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public int getDisplayPC() {
        return displayPC;
    }

    public void setDisplayPC(int displayPC) {
        this.displayPC = displayPC;
    }

    public int getDisplayAPP() {
        return displayAPP;
    }

    public void setDisplayAPP(int displayAPP) {
        this.displayAPP = displayAPP;
    }

    /*------------------------------------------------------------*/

    public static List<ActivityListBO> converter(List<ActivityDO> activityList) {
        if (CollectionUtil.isEmpty(activityList)) {
            return null;
        }
        List<ActivityListBO> activityBOList = new ArrayList<>();
        for (ActivityDO activity : activityList) {
            ActivityListBO bo = converter(activity);
            if (bo != null) {
                activityBOList.add(bo);
            }
        }
        return activityBOList;
    }

    public static ActivityListBO converter(ActivityDO activity) {
        if (activity == null) {
            return null;
        }
        ActivityListBO activityBO = new ActivityListBO();
        activityBO.setId(activity.getId());
        activityBO.setActName(activity.getActName());
        ActTypeEnum actTypeEnum = EnumUtil.parse(ActTypeEnum.class, activity.getActType());
        activityBO.setActTypeStr(actTypeEnum.getRemark());
        activityBO.setStartTime(activity.getStartTime());
        Date endTime = activity.getEndTime();
        activityBO.setEndTime(endTime);
        int status = activity.getStatus();
        if (endTime.getTime() < System.currentTimeMillis()) {
            activityBO.setStatusStr("已结束");
        } else if (status == 0) {
            activityBO.setStatusStr("未开启");
        } else {
            activityBO.setStatusStr("进行中");
        }
        activityBO.setStatus(status);
        int displayTerminal = activity.getDisplayTerminal();
        String bannerImageJson = activity.getBannerImage();
        BannerImage bannerImage = BannerImage.fromJson(bannerImageJson);
        if (StringUtils.isNotEmpty(bannerImage.getBannerImageUrlForPC())) {
            activityBO.setDisplayPC(1);
        }
        if (StringUtils.isNotEmpty(bannerImage.getBannerImageUrlForAPP())) {
            activityBO.setDisplayAPP(1);
        }
        return activityBO;
    }
}
