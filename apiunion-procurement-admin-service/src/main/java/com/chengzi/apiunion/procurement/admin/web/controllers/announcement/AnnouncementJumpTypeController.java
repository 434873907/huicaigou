package com.chengzi.apiunion.procurement.admin.web.controllers.announcement;

import com.chengzi.apiunion.common.jump.JumpTypeEnum;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.pojo.common.JumpTypeBO;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.EmptyForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 公告类型列表
 *
 * @author 月汐
 * @date 2018/10/22 20:58
 */
public class AnnouncementJumpTypeController extends AbstractApiController<EmptyForm> {

    private static final List<JumpTypeBO> JUMP_TYPE_LIST = new ArrayList<>();

    static {
        JumpTypeEnum[] arr = new JumpTypeEnum[3];
        arr[0] = JumpTypeEnum.PAGE_WEB;
        arr[1] = JumpTypeEnum.ACTIVITY_DETAIL;
        arr[2] = JumpTypeEnum.CATEGORY_DETAIL;
        for (JumpTypeEnum jumpType : arr) {
            JumpTypeBO jumpTypeBO = new JumpTypeBO();
            jumpTypeBO.setCode(jumpType.getCode());
            jumpTypeBO.setRemark(jumpType.getRemark());
            JUMP_TYPE_LIST.add(jumpTypeBO);
        }
    }

    @Override
    protected Result<List<JumpTypeBO>> doBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {
        return Result.buildSuccessResult(JUMP_TYPE_LIST);
    }

}
