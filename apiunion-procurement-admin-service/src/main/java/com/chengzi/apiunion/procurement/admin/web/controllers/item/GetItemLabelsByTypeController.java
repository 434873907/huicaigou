package com.chengzi.apiunion.procurement.admin.web.controllers.item;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.pojo.ItemLabelDO;
import com.chengzi.apiunion.item.service.ItemLabelService;
import com.chengzi.apiunion.procurement.admin.web.formbean.item.GetItemLabelsByTypeForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.item.GetItemLabelsByTypeBO;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 月汐
 * @date 2019/12/19 15:43
 */
public class GetItemLabelsByTypeController extends AbstractApiController<GetItemLabelsByTypeForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, GetItemLabelsByTypeForm command) throws Exception {
        ItemLabelService itemLabelService = BeanFactory.getBean(ItemLabelService.class);
        List<ItemLabelDO> itemLabelDOS = itemLabelService.queryByType(command.getLabelType().getCode());
        List<GetItemLabelsByTypeBO> boList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(itemLabelDOS)) {
            for (ItemLabelDO itemLabelDO : itemLabelDOS) {
                GetItemLabelsByTypeBO bo = new GetItemLabelsByTypeBO();
                bo.setId(itemLabelDO.getId());
                bo.setName(itemLabelDO.getName());
                boList.add(bo);
            }
        }
        return Result.buildSuccessResult(boList);
    }
}
