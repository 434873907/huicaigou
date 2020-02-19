package com.chengzi.apiunion.procurement.admin.web.controllers.activity;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.pojo.activity.DisplayTerminalBO;
import com.chengzi.apiunion.sales.enums.DisplayTerminalEnum;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.EmptyForm;

/**
 * @author 苏子
 * @date 2018年11月13日
 */
public class DisplayTerminalListController extends AbstractApiController<EmptyForm> {

    private static final List<DisplayTerminalBO> DISPLAY_TERMINAL_LIST = new ArrayList<>();

    static {
        DisplayTerminalEnum[] arr = DisplayTerminalEnum.values();
        for (DisplayTerminalEnum displayTerminal : arr) {
            DisplayTerminalBO displayTerminalBO = new DisplayTerminalBO();
            displayTerminalBO.setCode(displayTerminal.getCode());
            displayTerminalBO.setRemark(displayTerminal.getRemark());
            DISPLAY_TERMINAL_LIST.add(displayTerminalBO);
        }
    }
    
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {
        return Result.buildSuccessResult(DISPLAY_TERMINAL_LIST);
    }

}
