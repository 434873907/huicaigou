package com.chengzi.apiunion.procurement.admin.web.controllers.unit;

import com.chengzi.apiunion.common.module.config.service.UnitService;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.common.module.config.pojo.UnitDO;
import com.chengzi.apiunion.procurement.admin.web.formbean.unit.AddUnitForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 单位列表
 * @author 行者
 *
 */
public class AddUnitController extends AbstractApiController<AddUnitForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, AddUnitForm command) throws Exception {
        UnitService unitService = BeanFactory.getBean(UnitService.class);
        UnitDO unitDO = new UnitDO(command.getUnit());
        unitService.add(unitDO);
        return Result.buildEmptySuccess();
    }

}
