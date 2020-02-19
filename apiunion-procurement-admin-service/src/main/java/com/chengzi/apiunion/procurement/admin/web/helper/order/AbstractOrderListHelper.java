package com.chengzi.apiunion.procurement.admin.web.helper.order;

import com.chengzi.apiunion.common.data.search.elastic.pojo.BaseSearchQuery;
import com.chengzi.apiunion.order.constant.OrderStatusConstants;
import com.chengzi.apiunion.order.enums.OrderStatusForStatisticEnum;
import com.chengzi.apiunion.procurement.admin.web.formbean.order.OrderListForm;
import com.chengzi.common.data.beans.JsonPojo;
import com.chengzi.common.data.beans.PageDataList;
import com.chengzi.common.util.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * </p>
 * User: 摩天
 * Date: 2019-01-31 11:22
 */
public abstract class AbstractOrderListHelper<Q extends BaseSearchQuery, P extends JsonPojo> {

    /**
     * 日志对象
     */
    private static final Logger                                   logger                = LoggerFactory.getLogger(AbstractOrderListHelper.class);

    /**
     * 【订单状态-->订单列表辅助类】映射
     */
    private static volatile Map<Integer, AbstractOrderListHelper> ORDER_LIST_HELPER_MAP = null;

    /**
     * 订单总数ThreadLocal，用于线程上下文传参
     */
    protected static final ThreadLocal<Long>                      TOTAL_TL              = new ThreadLocal<Long>();

    public static AbstractOrderListHelper getInstance(int orderStatus) {
        if (ORDER_LIST_HELPER_MAP == null) {
            synchronized (AbstractOrderListHelper.class) {
                ORDER_LIST_HELPER_MAP = new HashMap<Integer, AbstractOrderListHelper>();

                DefaultOrderListHelper defaultOrderListHelper = new DefaultOrderListHelper();
                // 所有订单
                ORDER_LIST_HELPER_MAP.put(OrderStatusConstants.ALL, defaultOrderListHelper);
                // 待发货订单
                ORDER_LIST_HELPER_MAP.put(OrderStatusConstants.WAIT_DELIVER, defaultOrderListHelper);
                // 待收货订单
                ORDER_LIST_HELPER_MAP.put(OrderStatusConstants.WAIT_RECEIPT, defaultOrderListHelper);
                // 已完成订单
                ORDER_LIST_HELPER_MAP.put(OrderStatusConstants.COMPLETED, defaultOrderListHelper);
                // 已驳回订单
                ORDER_LIST_HELPER_MAP.put(OrderStatusConstants.REJECT, defaultOrderListHelper);
                // 退货退款订单
                ORDER_LIST_HELPER_MAP.put(OrderStatusConstants.REFUND, new RefundOrderListHelper());
                // 异常订单
                ORDER_LIST_HELPER_MAP.put(OrderStatusConstants.ABNORMAL, defaultOrderListHelper);
                // 待付款
                ORDER_LIST_HELPER_MAP.put(OrderStatusConstants.WAIT_PAYMENT, defaultOrderListHelper);
            }
        }

        if (!ORDER_LIST_HELPER_MAP.containsKey(orderStatus)) {
            orderStatus = OrderStatusConstants.ALL;
        }
        return ORDER_LIST_HELPER_MAP.get(orderStatus);
    }

    public PageDataList searchOrderList(OrderListForm orderListForm) {
        try {
            // 构建订单搜索查询对象
            Q q = buildCommonOrderSearchQuery(orderListForm);
            if (q != null) {

                // 查询订单
                List<P> dataList = searchOrders(q);
                if (CollectionUtil.isNotEmpty(dataList)) {
                    // 从ThreadLocal中拿到订单总数
                    long total = TOTAL_TL.get();
                    TOTAL_TL.remove();

                    return new PageDataList(total, orderListForm.getPage(), orderListForm.getLimit(), dataList);
                }
            }
        } catch (Exception e) {
            logger.error("AbstractOrderManageHelper.buildOrderListItemBaseBO() Error!", e);
            return null;
        }
        return new PageDataList();
    }

    /**
     * 构建搜索条件
     * @param orderListForm
     * @return
     */
    protected abstract Q buildCommonOrderSearchQuery(OrderListForm orderListForm);

    /**
     * 封装搜索结果
     * @param q
     * @return
     */
    protected abstract List<P> searchOrders(Q q);

    /**
     * 订单统计
     */
    public abstract Map<OrderStatusForStatisticEnum, Long> statisticByQuery(OrderListForm orderListForm);
}
