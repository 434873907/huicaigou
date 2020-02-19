/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.controllers.upload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.common.module.image.service.ImageService;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.upload.FileUploadForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.UploadFileBO;
import com.chengzi.common.data.validate.Result;

/**
 * 上传文件
 * 
 * @author Kolor
 */
public class UploadFileController extends AbstractApiController<FileUploadForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, FileUploadForm command) throws Exception {
        ImageService imageService = BeanFactory.getBean(ImageService.class);

        String fileName = ((CommonsMultipartFile) command.getFile()).getFileItem().getName();

        Result<String> retUpload = imageService.uploadImage(command.getFile(), command.getUploadType(), null, fileName, command.getFileFolderId());
        if (!retUpload.isSuccess()) {
            return retUpload;
        }

        UploadFileBO uploadFileBO = new UploadFileBO();
        uploadFileBO.setFileName(fileName);
        uploadFileBO.setUrl(retUpload.getData());

        return Result.buildSuccessResult(uploadFileBO);
    }

}