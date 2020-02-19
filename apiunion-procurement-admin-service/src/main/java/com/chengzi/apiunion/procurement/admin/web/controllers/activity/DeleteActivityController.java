package com.chengzi.apiunion.procurement.admin.web.controllers.activity;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.activity.DeleteActivityForm;
import com.chengzi.apiunion.sales.service.ActivityService;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 删除活动
 *
 * @author 月汐
 * @date 2019/1/14 16:50
 */
public class DeleteActivityController extends AbstractApiController<DeleteActivityForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, DeleteActivityForm command) throws Exception {
        ActivityService activityService = BeanFactory.getBean(ActivityService.class);
        activityService.delete(command.getId());
        return Result.buildSuccessResult("删除成功");
    }
}
