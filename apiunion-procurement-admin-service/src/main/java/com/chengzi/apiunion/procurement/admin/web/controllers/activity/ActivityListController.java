package com.chengzi.apiunion.procurement.admin.web.controllers.activity;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.activity.ActivityListForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.activity.ActivityBO;
import com.chengzi.apiunion.procurement.admin.web.pojo.activity.ActivityListBO;
import com.chengzi.apiunion.sales.pojo.ActivityDO;
import com.chengzi.apiunion.sales.pojo.ActivityListQuery;
import com.chengzi.apiunion.sales.service.ActivityService;
import com.chengzi.common.data.beans.PageDataList;
import com.chengzi.common.data.validate.Result;

/** 
* @author 苏子 
* @date 2018年10月18日
*/
public class ActivityListController extends AbstractApiController<ActivityListForm>{

	@Override
	protected Result<PageDataList<ActivityListBO>> doBiz(HttpServletRequest request, HttpServletResponse response, ActivityListForm command)
			throws Exception {
		
		int limit = command.getLimit();
		int page = command.getPage();
		int actType = command.getActType();
		int status = command.getStatus();
		String keyword = command.getKeyword();
		int displayTerminal = command.getDisplayTerminal();
		
		//查询参数
		ActivityListQuery query = new ActivityListQuery();
		query.setActType(actType);
		query.setStatus(status);
		query.setKeyword(keyword);
		query.setDisplayTerminal(displayTerminal);
		query.setLimit(command.getLimit());
		query.setOffset(command.getOffset());
		
		ActivityService activityService = BeanFactory.getBean(ActivityService.class);
		int count = activityService.countActivitys(query);
		List<ActivityDO> activityList = activityService.queryActivitys(query);
		List<ActivityListBO> activityBOList = ActivityListBO.converter(activityList);
		
		//返回数据
		PageDataList<ActivityListBO> pageData = new PageDataList<>(count,page,limit,activityBOList);
		return Result.buildSuccessResult(pageData);
	}
	
}
