package com.chengzi.apiunion.procurement.admin.web.controllers.supplier.order;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.common.async.ProgressContext;
import com.chengzi.apiunion.common.data.search.elastic.pojo.OrderBy;
import com.chengzi.apiunion.common.data.search.elastic.pojo.SearchResult;
import com.chengzi.apiunion.common.web.controllers.excel.AsyncExportExcelController;
import com.chengzi.apiunion.common.web.pojo.excel.ExcelData;
import com.chengzi.apiunion.expresscompany.pojo.ExpressCompanyDO;
import com.chengzi.apiunion.expresscompany.service.ExpressCompanyService;
import com.chengzi.apiunion.procurement.admin.web.formbean.supplier.order.DownloadOrderForm;
import com.chengzi.apiunion.supplier.common.order.enums.SupplierOrderExpressStatusEnum;
import com.chengzi.apiunion.supplier.common.order.pojo.search.SupplierOrderExpressSearchBO;
import com.chengzi.apiunion.supplier.common.order.pojo.search.SupplierOrderItemSearchBO;
import com.chengzi.apiunion.supplier.common.order.pojo.snapshot.RecipientInfo;
import com.chengzi.apiunion.supplier.common.order.pojo.snapshot.SupplierItemSnapshot;
import com.chengzi.apiunion.supplier.common.order.query.SupplierOrderSearchQuery;
import com.chengzi.apiunion.supplier.common.order.service.SupplierOrderExpressSearchService;
import com.chengzi.common.data.support.Range;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import com.chengzi.common.util.DateUtil;

/**
 * 导出订单
 *
 * @author 随风
 * @create 2020-01-17 10:33
 **/
public class DownloadOrderController extends AsyncExportExcelController<DownloadOrderForm> {

    private static final Logger     logger = LoggerFactory.getLogger(DownloadOrderController.class);
    private static final String[]   HEADER = {"推送时间","包裹状态", "包裹号", "订单号", "商品名称", "条形码", "规格",
            "供货价格", "数量", "发货方式", "收货人姓名", "收货人电话", "身份证号", "收货地址", "物流公司", "物流单号",
            "最后一条物流轨迹"};

    @Override
    protected Result<ExcelData> doExport(HttpServletRequest request, HttpServletResponse response, DownloadOrderForm command) {
        SupplierOrderExpressSearchService expressSearchService = BeanFactory.getBean(SupplierOrderExpressSearchService.class);
        ExpressCompanyService expressCompanyService = BeanFactory.getBean(ExpressCompanyService.class);

        SupplierOrderSearchQuery searchQuery = new SupplierOrderSearchQuery();
        if (command.isAbnormal()) {
            searchQuery.setAbnormalStatus(command.getStatus().getCode());
        } else {
            searchQuery.setExpressStatus(command.getStatus().getCode());
        }
        searchQuery.setKeyword(command.getKeyword());
        searchQuery.setSupplierId(command.getSupplierId());
        searchQuery.setDateRange(new Range<>(command.getStartTime(), command.getEndTime()));
        searchQuery.setSize(9999);
        searchQuery.setFrom(0);
        searchQuery.setOrderBy(CollectionUtil.asArrayList(new OrderBy("createTime", SortOrder.DESC)));
        searchQuery.setIsDeleted(null);

        SearchResult<SupplierOrderExpressSearchBO> searchResult = expressSearchService.queryExpress(searchQuery);
        List<SupplierOrderExpressSearchBO> expressSearchBOS = searchResult.getData();

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        sheet.autoSizeColumn(1);
        writeHeader(workbook, sheet);
        int rowIndex = 1;

        int size = 0;
        if (CollectionUtil.isNotEmpty(expressSearchBOS)) {
            Optional<Integer> optional = expressSearchBOS.stream().map(SupplierOrderExpressSearchBO::getItemList).map(List::size).reduce(Integer::sum);
            if (optional.isPresent()) {
                size = optional.get();
            }
            List<ExpressCompanyDO> expressCompanyDOS = expressCompanyService.queryExpressCompanyList();
            Map<Long, ExpressCompanyDO> companyDOMap = expressCompanyDOS.stream().collect(Collectors.toMap(ExpressCompanyDO::getId, Function.identity()));

            for (SupplierOrderExpressSearchBO expressSearchBO : expressSearchBOS) {
                ExpressCompanyDO expressCompanyDO = companyDOMap.get(expressSearchBO.getExpressCompanyId());
                String lastExpressDetail = "";
                for (SupplierOrderItemSearchBO itemSearchBO : expressSearchBO.getItemList()) {
                    writeRow(sheet, rowIndex, expressSearchBO, itemSearchBO, expressCompanyDO == null ? "" : expressCompanyDO.getCompanyName(), lastExpressDetail);
                    rowIndex += 1;
                }
                ProgressContext.percent(ProgressContext.getCurrent(), rowIndex, size + 1);
            }
        }
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return Result.buildSuccessResult(new ExcelData("订单数据导出_" + DateUtil.formatDate(new Date(), "yyyyMMddHHmmss") + ".xls", new ByteArrayInputStream(outputStream.toByteArray())));
        } catch (Exception e) {
            return Result.buildOpFailedResult("导出失败");
        }
    }

    private void writeRow(HSSFSheet sheet, int rowIndex, SupplierOrderExpressSearchBO expressBO,
                          SupplierOrderItemSearchBO itemBO, String companyName, String lastExpressDetail) {
        SupplierItemSnapshot itemInfo = itemBO.getItemInfo();
        RecipientInfo recipient = expressBO.getRecipient();

        HSSFRow row = sheet.getRow(rowIndex);
        if (row == null) {
            row = sheet.createRow(rowIndex);
        }
        int i = 0;

        row.createCell(i++).setCellValue(DateUtil.formatDate(expressBO.getCreateTime(),DateUtil.YYYY_MM_DD_HH_MM_SS));
        row.createCell(i++).setCellValue(SupplierOrderExpressStatusEnum.getDescByCode(expressBO.getExpressStatus()));
        row.createCell(i++).setCellValue(expressBO.getPkgNo());
        row.createCell(i++).setCellValue(expressBO.getOrderNum());
        row.createCell(i++).setCellValue(itemInfo.getName());
        row.createCell(i++).setCellValue(itemInfo.getUpc());
        row.createCell(i++).setCellValue(itemInfo.getSku());
        row.createCell(i++).setCellValue(itemInfo.getOrderSkuPrice().toString());
        row.createCell(i++).setCellValue(itemBO.getBuyNum());
        row.createCell(i++).setCellValue(itemInfo.getChannelName());
        row.createCell(i++).setCellValue(recipient.getName());
        row.createCell(i++).setCellValue(recipient.getPhone());
        row.createCell(i++).setCellValue(recipient.getIdCardNumber());
        row.createCell(i++).setCellValue(RecipientInfo.getAddress(recipient));
        row.createCell(i++).setCellValue(companyName);
        row.createCell(i++).setCellValue(expressBO.getExpressNum());
        row.createCell(i).setCellValue(lastExpressDetail);
    }

    private void writeHeader(HSSFWorkbook workbook, HSSFSheet sheet) {
        HSSFRow row = sheet.getRow(0);
        if (row == null) {
            row = sheet.createRow(0);
        }

        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);

        for (int i = 0; i < HEADER.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(HEADER[i]);
            cell.setCellStyle(cellStyle);
        }
    }
}
