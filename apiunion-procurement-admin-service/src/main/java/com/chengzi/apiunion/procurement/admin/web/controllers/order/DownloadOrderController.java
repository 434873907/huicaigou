package com.chengzi.apiunion.procurement.admin.web.controllers.order;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chengzi.apiunion.common.async.ProgressContext;
import com.chengzi.apiunion.common.data.search.elastic.pojo.OrderBy;
import com.chengzi.apiunion.common.data.search.elastic.pojo.SearchResult;
import com.chengzi.apiunion.common.web.controllers.excel.AsyncExportExcelController;
import com.chengzi.apiunion.common.web.pojo.excel.ExcelData;
import com.chengzi.apiunion.express.pojo.OrderExpressDO;
import com.chengzi.apiunion.express.service.OrderExpressService;
import com.chengzi.apiunion.expresscompany.pojo.ExpressCompanyDO;
import com.chengzi.apiunion.expresscompany.service.ExpressCompanyService;
import com.chengzi.apiunion.order.enums.OrderStatusEnum;
import com.chengzi.apiunion.order.pojo.OrderDO;
import com.chengzi.apiunion.order.pojo.OrderDeliveryInfo;
import com.chengzi.apiunion.order.pojo.OrderItemDO;
import com.chengzi.apiunion.order.pojo.OrderPayInfoDO;
import com.chengzi.apiunion.order.pojo.search.OrderSearchBO;
import com.chengzi.apiunion.order.pojo.snapshot.FeeSnapshot;
import com.chengzi.apiunion.order.pojo.snapshot.ItemSnapshot;
import com.chengzi.apiunion.order.query.OrderSearchQuery;
import com.chengzi.apiunion.order.service.OrderPayInfoService;
import com.chengzi.apiunion.order.service.OrderSearchService;
import com.chengzi.apiunion.order.service.OrderService;
import com.chengzi.apiunion.procurement.admin.web.formbean.order.DownloadOrderForm;
import com.chengzi.apiunion.user.pojo.UserDO;
import com.chengzi.apiunion.user.service.UserService;
import com.chengzi.common.data.support.Range;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import com.chengzi.common.util.DateUtil;
import com.chengzi.common.util.EnumUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.summercool.util.LangUtil;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.*;

import static com.chengzi.apiunion.order.constant.OrderStatusConstants.*;
import static com.chengzi.apiunion.order.constant.OrderStatusConstants.ABNORMAL;
import static com.chengzi.apiunion.order.constant.OrderStatusConstants.REFUND;
import static com.chengzi.apiunion.order.enums.OrderStatusEnum.*;
import static com.chengzi.apiunion.order.enums.OrderStatusEnum.ORDER_STATUS_REFUND;
import static com.chengzi.apiunion.procurement.admin.web.formbean.order.OrderListForm.OrderListKeywordTypeEnum.ITEM_ID;
import static com.chengzi.apiunion.procurement.admin.web.formbean.order.OrderListForm.OrderListKeywordTypeEnum.ITEM_NAME;
import static com.chengzi.apiunion.procurement.admin.web.formbean.order.OrderListForm.OrderListKeywordTypeEnum.RECEIVER_NAME;

/**
 * 导出订单
 *
 * @author 月汐
 * @date 2018/12/19 20:58
 */
public class DownloadOrderController extends AsyncExportExcelController<DownloadOrderForm> {
    private static final Logger logger = LoggerFactory.getLogger(DownloadOrderController.class);
    private static final String[] HEADER = {"序号","下单时间", "供应商", "供应商ID", "订单号", "包裹号", "商品名称",
            "商品ID", "规格名称", "规格ID", "数量", "物流公司", "物流单号", "收货人", "收货地址", "联系电话", "采购商账号",
            "采购商ID", "成本价", "运费(￥)", "已付金额", "退款金额", "订单状态", "最后一条物流轨迹"};

