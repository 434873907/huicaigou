package com.chengzi.apiunion.procurement.admin.web.controllers.index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chengzi.apiunion.common.jump.JumpTypeEnum;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.index.enums.LayoutTypeEnum;
import com.chengzi.apiunion.procurement.admin.web.formbean.index.JumpTypeForLayoutForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.common.JumpTypeBO;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;

/**
 * @author 苏子
 * @date 2018年10月29日
 */
public class JumpTypeForLayoutController extends AbstractApiController<JumpTypeForLayoutForm> {
    
    private static Map<Integer,List<JumpTypeEnum>> JUMP_TYPE_MAP = new HashMap<>();
    
    static {
        List<JumpTypeEnum> list = new ArrayList<>();
        list.add(JumpTypeEnum.ACTIVITY_DETAIL);
        list.add(JumpTypeEnum.CATEGORY_DETAIL);
        list.add(JumpTypeEnum.PAGE_BRAND_DETAIL);
        list.add(JumpTypeEnum.PAGE_SEARCH);
        list.add(JumpTypeEnum.PAGE_GOODS_DETAIL);
        list.add(JumpTypeEnum.PAGE_WEB);
        
        List<JumpTypeEnum> list2 = new ArrayList<>();
        list2.add(JumpTypeEnum.ACTIVITY_DETAIL);
        list2.add(JumpTypeEnum.CATEGORY_DETAIL);
        list2.add(JumpTypeEnum.PAGE_BRAND_DETAIL);
        
        JUMP_TYPE_MAP.put(LayoutTypeEnum.CAROUSEL_POSTER.getCode(), list);
        JUMP_TYPE_MAP.put(LayoutTypeEnum.SLIDING_PAGE.getCode(), list);
        JUMP_TYPE_MAP.put(LayoutTypeEnum.GALLERY_MODULE.getCode(), list);
        JUMP_TYPE_MAP.put(LayoutTypeEnum.MIXED_ARRANGEMENT.getCode(), list);
        JUMP_TYPE_MAP.put(LayoutTypeEnum.PRODUCT_LIST.getCode(), list2);
        JUMP_TYPE_MAP.put(LayoutTypeEnum.CLOSET_LAYOUT.getCode(), list2);
    }

    @Override
    protected Result<List<JumpTypeBO>> doBiz(HttpServletRequest request, HttpServletResponse response, JumpTypeForLayoutForm command)
            throws Exception {
        int layoutType = command.getLayoutType();
        List<JumpTypeEnum> list = JUMP_TYPE_MAP.get(layoutType);
        
        List<JumpTypeBO> jumpTypeList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(list)) {
            for (JumpTypeEnum jumpTypeEnum : list) {
                JumpTypeBO bo = new JumpTypeBO();
                bo.setCode(jumpTypeEnum.getCode());
                bo.setRemark(jumpTypeEnum.getRemark());
                jumpTypeList.add(bo);
            }
        }
        return Result.buildSuccessResult(jumpTypeList);
    }
}
