package com.chengzi.apiunion.procurement.admin.web.controllers.filefolder;

import com.chengzi.apiunion.common.data.beans.SimpleRouteOperate;
import com.chengzi.apiunion.common.module.filefolder.pojo.FileFolderDO;
import com.chengzi.apiunion.common.module.filefolder.service.FileFolderService;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.filefolder.QueryFileFolderByIdForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.filefolder.FileFolderBO;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ql
 * @date 2019/1/30
 * @description: 通过id查询其下所有文件夹
 */
public class QueryFileFolderByIdController extends AbstractApiController<QueryFileFolderByIdForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, QueryFileFolderByIdForm command) throws Exception {
        FileFolderService folderService = BeanFactory.getBean(FileFolderService.class);
        // root id = 1;
        FileFolderDO filefolderDO = folderService.queryAllFoldersById(SimpleRouteOperate.of(command.getId()));
        FileFolderBO convert = FileFolderBO.convert(filefolderDO);
        return Result.buildSuccessResult(convert);
    }
}
