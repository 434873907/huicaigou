package com.chengzi.apiunion.procurement.admin.web.controllers.activity;

import com.alibaba.fastjson.JSON;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.activity.UpdateFlashSaleItemIndexForm;
import com.chengzi.apiunion.sales.pojo.FlashSaleActivityDO;
import com.chengzi.apiunion.sales.pojo.ParticipateItem;
import com.chengzi.apiunion.sales.service.ActivityService;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * </p>
 * User: 摩天
 * Date: 2019-02-20 16:30
 */
public class UpdateFlashSaleItemIndexController extends AbstractApiController<UpdateFlashSaleItemIndexForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, UpdateFlashSaleItemIndexForm command) throws Exception {
        ActivityService activityService = BeanFactory.getBean(ActivityService.class);
        FlashSaleActivityDO flashSaleActivityDO = activityService.getFlashSaleActivity(command.getActivityId());

        if (flashSaleActivityDO.getEndTime().before(new Date())) {
            return Result.buildOpFailedResult("活动已开始无法编辑");
        }
        int index = command.getIndex();
        int direction = command.getDirection();
        long itemId = command.getItemId();
        List<ParticipateItem> participateItems = JSON.parseArray(flashSaleActivityDO.getParticipateItemNum(), ParticipateItem.class);
        Collections.sort(participateItems);
        int curPoint = 0;
        for (int i = 0; i < participateItems.size(); i++) {
            ParticipateItem participateItem = participateItems.get(i);
            if (participateItem.getItemId() == itemId) {
                curPoint = i;
            }
        }

        ParticipateItem curParticipateItem = participateItems.get(curPoint);
        int curIndex = curParticipateItem.getIndex();
        if (curIndex != index) {
            return Result.buildOpFailedResult("商品位置已变动，无法移动，请刷新后重试");
        }
        if (direction == 1) {
            if (curPoint > 0) {
                ParticipateItem upParticipateItem = participateItems.get(curPoint - 1);
                curParticipateItem.setIndex(upParticipateItem.getIndex());
                upParticipateItem.setIndex(curIndex);
            } else {
                return Result.buildOpFailedResult("已经到顶了！");
            }
        } else {
            if (curPoint <= participateItems.size() - 2) {
                ParticipateItem downParticipateItem = participateItems.get(curPoint + 1);
                curParticipateItem.setIndex(downParticipateItem.getIndex());
                downParticipateItem.setIndex(curIndex);
            } else {
                return Result.buildOpFailedResult("已经到底了！");
            }
        }

        flashSaleActivityDO.setParticipateItemNum(participateItems.toString());
        return activityService.updateFlashSaleActivity(flashSaleActivityDO);
    }
}
