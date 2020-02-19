package com.chengzi.apiunion.procurement.admin.web.controllers.supplier.order;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.supplier.common.order.enums.SupplierOrderAggTypeEnum;
import com.chengzi.apiunion.supplier.common.order.query.SupplierOrderSearchQuery;
import com.chengzi.apiunion.supplier.common.order.service.SupplierOrderExpressSearchService;
import com.chengzi.common.data.support.Range;
import com.chengzi.common.data.support.Tuple2;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.DateUtil;
import com.chengzi.common.web.formbean.EmptyForm;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

public class SupplierOrderSummaryController extends AbstractApiController<EmptyForm> {
    private static final String PAY_AMOUNT = "payAmount";
    private static final String REFUND_AMOUNT = "refundAmount";

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {
        SupplierOrderExpressSearchService searchService = BeanFactory.getBean(SupplierOrderExpressSearchService.class);

        SupplierOrderSearchQuery query = new SupplierOrderSearchQuery();

        List<Tuple2<SupplierOrderAggTypeEnum, BigDecimal>> resultTupleList = searchService.aggSupplierOrder(query);

        Range<Date> dateRange = new Range<>();
        dateRange.setRight(new Date());
        query.setDateRange(dateRange);

        dateRange.setLeft(getToday());
        Map<String, Double> todayPurchaseMap = searchService.getPurchaseAmount(query);
        double todayPurchaseAmount = todayPurchaseMap.get(PAY_AMOUNT) - todayPurchaseMap.getOrDefault(REFUND_AMOUNT, 0.0);
        resultTupleList.add(new Tuple2<>(SupplierOrderAggTypeEnum.TODAY_SALES, BigDecimal.valueOf(todayPurchaseAmount)));

        dateRange.setLeft(getYesterday());
        Map<String, Double> yesterdayPurchaseMap = searchService.getPurchaseAmount(query);
        double yesterdayPurchaseAmount = yesterdayPurchaseMap.get(PAY_AMOUNT) - yesterdayPurchaseMap.getOrDefault(REFUND_AMOUNT, 0.0) - todayPurchaseAmount;
        resultTupleList.add(new Tuple2<>(SupplierOrderAggTypeEnum.YESTERDAY_SALES, BigDecimal.valueOf(yesterdayPurchaseAmount)));

        dateRange.setLeft(thisWeekFirstDay());
        Map<String, Double> weekPurchaseMap = searchService.getPurchaseAmount(query);
        double weekPurchaseAmount = weekPurchaseMap.get(PAY_AMOUNT) - weekPurchaseMap.getOrDefault(REFUND_AMOUNT, 0.0);
        resultTupleList.add(new Tuple2<>(SupplierOrderAggTypeEnum.WEEK_SALES, BigDecimal.valueOf(weekPurchaseAmount)));

        dateRange.setLeft(thisMonthFirstDay());
        Map<String, Double> monthPurchaseMap = searchService.getPurchaseAmount(query);
        double monthPurchaseAmount = monthPurchaseMap.get(PAY_AMOUNT) - monthPurchaseMap.getOrDefault(REFUND_AMOUNT, 0.0);
        resultTupleList.add(new Tuple2<>(SupplierOrderAggTypeEnum.MONTH_SALES, BigDecimal.valueOf(monthPurchaseAmount)));

        return Result.buildSuccessResult(tupleToMap(resultTupleList));
    }

    private Date getToday() {
        return DateUtil.parseDate(DateUtil.formatDate(new Date(), DateUtil.YYYY_MM_DD));
    }

    private Date getYesterday() {
        return DateUtil.plusDays(getToday(), -1);
    }

    private Date thisWeekFirstDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, 1);
        return DateUtil.parseDate(DateUtil.formatDate(calendar.getTime(), DateUtil.YYYY_MM_DD));
    }

    private Date thisMonthFirstDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return DateUtil.parseDate(DateUtil.formatDate(calendar.getTime(), DateUtil.YYYY_MM_DD));
    }

    private Map<String, BigDecimal> tupleToMap(List<Tuple2<SupplierOrderAggTypeEnum, BigDecimal>> tupleList) {
        Map<String, BigDecimal> resultMap = new HashMap<>();
        tupleList.forEach(tuple ->
            resultMap.put(tuple.getV1().name(), tuple.getV2())
        );
        for (SupplierOrderAggTypeEnum aggType : SupplierOrderAggTypeEnum.values()) {
            resultMap.putIfAbsent(aggType.name(), BigDecimal.ZERO);
        }
        return resultMap;
    }
}
