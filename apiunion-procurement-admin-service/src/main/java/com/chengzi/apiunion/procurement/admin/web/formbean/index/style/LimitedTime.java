package com.chengzi.apiunion.procurement.admin.web.formbean.index.style;

import com.alibaba.fastjson.JSONObject;
import com.chengzi.apiunion.common.data.style.ItemStyle;

/** 
* @author 苏子 
* @date 2018年10月24日
*/
public class LimitedTime extends BaseFormStyle{
	/**
	 * 商品展示风格，0：宽松，1：紧凑，9：自定义
	 */
	private int productDisplayStyle;
	/**
	 * 显示阴影,0:否，1：是
	 */
	private int showShadow;
	/**
	 * 倒计时颜色
	 */
	private String countdownColor;
	/**
	 * 商品样式
	 */
	private ItemStyle itemStyle;
	
	public int getProductDisplayStyle() {
		return productDisplayStyle;
	}
	public void setProductDisplayStyle(int productDisplayStyle) {
		this.productDisplayStyle = productDisplayStyle;
	}
	public int getShowShadow() {
		return showShadow;
	}
	public void setShowShadow(int showShadow) {
		this.showShadow = showShadow;
	}
	public String getCountdownColor() {
		return countdownColor;
	}
	public void setCountdownColor(String countdownColor) {
		this.countdownColor = countdownColor;
	}
	public ItemStyle getItemStyle() {
		return itemStyle;
	}
	public void setItemStyle(ItemStyle itemStyle) {
		this.itemStyle = itemStyle;
	}

	public static LimitedTime parse(String json) {
		return JSONObject.parseObject(json, LimitedTime.class);
	}
}
