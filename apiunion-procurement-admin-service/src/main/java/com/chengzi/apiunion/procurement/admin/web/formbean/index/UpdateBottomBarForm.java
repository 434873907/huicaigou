package com.chengzi.apiunion.procurement.admin.web.formbean.index;

import com.chengzi.apiunion.index.pojo.BottomBarData;
import com.chengzi.common.web.formbean.BaseForm;

/** 
* @author 苏子 
* @date 2018年10月16日
*/
public class UpdateBottomBarForm extends BaseForm{

	/**
     * 主键ID
     */
	private long id;
	/**
	 * 数据
	 * {@link BottomBarData}
	 */
	private String data;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
}
