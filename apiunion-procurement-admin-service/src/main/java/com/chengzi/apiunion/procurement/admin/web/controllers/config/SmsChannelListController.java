package com.chengzi.apiunion.procurement.admin.web.controllers.config;

import com.chengzi.apiunion.common.module.config.enums.SmsChannelEnum;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.pojo.config.SmsChannelListBO;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.EmptyForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 月汐
 * @date 2019/3/12 15:56
 */
public class SmsChannelListController extends AbstractApiController<EmptyForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {
        List<SmsChannelListBO> boList = new ArrayList<>();

        for (SmsChannelEnum smsChannelEnum : SmsChannelEnum.values()) {
            SmsChannelListBO bo = new SmsChannelListBO();
            bo.setChannel(smsChannelEnum.getCode());
            bo.setChannelName(smsChannelEnum.getDesc());
            boList.add(bo);
        }

        return Result.buildSuccessResult(boList);
    }
}
