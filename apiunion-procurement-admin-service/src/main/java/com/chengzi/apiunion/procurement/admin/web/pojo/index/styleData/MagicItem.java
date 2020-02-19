package com.chengzi.apiunion.procurement.admin.web.pojo.index.styleData;

import com.chengzi.apiunion.common.data.style.data.Image;

/** 
* @author 苏子 
* @date 2018年10月24日
*/
public class MagicItem {
	/**
	 * 起点X坐标
	 */
	private int startX;
	/**
	 * 起点Y坐标
	 */
	private int startY;
	/**
	 * 终点X坐标
	 */
	private int endX;
	/**
	 * 终点Y坐标
	 */
	private int endY;
	/**
	 * 数据
	 */
	private Image image;
	
	public int getStartX() {
		return startX;
	}
	public void setStartX(int startX) {
		this.startX = startX;
	}
	public int getStartY() {
		return startY;
	}
	public void setStartY(int startY) {
		this.startY = startY;
	}
	public int getEndX() {
		return endX;
	}
	public void setEndX(int endX) {
		this.endX = endX;
	}
	public int getEndY() {
		return endY;
	}
	public void setEndY(int endY) {
		this.endY = endY;
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
}
