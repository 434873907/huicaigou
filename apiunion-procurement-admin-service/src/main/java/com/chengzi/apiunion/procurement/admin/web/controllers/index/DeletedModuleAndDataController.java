package com.chengzi.apiunion.procurement.admin.web.controllers.index;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.index.service.IndexTabModuleAndDataService;
import com.chengzi.apiunion.procurement.admin.web.formbean.index.DeletedModuleAndDataForm;
import com.chengzi.common.data.validate.Result;

/**
 * @author 苏子
 * @date 2018年10月30日
 */
public class DeletedModuleAndDataController extends AbstractApiController<DeletedModuleAndDataForm>{

    @Override
    protected Result<String> doBiz(HttpServletRequest request, HttpServletResponse response, DeletedModuleAndDataForm command) throws Exception {
        IndexTabModuleAndDataService indexTabModuleAndDataService = BeanFactory.getBean(IndexTabModuleAndDataService.class);
        indexTabModuleAndDataService.updateModuleDeleted(command.getId());
        return Result.buildSuccessMsg("删除成功！");
    }

}
