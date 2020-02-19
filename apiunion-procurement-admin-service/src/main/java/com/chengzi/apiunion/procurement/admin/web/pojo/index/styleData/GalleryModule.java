package com.chengzi.apiunion.procurement.admin.web.pojo.index.styleData;

import java.math.BigDecimal;
import java.util.List;

import com.chengzi.apiunion.common.data.style.IndicatorStyle;
import com.chengzi.apiunion.common.data.style.data.Image;

/** 
* @author 苏子 
* @date 2018年10月24日
*/
public class GalleryModule extends BaseAdminStyle {
    /**
     * 展示风格，0：大图，1：平均，2：小图，9：自定义
     */
    private int            displayStyleType;
    /**
     * 宽高比
     */
    private BigDecimal     proportion;
    /**
     * 填充方式 0：填充，1：按比例
     */
    private int            scaleType;
    /**
     * 轮播间隔时间
     */
    private Long           duration;
    /**
     * 指示器
     */
    private IndicatorStyle indicatorStyle;
    /**
     * 缩放比
     */
    private BigDecimal     scale;
    /**
     * 大图占比
     */
    private BigDecimal     itemWidthProportion;
    /**
     * 图片间距
     */
    private Integer        columnSpacing;
    /**
     * 图片
     */
    private List<Image>    imageList;

    public int getDisplayStyleType() {
        return displayStyleType;
    }

    public void setDisplayStyleType(int displayStyleType) {
        this.displayStyleType = displayStyleType;
    }

    public BigDecimal getProportion() {
        return proportion;
    }

    public void setProportion(BigDecimal proportion) {
        this.proportion = proportion;
    }

    public int getScaleType() {
        return scaleType;
    }

    public void setScaleType(int scaleType) {
        this.scaleType = scaleType;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public IndicatorStyle getIndicatorStyle() {
        return indicatorStyle;
    }

    public void setIndicatorStyle(IndicatorStyle indicatorStyle) {
        this.indicatorStyle = indicatorStyle;
    }

    public BigDecimal getScale() {
        return scale;
    }

    public void setScale(BigDecimal scale) {
        this.scale = scale;
    }

    public BigDecimal getItemWidthProportion() {
        return itemWidthProportion;
    }

    public void setItemWidthProportion(BigDecimal itemWidthProportion) {
        this.itemWidthProportion = itemWidthProportion;
    }

    public Integer getColumnSpacing() {
        return columnSpacing;
    }

    public void setColumnSpacing(Integer columnSpacing) {
        this.columnSpacing = columnSpacing;
    }

    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }
}
