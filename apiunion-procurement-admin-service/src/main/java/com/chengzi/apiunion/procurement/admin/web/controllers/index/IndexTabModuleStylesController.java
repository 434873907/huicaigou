package com.chengzi.apiunion.procurement.admin.web.controllers.index;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.index.pojo.IndexTabModuleStyleDO;
import com.chengzi.apiunion.index.service.IndexTabModuleStyleService;
import com.chengzi.apiunion.procurement.admin.web.formbean.index.IndexTabModuleStylesForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.index.IndexTabModuleStylesBO;
import com.chengzi.common.data.validate.Result;

/**
 * @author 苏子
 * @date 2018年10月26日
 */
public class IndexTabModuleStylesController extends AbstractApiController<IndexTabModuleStylesForm>{

    @Override
    protected Result<List<IndexTabModuleStylesBO>> doBiz(HttpServletRequest request, HttpServletResponse response, IndexTabModuleStylesForm command) throws Exception {
        int layoutType = command.getLayoutType();
        
        IndexTabModuleStyleService indexTabModuleStyleService = BeanFactory.getBean(IndexTabModuleStyleService.class);
        List<IndexTabModuleStyleDO> moduleStyleList = indexTabModuleStyleService.queryModuleStyles(layoutType);
        List<IndexTabModuleStylesBO> moduleStylesBOList = IndexTabModuleStylesBO.converter(moduleStyleList);
        
        return Result.buildSuccessResult(moduleStylesBOList);
    }

}
