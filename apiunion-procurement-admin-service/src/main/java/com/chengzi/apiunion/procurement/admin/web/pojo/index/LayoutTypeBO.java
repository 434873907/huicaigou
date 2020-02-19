package com.chengzi.apiunion.procurement.admin.web.pojo.index;

import com.chengzi.common.data.beans.JsonPojo;

/** 
* @author 苏子 
* @date 2018年10月9日
*/
public class LayoutTypeBO extends JsonPojo{

	private int    code;
    private String remark;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
