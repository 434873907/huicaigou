package com.chengzi.apiunion.procurement.admin.web.controllers.activity;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.activity.GetFlashSaleActivityForm;
import com.chengzi.apiunion.sales.pojo.FlashSaleActivityDO;
import com.chengzi.apiunion.sales.service.ActivityService;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * <p>
 * </p>
 * User: 摩天
 * Date: 2019-02-20 16:46
 */
public class DeleteFlashSaleActivityController extends AbstractApiController<GetFlashSaleActivityForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, GetFlashSaleActivityForm command) throws Exception {
        ActivityService activityService = BeanFactory.getBean(ActivityService.class);
        FlashSaleActivityDO flashSaleActivityDO = activityService.getFlashSaleActivity(command.getActivityId());

        if (flashSaleActivityDO.getEndTime().after(new Date()) && flashSaleActivityDO.getStartTime().before(new Date())) {
            return Result.buildOpFailedResult("活动已开始无法删除");
        }
        int i = activityService.deleteFlashSaleActivity(flashSaleActivityDO.getId());

        if (i>0){
            return Result.buildSuccessMsg("删除成功");
        }
        return Result.buildOpFailedResult("删除失败");


    }
}
