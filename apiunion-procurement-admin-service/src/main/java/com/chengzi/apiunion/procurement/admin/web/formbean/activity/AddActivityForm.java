package com.chengzi.apiunion.procurement.admin.web.formbean.activity;

import java.util.Date;
import java.util.List;

import net.sf.oval.constraint.Min;
import net.sf.oval.constraint.NotNull;
import org.summercool.web.annotation.JsonField;

import com.chengzi.apiunion.sales.pojo.ActivityScope;
import com.chengzi.apiunion.sales.pojo.BannerImage;
import com.chengzi.common.web.formbean.BaseForm;

/** 
* @author 苏子 
* @date 2018年10月18日
*/
public class AddActivityForm extends BaseForm {

    /**
     * 活动名称
     */
    private String              actName;
    /**
     * 活动类型 {@link ActTypeEnum}
     */
    @Min(value = 1, message = "请选择正确的活动类型")
    private int                 actType;
    /**
     * 活动策略JSON {@link ? extends BaseStrategy}
     */
    private String              actStrategy;
    /**
     * 参与活动的商品范围JSON 商品idList
     */
    @JsonField
    private ActivityScope actScope;
    /**
     * 不参与活动的商品范围JSON 商品idList
     */
    @JsonField
    private ActivityScope excludeScope;
    /**
     * 前置商品  置顶商品idList
     */
    @JsonField
    private List<Long>          headItemIds;
    /**
     * 海报图-PC
     */
    @JsonField
    private BannerImage         bannerImage;
    /**
     * 活动描述（图文详情）
     */
    private String              summary;
    /**
     * 开始时间
     */
    @NotNull(message = "请选择起始日期")
    private Date                startTime;
    /**
     * 结束时间
     */
    @NotNull(message = "请选择结束日期")
    private Date                endTime;

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

    public String getActStrategy() {
        return actStrategy;
    }

    public void setActStrategy(String actStrategy) {
        this.actStrategy = actStrategy;
    }

    public ActivityScope getActScope() {
        return actScope;
    }

    public void setActScope(ActivityScope actScope) {
        this.actScope = actScope;
    }

    public ActivityScope getExcludeScope() {
        return excludeScope;
    }

    public void setExcludeScope(ActivityScope excludeScope) {
        this.excludeScope = excludeScope;
    }

    public List<Long> getHeadItemIds() {
        return headItemIds;
    }

    public void setHeadItemIds(List<Long> headItemIds) {
        this.headItemIds = headItemIds;
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

}
