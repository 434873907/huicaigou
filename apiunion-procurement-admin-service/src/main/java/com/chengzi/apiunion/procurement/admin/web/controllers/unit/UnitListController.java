package com.chengzi.apiunion.procurement.admin.web.controllers.unit;

import com.chengzi.apiunion.common.data.beans.EmptyRouteOperate;
import com.chengzi.apiunion.common.module.config.service.UnitService;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.common.module.config.pojo.UnitDO;
import com.chengzi.apiunion.procurement.admin.web.pojo.unit.ItemUnitBO;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.web.formbean.EmptyForm;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 单位列表
 * @author 行者
 *
 */
public class UnitListController extends AbstractApiController<EmptyForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {
        UnitService unitService = BeanFactory.getBean(UnitService.class);
        List<UnitDO> unitDOList = unitService.selectAllUnitsWithCache(new EmptyRouteOperate());

        List<ItemUnitBO> list = new ArrayList<>();
        for (UnitDO unitDO : unitDOList) {
            if (!unitDO.isDeleted()) {
                ItemUnitBO unitBO = new ItemUnitBO();
                unitBO.setId(unitDO.getId());
                unitBO.setUnit(unitDO.getUnit());
                list.add(unitBO);
            }
        }
        return Result.buildSuccessResult(list);
    }

}
