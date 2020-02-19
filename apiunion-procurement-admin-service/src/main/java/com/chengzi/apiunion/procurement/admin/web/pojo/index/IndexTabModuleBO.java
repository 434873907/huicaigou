package com.chengzi.apiunion.procurement.admin.web.pojo.index;

import com.chengzi.apiunion.procurement.admin.web.pojo.index.styleData.BaseAdminStyle;
import com.chengzi.common.data.beans.JsonPojo;

/** 
* @author 苏子 
* @date 2018年10月8日
*/
public class IndexTabModuleBO extends JsonPojo{
	
	/**
     * 主键ID
     */
    private long id;
	/**
	 * tab表id
	 */
	private long tabId;
	/**
	 * 布局类型 
	 * {@link} LayoutTypeEnum
	 */
	private int layoutType;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 样式
	 */
	private BaseAdminStyle styleAndData;
	/**
	 * 排序
	 */
	private int order;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
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
	public BaseAdminStyle getStyleAndData() {
		return styleAndData;
	}
	public void setStyleAndData(BaseAdminStyle styleAndData) {
		this.styleAndData = styleAndData;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
}
