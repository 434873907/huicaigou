package com.chengzi.apiunion.procurement.admin.web.controllers.activity;

import com.alibaba.fastjson.JSON;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.activity.AddFlashSaleActivityForm;
import com.chengzi.apiunion.sales.pojo.FlashSaleActivityDO;
import com.chengzi.apiunion.sales.pojo.ParticipateItem;
import com.chengzi.apiunion.sales.service.ActivityService;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * </p>
 * User: 摩天
 * Date: 2019-02-20 15:48
 */
public class AddFlashSaleActivityController extends AbstractApiController<AddFlashSaleActivityForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, AddFlashSaleActivityForm command) throws Exception {
        ActivityService activityService = BeanFactory.getBean(ActivityService.class);
        FlashSaleActivityDO flashSaleActivityDO = new FlashSaleActivityDO();
        flashSaleActivityDO.setStartTime(command.getStartTime());
        flashSaleActivityDO.setEndTime(command.getEndTime());
        flashSaleActivityDO.setIsDisableCoupon(command.getIsDisableCoupon());
        List<ParticipateItem> participateItems = JSON.parseArray(command.getParticipateItemNum(), ParticipateItem.class);
        Result result = ParticipateItem.checkData(participateItems);
        if (!result.isSuccess()) {
            return result;
        }
        flashSaleActivityDO.setParticipateItemNum(participateItems.toString());
        return activityService.addFlashSaleActivity(flashSaleActivityDO);
    }
}
