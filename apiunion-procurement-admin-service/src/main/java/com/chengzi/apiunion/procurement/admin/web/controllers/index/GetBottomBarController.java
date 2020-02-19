package com.chengzi.apiunion.procurement.admin.web.controllers.index;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.index.pojo.BottomBarDO;
import com.chengzi.apiunion.index.service.BottomBarService;
import com.chengzi.apiunion.procurement.admin.web.pojo.index.BottomBarBO;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.EmptyForm;

/** 
* @author 苏子 
* @date 2018年10月16日
*/
public class GetBottomBarController extends AbstractApiController<EmptyForm>{

	@Override
	protected Result<BottomBarBO> doBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command)
			throws Exception {
		BottomBarService bottomBarService = BeanFactory.getBean(BottomBarService.class);
		BottomBarDO bottomBar = bottomBarService.getBottomBar();
		BottomBarBO bottomBarBO  = BottomBarBO.converter(bottomBar);
		return Result.buildSuccessResult(bottomBarBO);
	}

}
