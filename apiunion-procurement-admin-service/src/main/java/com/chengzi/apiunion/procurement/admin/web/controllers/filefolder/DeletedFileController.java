package com.chengzi.apiunion.procurement.admin.web.controllers.filefolder;

import com.chengzi.apiunion.common.module.filefolder.service.FileFolderService;
import com.chengzi.apiunion.common.module.image.enums.FileTypeEnum;
import com.chengzi.apiunion.common.module.image.service.ImageService;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.filefolder.DeleteFileForm;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ql
 * @date 2019/1/30
 * @description: 删除文件夹以及子文件夹，图片文件移动到默认文件夹下（imageFolderId）
 */
public class DeletedFileController extends AbstractApiController<DeleteFileForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, DeleteFileForm command) throws Exception {
        FileFolderService folderService = BeanFactory.getBean(FileFolderService.class);
        ImageService imageService = BeanFactory.getBean(ImageService.class);

        // 删除文件夹
        if (CollectionUtil.isNotEmpty(command.getDeleteFileFolderIds())) {
            folderService.deletedFoldersByIds(command.getDeleteFileFolderIds());
        }

        // 删除图片
        if (CollectionUtil.isNotEmpty(command.getDeleteImageMd5s())) {
            imageService.deleteImages(command.getDeleteImageMd5s());
        }

        return Result.buildSuccessResult("删除成功");
    }
}
