package com.chengzi.apiunion.procurement.admin.web.controllers.filefolder;

import com.chengzi.apiunion.common.module.filefolder.service.FileFolderService;
import com.chengzi.apiunion.common.module.image.service.ImageService;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.filefolder.MoveFileForm;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 移动文件/文件夹
 *
 * @author 月汐
 * @date 2019/1/31 15:40
 */
public class MoveFileController extends AbstractApiController<MoveFileForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, MoveFileForm command) throws Exception {
        FileFolderService fileFolderService = BeanFactory.getBean(FileFolderService.class);
        ImageService imageService = BeanFactory.getBean(ImageService.class);

        if (command.getParentId() < 0) {
            return Result.buildIllegalArgumentResult("请选择目标文件夹");
        }

        if (CollectionUtil.isNotEmpty(command.getFileFolderIds())) {
            for (long fileFolderId : command.getFileFolderIds()) {
                if (fileFolderId == command.getParentId()) {
                    return Result.buildIllegalArgumentResult("不能将文件夹作为自己的子文件夹");
                }
            }
            fileFolderService.moveFileFolders(command.getFileFolderIds(), command.getParentId());
        }

        if (CollectionUtil.isNotEmpty(command.getImageMd5s())) {
            imageService.changeImagesFolder(command.getImageMd5s(), command.getParentId());
        }

        return Result.buildSuccessResult("移动成功");
    }
}
