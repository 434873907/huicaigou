package com.chengzi.apiunion.procurement.admin.web.pojo.index.styleData;

import com.chengzi.apiunion.common.data.style.ItemStyle;
import com.chengzi.apiunion.common.data.style.data.Image;

/** 
* @author 苏子 
* @date 2018年10月23日
*/
public class ClosetLayout extends BaseAdminStyle{
	
	/**
	 * 大图
	 */
	private Image headerImage;
	/**
	 * 商品列表边距
	 */
	private String footerPadding;
	/**
	 * 列表上移高度
	 */
	private Integer offset;
	/**
	 * 显示阴影,0:否，1：是
	 */
	private int showShadow;
	/**
	 * 商品样式
	 */
	private ItemStyle itemStyle;
	/**
	 * 数据类型
	 */
	private int dataType;
	/**
	 * 数据
	 */
	private String value;
	/**
	 * 一级类目
	 */
	private Long firstCate;
	/**
	 * 二级类目
	 */
	private Long secondCate;
	/**
	 * 三级类目
	 */
	private Long thirdCate;
	
	public Image getHeaderImage() {
		return headerImage;
	}
	public void setHeaderImage(Image headerImage) {
		this.headerImage = headerImage;
	}
	public String getFooterPadding() {
		return footerPadding;
	}
	public void setFooterPadding(String footerPadding) {
		this.footerPadding = footerPadding;
	}
	public Integer getOffset() {
		return offset;
	}
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	public int getShowShadow() {
		return showShadow;
	}
	public void setShowShadow(int showShadow) {
		this.showShadow = showShadow;
	}
	public ItemStyle getItemStyle() {
		return itemStyle;
	}
	public void setItemStyle(ItemStyle itemStyle) {
		this.itemStyle = itemStyle;
	}
	public int getDataType() {
		return dataType;
	}
	public void setDataType(int dataType) {
		this.dataType = dataType;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public Long getFirstCate() {
		return firstCate;
	}

	public void setFirstCate(Long firstCate) {
		this.firstCate = firstCate;
	}

	public Long getSecondCate() {
		return secondCate;
	}

	public void setSecondCate(Long secondCate) {
		this.secondCate = secondCate;
	}

	public Long getThirdCate() {
		return thirdCate;
	}

	public void setThirdCate(Long thirdCate) {
		this.thirdCate = thirdCate;
	}
}
