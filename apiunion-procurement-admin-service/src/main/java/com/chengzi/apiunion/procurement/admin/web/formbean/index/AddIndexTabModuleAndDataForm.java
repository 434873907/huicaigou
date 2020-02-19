package com.chengzi.apiunion.procurement.admin.web.formbean.index;

import com.chengzi.common.web.formbean.BaseForm;

import net.sf.oval.constraint.Min;

/** 
* @author 苏子 
* @date 2018年10月10日
*/
public class AddIndexTabModuleAndDataForm extends BaseForm{

	/**
	 * tab表id
	 */
	private long tabId;
	/**
	 * 布局类型 
	 * {@link} LayoutTypeEnum
	 */
	@Min(value=1,message="请选择模块！")
	private int layoutType;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 样式
	 */
	private String styleAndData;
	/**
	 * 排序
	 */
	private int order;
	
	public long getTabId() {
		return tabId;
	}
	public void setTabId(long tabId) {
		this.tabId = tabId;
	}
	public int getLayoutType() {
		return layoutType;
	}
	public void setLayoutType(int layoutType) {
		this.layoutType = layoutType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStyleAndData() {
		return styleAndData;
	}
	public void setStyleAndData(String styleAndData) {
		this.styleAndData = styleAndData;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
}
