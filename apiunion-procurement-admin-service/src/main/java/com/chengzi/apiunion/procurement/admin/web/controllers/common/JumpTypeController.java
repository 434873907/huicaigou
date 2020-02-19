package com.chengzi.apiunion.procurement.admin.web.controllers.common;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chengzi.apiunion.common.jump.JumpTypeEnum;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.pojo.common.JumpTypeBO;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.EmptyForm;

/** 
* @author 苏子 
* @date 2018年10月9日
*/
public class JumpTypeController extends AbstractApiController<EmptyForm>{
	
	private static final List<JumpTypeBO> JUMP_TYPE_LIST = new ArrayList<>();

    static {
    	JumpTypeEnum[] arr = JumpTypeEnum.values();
        for (JumpTypeEnum jumpType : arr) {
        	JumpTypeBO jumpTypeBO = new JumpTypeBO();
        	jumpTypeBO.setCode(jumpType.getCode());
        	jumpTypeBO.setRemark(jumpType.getRemark());
        	JUMP_TYPE_LIST.add(jumpTypeBO);
        }
    }

	@Override
	protected Result<List<JumpTypeBO>> doBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command)
			throws Exception {
		return Result.buildSuccessResult(JUMP_TYPE_LIST);
	}

}
