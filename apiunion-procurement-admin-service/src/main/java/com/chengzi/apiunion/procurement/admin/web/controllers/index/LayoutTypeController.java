package com.chengzi.apiunion.procurement.admin.web.controllers.index;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.index.enums.LayoutTypeEnum;
import com.chengzi.apiunion.procurement.admin.web.pojo.index.LayoutTypeBO;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.EmptyForm;

/** 
* @author 苏子 
* @date 2018年10月9日
*/
public class LayoutTypeController extends AbstractApiController<EmptyForm>{
	
	private static final List<LayoutTypeBO> LAYOUT_TYPE_LIST = new ArrayList<>();

    static {
    	LayoutTypeEnum[] arr = LayoutTypeEnum.values();
        for (LayoutTypeEnum layoutType : arr) {
			if (layoutType == LayoutTypeEnum.GROUP_BUY) {
				continue;
			}
        	LayoutTypeBO layoutTypeBO = new LayoutTypeBO();
        	layoutTypeBO.setCode(layoutType.getCode());
        	layoutTypeBO.setRemark(layoutType.getRemark());
        	LAYOUT_TYPE_LIST.add(layoutTypeBO);
        }
    }

	@Override
	protected Result<List<LayoutTypeBO>> doBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command)
			throws Exception {
		return Result.buildSuccessResult(LAYOUT_TYPE_LIST);
	}

}
