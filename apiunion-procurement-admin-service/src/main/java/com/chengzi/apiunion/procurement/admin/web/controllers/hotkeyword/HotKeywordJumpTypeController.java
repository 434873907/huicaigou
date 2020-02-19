package com.chengzi.apiunion.procurement.admin.web.controllers.hotkeyword;

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
 * 获取热门关键词跳转类型列表
 *
 * @author 月汐
 * @date 2018/12/4 15:37
 */
public class HotKeywordJumpTypeController extends AbstractApiController<EmptyForm> {

    private static final List<JumpTypeBO> JUMP_TYPE_LIST = new ArrayList<>();

    static {
        JumpTypeEnum[] arr = new JumpTypeEnum[3];
        arr[0] = JumpTypeEnum.PAGE_SEARCH;
        arr[1] = JumpTypeEnum.ACTIVITY_DETAIL;
        arr[2] = JumpTypeEnum.PAGE_WEB;
        for (JumpTypeEnum jumpType : arr) {
            JumpTypeBO jumpTypeBO = new JumpTypeBO();
            jumpTypeBO.setCode(jumpType.getCode());
            jumpTypeBO.setRemark(jumpType.getRemark());
            JUMP_TYPE_LIST.add(jumpTypeBO);
        }
    }

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {
        return Result.buildSuccessResult(JUMP_TYPE_LIST);
    }
}
