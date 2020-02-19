package com.chengzi.apiunion.procurement.admin.web.controllers.activity;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.pojo.activity.ActivityTypeBO;
import com.chengzi.apiunion.sales.enums.ActTypeEnum;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.EmptyForm;

/**
 * @author 苏子
 * @date 2018年11月13日
 */
public class ActityTypeListController extends AbstractApiController<EmptyForm> {

    private static final List<ActivityTypeBO> ACTIVITY_TYPE_LIST = new ArrayList<>();

    static {
        ActTypeEnum[] arr = ActTypeEnum.values();
        for (ActTypeEnum actType : arr) {
            if (actType == ActTypeEnum.PACKING_PRICE) {
                continue;
            }
            ActivityTypeBO activityTypeBO = new ActivityTypeBO();
            activityTypeBO.setCode(actType.getCode());
            activityTypeBO.setRemark(actType.getRemark());
            ACTIVITY_TYPE_LIST.add(activityTypeBO);
        }
    }

    @Override
    protected Result<List<ActivityTypeBO>> doBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {
        return Result.buildSuccessResult(ACTIVITY_TYPE_LIST);
    }

}
