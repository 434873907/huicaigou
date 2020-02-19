package com.chengzi.apiunion.procurement.admin.web.pojo.index.styleData;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author 月汐
 * @date 2019/05/16 17:45
 */
public class GroupBuy extends BaseAdminStyle {

    /**
     * 最大商品行数
     */
    private int maxLine;
    /**
     * 按钮样式
     */
    private int buttonStyle;
    /**
     * 商品展示内容
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

    public static com.chengzi.apiunion.procurement.admin.web.formbean.index.style.GroupBuy parse(String json) {
        return JSONObject.parseObject(json, com.chengzi.apiunion.procurement.admin.web.formbean.index.style.GroupBuy.class);
    }

}
