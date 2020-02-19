package com.chengzi.apiunion.procurement.admin.web.controllers.filefolder;

import com.chengzi.apiunion.common.module.filefolder.service.FileFolderService;
import com.chengzi.apiunion.common.module.image.enums.FileTypeEnum;
import com.chengzi.apiunion.common.module.image.service.ImageService;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.filefolder.RenameFileForm;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 重命名文件/文件夹
 *
 * @author 月汐
 * @date 2019/1/31 15:59
 */
public class RenameFileController extends AbstractApiController<RenameFileForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, RenameFileForm command) throws Exception {

        if(command.getFileType() == FileTypeEnum.FOLDER) {
            FileFolderService fileFolderService = BeanFactory.getBean(FileFolderService.class);
            fileFolderService.renameFileFolder(Long.parseLong(command.getKey()), command.getName());
        } else if (command.getFileType() == FileTypeEnum.IMAGE) {
            ImageService imageService = BeanFactory.getBean(ImageService.class);
            imageService.renameImage(command.getKey(), command.getName());
        }

        return Result.buildSuccessResult("修改成功");
    }
}