    @Override
    protected Result<ExcelData> doExport(HttpServletRequest request, HttpServletResponse response, DownloadOrderForm command) {
        ExpressCompanyService expressCompanyService = BeanFactory.getBean(ExpressCompanyService.class);
        OrderExpressService orderExpressService = BeanFactory.getBean(OrderExpressService.class);
        OrderSearchQuery query = new OrderSearchQuery();

        query.setDateRange(new Range(command.getStartTime(), command.getEndTime()));
        List<Integer> orderStatus = new ArrayList<>();
        switch (command.getStatus()) {
            case WAIT_DELIVER:
                orderStatus.add(ORDER_STATUS_WAIT_SEND.getCode());
                break;
            case WAIT_RECEIPT:
                orderStatus.add(ORDER_STATUS_WAIT_CONFIRM.getCode());
                break;
            case COMPLETED:
                orderStatus.add(ORDER_STATUS_COMPLETE.getCode());
                break;
            case REJECT:
                orderStatus.add(ORDER_STATUS_REJECT.getCode());
                break;
            case REFUND:
                orderStatus.add(ORDER_STATUS_REFUND.getCode());
                break;
            //todo louchongxiao 订单异常标记
            case ABNORMAL:
                query.setAbnormalStatus(1);
                break;
        }
        query.setStatus(orderStatus);
        query.setIsDeleted(null);
        if (!StringUtils.isEmpty(command.getKeyword())) {
            if (command.getKeywordType() == ITEM_ID.getCode()) {
                query.setItemId(LangUtil.parseLong(command.getKeyword()));
            } else if (command.getKeywordType() == ITEM_NAME.getCode()) {
                query.setItemName(command.getKeyword());
            } else if (command.getKeywordType() == RECEIVER_NAME.getCode()) {
                query.setReceiverName(command.getKeyword());
            }
        }

        if (CollectionUtil.isNotEmpty(command.getUserIds())) {
            query.setUserIds(new ArrayList<>(command.getUserIds()));
        }
        if (CollectionUtil.isNotEmpty(command.getSupplierIds())) {
            query.setSupplierIds(new ArrayList<>(command.getSupplierIds()));
        }

        query.setSize(9999);
        query.setFrom(0);
        query.setOrderBy(CollectionUtil.asArrayList(new OrderBy("createTime", SortOrder.DESC)));

        OrderSearchService orderSearchService = BeanFactory.getBean(OrderSearchService.class);
        SearchResult<OrderSearchBO> orderSearchBOSearchResult = orderSearchService.query(query);

        List<OrderSearchBO> orderSearchBOS = orderSearchBOSearchResult.getData();

        Set<Long> buyerIds = new HashSet<>();
        Map<String, Long> orderNumUserIdMap = new HashMap<>();
        orderSearchBOS.forEach(x -> {
            orderNumUserIdMap.put(x.getOrderNum(), x.getUserId());
            buyerIds.add(x.getUserId());
        });

        OrderService orderService = BeanFactory.getBean(OrderService.class);
        UserService userService = BeanFactory.getBean(UserService.class);

        List<UserDO> userDOList = userService.getUserByUserIds(new ArrayList<>(buyerIds));
        Map<Long, UserDO> userMap = CollectionUtil.toMap(userDOList, "id");

        List<OrderDO> orderByOrderNums = orderService.getOrderByOrderNums(orderNumUserIdMap);
        Map<String, OrderDO> orderNumOrderMap = CollectionUtil.toMap(orderByOrderNums, "orderNum");
        Map<String, List<OrderItemDO>> orderItemDOMap = orderService.queryByOrderNums(orderNumUserIdMap);
        List<ExpressCompanyDO> expressCompanyDOS = expressCompanyService.queryExpressCompanyList();
        String[] expressCompanies = new String[expressCompanyDOS.size()];
        for (int i = 0; i < expressCompanyDOS.size(); i++) {
            ExpressCompanyDO expressCompanyDO = expressCompanyDOS.get(i);
            expressCompanies[i] = expressCompanyDO.getId() + "-" + expressCompanyDO.getCompanyName();
        }

        Map<String, BigDecimal> orderPayAmountMap = buildOrderPayAmountMap(orderNumUserIdMap);

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        sheet.autoSizeColumn(1);
        writeHeader(workbook, sheet);
        int rowIndex = 1;
        Optional<Integer> sizeOptional = orderItemDOMap.values().stream().map(List::size).reduce(Integer::sum);
        int size = 0;
        if (sizeOptional.isPresent()) {
            size = sizeOptional.get();
        }
        for (OrderSearchBO orderSearchBO : orderSearchBOS) {
            String orderNum = orderSearchBO.getOrderNum();
            OrderDO order = orderNumOrderMap.get(orderNum);
            if (order == null) {
                continue;
            }
            List<OrderItemDO> orderItemDOList = orderItemDOMap.get(orderNum);
            Set<String> pkgNos = CollectionUtil.getDisctinctFieldValueList(orderItemDOList, "pkgNo");
            Map<String, List<OrderExpressDO>> pkgNoExpressListMap = orderExpressService.getByPkgNos(new ArrayList<>(pkgNos), order.getUserId());
            BigDecimal costAmount = calOrderCostAmount(orderItemDOList);
            String orderStatusDesc = OrderStatusEnum.getOrderStatus(order.getStatus()).getRemark();
            FeeSnapshot feeSnapshot = FeeSnapshot.parse(order.getFeeSnapshot());
            BigDecimal expAmount = feeSnapshot.getExpAmount();
            for (int i = 0; i < orderItemDOList.size(); i++) {
                OrderItemDO orderItemDO = orderItemDOList.get(i);
                List<OrderExpressDO> orderExpressDOS = pkgNoExpressListMap.get(orderItemDO.getPkgNo());
                OrderExpressDO firstExpressDO = getFirstExpressDO(orderExpressDOS);
                OrderExpressDO lastExpressDO  = getLastExpressDO(orderExpressDOS);
                writeRow(sheet, rowIndex, orderItemDO, orderNumOrderMap.get(orderNum),
                        expressCompanies, userMap, orderPayAmountMap, costAmount, orderStatusDesc, expAmount,firstExpressDO,
                        lastExpressDO);
                rowIndex += 1;
            }
            ProgressContext.percent(ProgressContext.getCurrent(), rowIndex, size + 1);
        }
//        orderItemDOMap.forEach((k, v) -> {
//            for (int i = 0; i < v.size(); i++) {
//                writeRow(sheet, i+1, v.get(i), expressCompanies);
//            }
//        });

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return Result.buildSuccessResult(new ExcelData("订单数据导出_" + DateUtil.formatDate(new Date(), "yyyyMMddHHmmss") + ".xls", new ByteArrayInputStream(outputStream.toByteArray())));
        } catch (Exception e) {
            return Result.buildOpFailedResult("导出失败");
        }
    }

    private Map<String, BigDecimal> buildOrderPayAmountMap(Map<String, Long> orderNumUserIdMap) {
        OrderPayInfoService orderPayInfoService = BeanFactory.getBean(OrderPayInfoService.class);
        Map<String, List<OrderPayInfoDO>> payInfoMap = orderPayInfoService.queryByOrderNums(orderNumUserIdMap);

        Map<String, BigDecimal> resultMap = new HashMap<>();

        payInfoMap.forEach((k, v) -> {
            if (CollectionUtil.isNotEmpty(v)) {
                resultMap.put(k, v.stream().map(OrderPayInfoDO::getPayAmount).reduce(BigDecimal::add).get());
            } else {
                resultMap.put(k, BigDecimal.ZERO);
            }
        });

        return resultMap;
    }

    private BigDecimal calOrderCostAmount(List<OrderItemDO> orderItemList) {
        BigDecimal costAmount = BigDecimal.ZERO;
        for (OrderItemDO orderItem : orderItemList) {
            ItemSnapshot itemSnapshot = ItemSnapshot.parse(orderItem.getItemSnapshot());
            costAmount = costAmount.add(itemSnapshot.getChannelPrice());
        }
        return costAmount;
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

    private void writeRow(HSSFSheet sheet, int rowIndex, OrderItemDO orderItemDO, OrderDO orderDO, String[] expressCompanies,
                          Map<Long, UserDO> userMap, Map<String, BigDecimal> orderPayAmountMap,
                          BigDecimal costAmount, String statusDesc, BigDecimal expAmount, OrderExpressDO orderExpressDO,
                          OrderExpressDO lastOrderExpressDO) {
        ItemSnapshot itemSnapshot = JSONObject.parseObject(orderItemDO.getItemSnapshot(), ItemSnapshot.class);
        HSSFRow row = sheet.getRow(rowIndex);
        if (row == null) {
            row = sheet.createRow(rowIndex);
        }
        int i = 0;
        Cell cell = row.createCell(i++);
        cell.setCellValue(rowIndex);
        cell.getCellStyle().setFillBackgroundColor(HSSFColor.HSSFColorPredefined.GREY_50_PERCENT.getColor().getIndex());
        row.createCell(i++).setCellValue(DateUtil.formatDate(orderDO.getCreateTime(),DateUtil.YYYY_MM_DD_HH_MM_SS));
        row.createCell(i++).setCellValue(itemSnapshot.getSupplierName());
        row.createCell(i++).setCellValue(itemSnapshot.getSupplierId());
        row.createCell(i++).setCellValue(orderItemDO.getOrderNum());
        row.createCell(i++).setCellValue(orderItemDO.getPkgNo());
        row.createCell(i++).setCellValue(itemSnapshot.getItemName());
        row.createCell(i++).setCellValue(orderItemDO.getItemId());
        row.createCell(i++).setCellValue(itemSnapshot.getSkuInfo());
        row.createCell(i++).setCellValue(orderItemDO.getSkuId());
        row.createCell(i++).setCellValue(orderItemDO.getBuyNum());
        row.createCell(i++).setCellValue(orderExpressDO == null ? "" : orderExpressDO.getRealExpressCompanyName());
        row.createCell(i++).setCellValue(orderExpressDO == null ? "" : orderExpressDO.getRealExpressNum());
        // CellRangeAddressList regions = new CellRangeAddressList(rowIndex, rowIndex, i, i++);
        // DVConstraint constraint = DVConstraint.createExplicitListConstraint(expressCompanies);
        // HSSFDataValidation dataValidation = new HSSFDataValidation(regions, constraint);
        // sheet.addValidationData(dataValidation);
        if (StringUtils.isNotBlank(orderDO.getOrderDeliveryInfo())) {
            OrderDeliveryInfo deliveryInfo = JSONObject.parseObject(orderDO.getOrderDeliveryInfo(), OrderDeliveryInfo.class);
            //订单号
            // i += 1;
            row.createCell(i++).setCellValue(deliveryInfo.getName());
            row.createCell(i++).setCellValue(deliveryInfo.getAddress());
            row.createCell(i++).setCellValue(deliveryInfo.getPhone());
        } else {
            // i += 1;
            row.createCell(i++).setCellValue("");
            row.createCell(i++).setCellValue("");
            row.createCell(i++).setCellValue("");
        }

        UserDO user = userMap.get(orderDO.getUserId());
        row.createCell(i++).setCellValue(user.getAccount());
        row.createCell(i++).setCellValue(user.getId());
        row.createCell(i++).setCellValue(costAmount.toString());
        row.createCell(i++).setCellValue(expAmount.toString());
        row.createCell(i++).setCellValue(orderPayAmountMap.getOrDefault(orderDO.getOrderNum(), BigDecimal.ZERO).doubleValue());
        row.createCell(i++).setCellValue(orderDO.getRefundAmount());
        row.createCell(i++).setCellValue(statusDesc);
        row.createCell(i).setCellValue(getLastExpressDetail(lastOrderExpressDO));
    }

    private static OrderExpressDO getFirstExpressDO(List<OrderExpressDO> orderExpressDOS) {
        if (CollectionUtil.isEmpty(orderExpressDOS)) {
            return null;
        }
        OrderExpressDO result = orderExpressDOS.get(0);
        for (OrderExpressDO orderExpressDO : orderExpressDOS) {
            if (orderExpressDO.getType() < result.getType()) {
                if (orderExpressDO.getRealExpressNum()!=null) {
                    result = orderExpressDO;
                }
            }
        }
        return result;
    }

    /**
     * 得到最新的物流轨迹
     * @param orderExpressDOS
     * @return
     */
    private static OrderExpressDO getLastExpressDO(List<OrderExpressDO> orderExpressDOS) {
        if (CollectionUtil.isEmpty(orderExpressDOS)) {
            return null;
        }
        OrderExpressDO result = orderExpressDOS.get(0);
        for (OrderExpressDO orderExpressDO : orderExpressDOS) {
            if (orderExpressDO.getType() > result.getType()) {
                if(StringUtils.isNotBlank(orderExpressDO.getDetail())) {
                    result= orderExpressDO;
                }
            }
        }
        return result;
    }
    private static String getLastExpressDetail(OrderExpressDO orderExpressDO) {
        if(orderExpressDO == null) {
            return "";
        }
        String detail = orderExpressDO.getDetail();
        if(StringUtils.isNotBlank(detail)) {
            try {
                JSONArray expressArray = JSONObject.parseArray(orderExpressDO.getDetail());
                if(expressArray!= null && expressArray.size()>0) {
                    JSONObject jsonObject = expressArray.getJSONObject(0);
                    String key = jsonObject.keySet().iterator().next();
                    String val = jsonObject.getString(key);
                    return key+" "+ val;
                }
            } catch (Exception e) {
                logger.error("解析物流轨迹错误", e);
            }
        }
        return "";
    }

}
