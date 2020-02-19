package com.chengzi.apiunion.procurement.admin.web.pojo.index.styleData;

import com.chengzi.apiunion.common.data.style.BackgroundStyle;
import com.chengzi.apiunion.common.data.style.DividerStyle;
import com.chengzi.apiunion.common.data.style.TextStyle;

/** 
* @author 苏子 
* @date 2018年10月25日
*/
public class BaseAdminStyle {
    /**
     * 模块样式 0：默认，9：自定义
     */
    private Integer         styleType;
    /**
     * 左、上、右、下边距,英文逗号分隔
     */
    private String          padding;
    /**
     * 背景
     */
    private BackgroundStyle backgroundStyle;
    /**
     * 不显示分隔线 0：否， 1：是
     */
    private Integer         hideDividers;
    /**
     * 分隔线
     */
    private DividerStyle    divider;
    /**
     * 标题风格，0：大字，1：精致，2：不显示
     */
    private Integer         titleStyleType;
    /**
     * 标题样式
     */
    private TextStyle       titleStyle;

    public Integer getStyleType() {
        return styleType;
    }

    public void setStyleType(Integer styleType) {
        this.styleType = styleType;
    }

    public String getPadding() {
        return padding;
    }

    public void setPadding(String padding) {
        this.padding = padding;
    }

    public BackgroundStyle getBackgroundStyle() {
        return backgroundStyle;
    }

    public void setBackgroundStyle(BackgroundStyle backgroundStyle) {
        this.backgroundStyle = backgroundStyle;
    }

    public Integer getHideDividers() {
        return hideDividers;
    }

    public void setHideDividers(Integer hideDividers) {
        this.hideDividers = hideDividers;
    }

    public DividerStyle getDivider() {
        return divider;
    }

    public void setDivider(DividerStyle divider) {
        this.divider = divider;
    }

    public Integer getTitleStyleType() {
        return titleStyleType;
    }

    public void setTitleStyleType(Integer titleStyleType) {
        this.titleStyleType = titleStyleType;
    }

    public TextStyle getTitleStyle() {
        return titleStyle;
    }

    public void setTitleStyle(TextStyle titleStyle) {
        this.titleStyle = titleStyle;
    }

}
