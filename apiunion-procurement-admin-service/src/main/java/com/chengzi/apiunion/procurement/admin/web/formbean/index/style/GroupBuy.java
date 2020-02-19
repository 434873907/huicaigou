package com.chengzi.apiunion.procurement.admin.web.formbean.index.style;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author 月汐
 * @date 2019/05/16 17:13
 */
public class GroupBuy extends BaseFormStyle {

    /**
     * 最高显示行数
     */
    private int maxLine;

    /**
     * 按钮风格
     */
    private int buttonStyle;

    /**
     * 展示内容
     */
    private List<Integer> displayContent;

    public int getMaxLine() {
        return maxLine;
    }

    public void setMaxLine(int maxLine) {
        this.maxLine = maxLine;
    }

    public int getButtonStyle() {
        return buttonStyle;
    }

    public void setButtonStyle(int buttonStyle) {
        this.buttonStyle = buttonStyle;
    }

    public List<Integer> getDisplayContent() {
        return displayContent;
    }

    public void setDisplayContent(List<Integer> displayContent) {
        this.displayContent = displayContent;
    }

    public static GroupBuy parse(String json) {
        return JSONObject.parseObject(json, GroupBuy.class);
    }
}
