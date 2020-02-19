package com.chengzi.apiunion.procurement.admin.web.controllers.filefolder;

import com.chengzi.apiunion.common.module.filefolder.service.FileFolderService;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.filefolder.FileFolderForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ql
 * @date 2019/1/30
 * @description: 新建文件夹
 */
public class AddFileFolderController extends AbstractApiController<FileFolderForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, FileFolderForm command) throws Exception {
        FileFolderService folderService = BeanFactory.getBean(FileFolderService.class);
        // 拿到输入的文件夹名称和当前文件夹id
        long newId = folderService.createFolder(command.getFolderName(), command.getId());
        if (newId > command.getId()) {
            return Result.buildSuccessResult(newId);
        }
        return Result.buildOpFailedResult("创建文件夹失败");
    }
}
