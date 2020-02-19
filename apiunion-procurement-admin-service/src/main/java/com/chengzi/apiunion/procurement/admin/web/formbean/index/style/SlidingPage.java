package com.chengzi.apiunion.procurement.admin.web.formbean.index.style;

import java.math.BigDecimal;
import java.util.List;

import com.chengzi.apiunion.common.data.style.data.Image;
import com.chengzi.common.data.validate.oval.annonation.NotEmptyAndNull;

/** 
* @author 苏子 
* @date 2018年10月24日
*/
public class SlidingPage extends BaseFormStyle {
    /**
     * 展示风格，0：大图，1：平均，2：小图，9：自定义
     */
    private int         displayStyleType;
    /**
     * 单图占比，最大1
     */
    private BigDecimal  imgWidthProportion;
    /**
     * 图片宽高比例
     */
    private BigDecimal  proportion;
    /**
     * 图片间距
     */
    private Integer     columnSpacing;
    /**
     * 填充方式 0：填充，1：按比例
     */
    private int         scaleType;
    /**
     * 图片
     */
    @NotEmptyAndNull(message="请填写正确数据")
    private List<Image> imageList;

    public int getDisplayStyleType() {
        return displayStyleType;
    }

    public void setDisplayStyleType(int displayStyleType) {
        this.displayStyleType = displayStyleType;
    }

    public BigDecimal getImgWidthProportion() {
        return imgWidthProportion;
    }

    public void setImgWidthProportion(BigDecimal imgWidthProportion) {
        this.imgWidthProportion = imgWidthProportion;
    }

    public BigDecimal getProportion() {
        return proportion;
    }

    public void setProportion(BigDecimal proportion) {
        this.proportion = proportion;
    }

    public Integer getColumnSpacing() {
        return columnSpacing;
    }

    public void setColumnSpacing(Integer columnSpacing) {
        this.columnSpacing = columnSpacing;
    }

    public int getScaleType() {
        return scaleType;
    }

    public void setScaleType(int scaleType) {
        this.scaleType = scaleType;
    }

    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }
}
