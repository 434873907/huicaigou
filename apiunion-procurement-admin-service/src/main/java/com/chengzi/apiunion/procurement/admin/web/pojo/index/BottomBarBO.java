package com.chengzi.apiunion.procurement.admin.web.pojo.index;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.chengzi.apiunion.index.pojo.BottomBarDO;
import com.chengzi.apiunion.index.pojo.BottomBarData;
import com.chengzi.common.data.beans.JsonPojo;

/** 
* @author 苏子 
* @date 2018年10月16日
*/
public class BottomBarBO extends JsonPojo{

	/**
     * 主键ID
     */
	private long id;
	/**
	 * 数据
	 * {@link BottomBarData}
	 */
	private List<BottomBarData> data;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public List<BottomBarData> getData() {
		return data;
	}
	public void setData(List<BottomBarData> data) {
		this.data = data;
	}
	
	/*--------------------------------------------------------*/
	
	public static BottomBarBO converter(BottomBarDO bottomBar){
		if (bottomBar == null) {
			return null;
		}
		BottomBarBO bottomBarBO = new BottomBarBO();
		bottomBarBO.setId(bottomBar.getId());
		bottomBarBO.setData(JSONArray.parseArray(bottomBar.getData(), BottomBarData.class));
		return bottomBarBO;
	}
	
}
