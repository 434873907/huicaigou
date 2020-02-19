package com.chengzi.apiunion.procurement.admin.web.pojo.activity;

import java.util.Date;
import java.util.List;

import com.chengzi.apiunion.common.module.image.annotation.ImageDecorater;
import com.chengzi.apiunion.common.module.image.enums.ImageBizType;
import com.chengzi.apiunion.sales.enums.ActTypeEnum;
import com.chengzi.apiunion.sales.pojo.ActivityDO;
import com.chengzi.apiunion.sales.pojo.ActivityScope;
import com.chengzi.apiunion.sales.pojo.BannerImage;
import com.chengzi.apiunion.sales.pojo.strategy.BaseStrategy;
import com.chengzi.common.data.beans.JsonPojo;

/** 
* @author 苏子 
* @date 2018年10月19日
*/
public class ActivityBO extends JsonPojo {

    /**
     * 主键
     */
    private long            id;
    /**
     * 活动名称
     */
    private String          actName;
    /**
     * 活动类型 {@link ActTypeEnum}
     */
    private int             actType;
    /**
     * 活动策略JSON {@link ? extends BaseStrategy}
     */
    private BaseStrategy    actStrategy;
    /**
     * 参与活动的商品范围JSON
     * {@link ActivityScope}
     */
    @ImageDecorater(ImageBizType.ADMIN_ITEM_LIST)
    private List<ActItemBO> actScope;
    /**
     * 不参与活动的商品范围JSON
     * {@link ActivityScope}
     */
    private List<ActItemBO> excludeScope;
    /**
     * 前置商品  置顶商品idList
     */
    private List<ActItemBO> headItemList;
    /**
     * 海报图 {@link BannerImage } 
     */
    private BannerImage     bannerImage;
    /**
     * 活动描述（图文详情）
     */
    private String          summary;
    /**
     * 开始时间
     */
    private Date            startTime;
    /**
     * 结束时间
     */
    private Date            endTime;

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

    public int getActType() {
        return actType;
    }

    public void setActType(int actType) {
        this.actType = actType;
    }

    public BaseStrategy getActStrategy() {
        return actStrategy;
    }

    public void setActStrategy(BaseStrategy actStrategy) {
        this.actStrategy = actStrategy;
    }

    public List<ActItemBO> getActScope() {
        return actScope;
    }

    public void setActScope(List<ActItemBO> actScope) {
        this.actScope = actScope;
    }

    public List<ActItemBO> getExcludeScope() {
        return excludeScope;
    }

    public void setExcludeScope(List<ActItemBO> excludeScope) {
        this.excludeScope = excludeScope;
    }

    public List<ActItemBO> getHeadItemList() {
        return headItemList;
    }

    public void setHeadItemList(List<ActItemBO> headItemList) {
        this.headItemList = headItemList;
    }

    public BannerImage getBannerImage() {
        return bannerImage;
    }

    public void setBannerImage(BannerImage bannerImage) {
        this.bannerImage = bannerImage;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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

    /*------------------------------------------------------------*/

    public static ActivityBO converter(ActivityDO activity) {
        if (activity == null) {
            return null;
        }
        ActivityBO activityBO = new ActivityBO();
        activityBO.setId(activity.getId());
        activityBO.setActName(activity.getActName());
        activityBO.setActType(activity.getActType());
        activityBO.setSummary(activity.getSummary());
        activityBO.setStartTime(activity.getStartTime());
        activityBO.setEndTime(activity.getEndTime());
        return activityBO;
    }
}
