package com.chengzi.apiunion.procurement.admin.web.helper.order;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chengzi.common.data.validate.ErrorConstants;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.FileUtil;
import com.chengzi.common.util.PropertyUtil;

/**
 * <p>
 * </p>
 * User: 摩天
 * Date: 2018-11-19 15:40
 */
public class OrderExpressHelper {

    public static final String  TEMP_FILE_DIR;
    private static final Logger logger = LoggerFactory.getLogger(OrderExpressHelper.class);

    static {
        TEMP_FILE_DIR = PropertyUtil.getProperty("user.home") + File.separator + "temp" + File.separator + "file";
        //
        File file = new File(TEMP_FILE_DIR);
        logger.warn("temp file path : " + file.getAbsolutePath());
        //
        try {
            file.mkdirs();
        } catch (Exception e) {
            logger.error("mkdirs temp file path " + file.getAbsolutePath() + " failed " + e.getMessage(), e);
        }
    }

    public static Result importOrderExpressExcel(InputStream[] inputStreamArray) {
        Result<Object> result = null;

        if (inputStreamArray == null) {
            return Result.buildOpFailedResult("导入物流失败,没有读取到文件!");
        }

        List<Sheet> sheetList = new ArrayList<>();
        int total = 0;
        int index = 0;
        for (InputStream inputStream : inputStreamArray) {
            BufferedInputStream in = new BufferedInputStream(inputStream);

            String path = TEMP_FILE_DIR + "/order_express_" + System.currentTimeMillis() + ".xls";
            FileUtil.write(path, in);
            result = validataXls(path);

            if (!result.isSuccess()) {
                break;
            }
            Sheet sheet = getFirstValidSheet((Workbook) result.getData());
            total += sheet.getLastRowNum();
            sheetList.add(sheet);

            result.setData(null);
            // 只支持一个文件
            File fileDel = new File(path);
            fileDel.delete();
            break;
        }

        for (Sheet sheet : sheetList) {
            try {

            } catch (Exception e) {

            }
        }
        if (result == null) {
            result = Result.buildOpFailedResult("导入物流失败");
        }
        return result;
    }

    private static Result<Object> validataXls(String path) {
        Result<Object> result = new Result<Object>();

        File tempFile = new File(path);
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(tempFile);
        } catch (FileNotFoundException e) {
            result.setCode(ErrorConstants.ERR_OP_FAILED);
            result.setSuccess(false);
            result.setMessage("没有找到上传的文件");
        }

        Workbook wb = null;
        try {
            wb = WorkbookFactory.create(fin);
            result.setData(wb);
        } catch (Exception e) {
            result.setCode(ErrorConstants.ERR_OP_FAILED);
            result.setSuccess(false);
            result.setMessage("无法读取上传的文件内容,无效的excel文件");
        }

        // 校验格式
        Sheet sheet = getFirstValidSheet(wb);
        int total = sheet.getLastRowNum();
        if (total < 1) {
            result.setCode(ErrorConstants.ERR_OP_FAILED);
            result.setSuccess(false);
            result.setMessage("没有读取到要导入的数据");
        }

        result.setCode(ErrorConstants.SUCCESS);
        result.setSuccess(true);
        return result;
    }

    private static Sheet getFirstValidSheet(Workbook wb) {
        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            if (!wb.isSheetHidden(i)) {
                return wb.getSheetAt(i);
            }
        }
        return null;
    }

}
