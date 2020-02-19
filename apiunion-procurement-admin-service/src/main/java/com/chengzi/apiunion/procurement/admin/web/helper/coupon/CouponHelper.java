package com.chengzi.apiunion.procurement.admin.web.helper.coupon;

import com.chengzi.apiunion.brand.pojo.BrandDO;
import com.chengzi.apiunion.common.util.ExcelUtil;
import com.chengzi.apiunion.common.web.pojo.excel.ExcelData;
import com.chengzi.apiunion.item.enums.ItemStatusEnum;
import com.chengzi.apiunion.item.pojo.ItemRichDO;
import com.chengzi.apiunion.item.pojo.ItemSkuInfo;
import com.chengzi.apiunion.item.util.SkuUtil;
import com.chengzi.apiunion.partnercategory.pojo.PartnerCategoryDO;
import com.chengzi.apiunion.supplier.pojo.SupplierDO;
import com.chengzi.apiunion.supplier.service.SupplierService;
import com.chengzi.apiunion.user.enums.AccountTypeEnum;
import com.chengzi.apiunion.user.pojo.UserDO;
import com.chengzi.apiunion.user.service.UserService;
import com.chengzi.common.data.support.Tuple2;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.summercool.web.servlet.BeanFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class CouponHelper {
    private static final String[] HEADER = {"序号", "用户ID", "用户名", "用户手机", "用户类型", "注册时间", "是否成功", "失败原因"};

    /**
     * @param commonErrorReason
     * @param errorUserIds      失败用户的ID
     * @param errorReasonMap    部分用户错误原因
     * @return
     */
    public static Result<ExcelData> buildErrorExcel(String commonErrorReason, List<Long> successUserIds, List<Long> errorUserIds, HashMap<Long, String> errorReasonMap) {

        UserService userService = BeanFactory.getBean(UserService.class);
        Tuple2<HSSFWorkbook, HSSFSheet> workbookAndSheetTuple = createAndSetHeader();
        HSSFWorkbook workbook = workbookAndSheetTuple.getV1();
        HSSFSheet sheet = workbookAndSheetTuple.getV2();
        int rowIndex = 1;
        List<UserDO> userDOS = userService.getUserByUserIds(errorUserIds);
        if (userDOS != null && userDOS.size() > 0) {
            for (UserDO userDO : userDOS) {
                Long userId = userDO.getId();
                String errorReason = errorReasonMap.get(userId);
                if (StringUtils.isEmpty(errorReason)) {
                    errorReason = commonErrorReason;
                }
                writeRowError(sheet, rowIndex, userDO, errorReason);
                rowIndex++;
            }
        }
        //成功的用户
        userDOS = userService.getUserByUserIds(successUserIds);
        if (userDOS != null && userDOS.size() > 0) {
            for (UserDO userDO : userDOS) {
                writeRowSuccess(sheet, rowIndex, userDO);
                rowIndex++;
            }
        }
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return Result.buildSuccessResult(new ExcelData("发放优惠券结果列表" + DateUtil.formatDate(new Date(), "yyyyMMddHHmmss") + ".xls", new ByteArrayInputStream(outputStream.toByteArray())));
        } catch (Exception e) {
            return Result.buildOpFailedResult("生成excel失败");
        }

    }


    private static Tuple2<HSSFWorkbook, HSSFSheet> createAndSetHeader() {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        sheet.autoSizeColumn(1);
        ExcelUtil.writeHeader(workbook, sheet, HEADER, null);
        return new Tuple2<>(workbook, sheet);
    }

    private static void writeRowError(HSSFSheet sheet, int rowIndex, UserDO userDO, String errorReason) {
        HSSFRow row = sheet.getRow(rowIndex);
        if (row == null) {
            row = sheet.createRow(rowIndex);
        }
        int colIndex = 0;
        Cell cell = row.createCell(colIndex++);
        cell.setCellValue(rowIndex);
        cell.getCellStyle().setFillBackgroundColor(HSSFColor.HSSFColorPredefined.RED.getColor().getIndex());
        row.createCell(colIndex++).setCellValue(userDO.getId());
        row.createCell(colIndex++).setCellValue(userDO.getNickName());
        row.createCell(colIndex++).setCellValue(userDO.getAccount());
        //用户类型
        String userType = AccountTypeEnum.parse(userDO.getCertificateType()).getRemark();
        row.createCell(colIndex++).setCellValue(userType);
        //注册时间
        String createTimeStr = DateUtil.formatDate(userDO.getCreateTime(), DateUtil.YYYY_MM_DD_HH_MM_SS);
        row.createCell(colIndex++).setCellValue(createTimeStr);
        row.createCell(colIndex++).setCellValue("失败");
        row.createCell(colIndex++).setCellValue(errorReason);
//        row.createCell(colIndex++).setCellValue(itemSkuInfo.getUpc());
    }

    private static void writeRowSuccess(HSSFSheet sheet, int rowIndex, UserDO userDO) {
        HSSFRow row = sheet.getRow(rowIndex);
        if (row == null) {
            row = sheet.createRow(rowIndex);
        }
        int colIndex = 0;
        Cell cell = row.createCell(colIndex++);
        cell.setCellValue(rowIndex);
        cell.getCellStyle().setFillBackgroundColor(HSSFColor.HSSFColorPredefined.GREY_50_PERCENT.getColor().getIndex());
        row.createCell(colIndex++).setCellValue(userDO.getId());
        row.createCell(colIndex++).setCellValue(userDO.getNickName());
        row.createCell(colIndex++).setCellValue(userDO.getAccount());
        //用户类型
        String userType = AccountTypeEnum.parse(userDO.getCertificateType()).getRemark();
        row.createCell(colIndex++).setCellValue(userType);
        //注册时间
        String createTimeStr = DateUtil.formatDate(userDO.getCreateTime(), DateUtil.YYYY_MM_DD_HH_MM_SS);
        row.createCell(colIndex++).setCellValue(createTimeStr);
        row.createCell(colIndex++).setCellValue("发放成功");
//        row.createCell(colIndex++).setCellValue(itemSkuInfo.getUpc());
    }
}
