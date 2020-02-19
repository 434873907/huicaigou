package com.chengzi.apiunion.procurement.admin.web.controllers.activity;

import com.alibaba.fastjson.JSON;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.activity.UpdateFlashSaleActivityForm;
import com.chengzi.apiunion.sales.pojo.FlashSaleActivityDO;
import com.chengzi.apiunion.sales.pojo.ParticipateItem;
import com.chengzi.apiunion.sales.service.ActivityService;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * </p>
 * User: 摩天
 * Date: 2019-02-20 16:14
 */
public class UpdateFlashSaleActivityController extends AbstractApiController<UpdateFlashSaleActivityForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, UpdateFlashSaleActivityForm command) throws Exception {
        ActivityService activityService = BeanFactory.getBean(ActivityService.class);
        FlashSaleActivityDO flashSaleActivityDO = activityService.getFlashSaleActivity(command.getActivityId());

        flashSaleActivityDO.setStartTime(command.getStartTime());
        flashSaleActivityDO.setEndTime(command.getEndTime());
        flashSaleActivityDO.setIsDisableCoupon(command.getIsDisableCoupon());
        flashSaleActivityDO.setCurrency(command.getCurrency());
        List<ParticipateItem> participateItems = JSON.parseArray(command.getParticipateItemNum(), ParticipateItem.class);
        Result result = ParticipateItem.checkData(participateItems);
        if (!result.isSuccess()) {
            return result;
        }
        flashSaleActivityDO.setParticipateItemNum(participateItems.toString());
        return activityService.updateFlashSaleActivity(flashSaleActivityDO);
    }
}
