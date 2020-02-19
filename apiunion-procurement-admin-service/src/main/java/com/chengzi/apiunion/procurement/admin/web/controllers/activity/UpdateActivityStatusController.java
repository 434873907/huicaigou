package com.chengzi.apiunion.procurement.admin.web.controllers.activity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chengzi.apiunion.sales.ActivityCO;
import com.chengzi.common.util.CollectionUtil;
import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.activity.UpdateActivityStatusForm;
import com.chengzi.apiunion.sales.constant.ActivityConstant;
import com.chengzi.apiunion.sales.service.ActivityService;
import com.chengzi.common.data.validate.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author 苏子
 * @date 2018年11月21日
 */
public class UpdateActivityStatusController extends AbstractApiController<UpdateActivityStatusForm>{

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, UpdateActivityStatusForm command) throws Exception {
        int status = command.getStatus();
        ActivityService activityService = BeanFactory.getBean(ActivityService.class);

        List<Long> mutexActIdList = new ArrayList<>();

        if(status == ActivityConstant.ACTIVITY_STATUS_ENABLE) {
            List<ActivityCO> activityCOS = activityService.queryActiveActivitiesFromRedis();
            Set<Long> idSet = CollectionUtil.getDisctinctFieldValueList(activityCOS, "id");
            for (long id : idSet) {
                if (id != command.getId()) {
                    mutexActIdList.add(id);
                }
            }
        }

        int row = activityService.updateActivityStatus(command.getId(), status, mutexActIdList);
        if(status == ActivityConstant.ACTIVITY_STATUS_ENABLE) {
            return Result.buildSuccessMsg("启用成功");
        }else {
            return Result.buildSuccessMsg("禁用成功");
        }
    }

}
