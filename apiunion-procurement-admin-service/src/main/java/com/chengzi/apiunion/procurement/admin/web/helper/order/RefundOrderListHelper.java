package com.chengzi.apiunion.procurement.admin.web.helper.order;

import com.chengzi.apiunion.common.data.search.elastic.pojo.OrderBy;
import com.chengzi.apiunion.common.data.search.elastic.pojo.SearchResult;
import com.chengzi.apiunion.order.enums.OrderStatusForStatisticEnum;
import com.chengzi.apiunion.order.pojo.OrderDO;
import com.chengzi.apiunion.order.pojo.OrderItemDO;
import com.chengzi.apiunion.order.pojo.search.OrderSearchBO;
import com.chengzi.apiunion.order.query.OrderSearchQuery;
import com.chengzi.apiunion.order.service.OrderSearchService;
import com.chengzi.apiunion.order.service.OrderService;
import com.chengzi.apiunion.procurement.admin.web.formbean.order.OrderListForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.order.RefundOrderListBO;
import com.chengzi.apiunion.user.pojo.UserDO;
import com.chengzi.apiunion.user.service.UserService;
import com.chengzi.common.data.support.Range;
import com.chengzi.common.util.CollectionUtil;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.search.sort.SortOrder;
import org.summercool.web.servlet.BeanFactory;

import java.util.*;

import static com.chengzi.apiunion.order.enums.OrderStatusEnum.*;
/**
 * <p>
 * </p>
 * User: 摩天
 * Date: 2019-02-12 17:15
 */
public class RefundOrderListHelper extends AbstractOrderListHelper<OrderSearchQuery, RefundOrderListBO> {

    @Override
    protected OrderSearchQuery buildCommonOrderSearchQuery(OrderListForm orderListForm) {
        OrderSearchQuery query = new OrderSearchQuery();
        query.setIsHaveRefund(true);
        query.setDateRange(new Range(orderListForm.getStartTime(), orderListForm.getEndTime()));
        query.setStatus(CollectionUtil.asArrayList(ORDER_STATUS_CREATE.getCode(),
                ORDER_STATUS_DEPOSIT.getCode(),
                ORDER_STATUS_WAIT_SEND.getCode(),
                ORDER_STATUS_WAIT_CONFIRM.getCode(),
                ORDER_STATUS_COMPLETE.getCode(),
                ORDER_STATUS_REFUND.getCode(),
                ORDER_STATUS_CLOSED.getCode()));
        if (!StringUtils.isEmpty(orderListForm.getKeyword())) {
            query.setKeyword(orderListForm.getKeyword());
        }

        if (CollectionUtil.isNotEmpty(orderListForm.getUserIds())) {
            query.setUserIds(new ArrayList<>(orderListForm.getUserIds()));
        }
        if (CollectionUtil.isNotEmpty(orderListForm.getSupplierIds())) {
            query.setSupplierIds(new ArrayList<>(orderListForm.getSupplierIds()));
        }
        if(orderListForm.getCreatePlatform() != null) {
            query.setCreatePlatform(orderListForm.getCreatePlatform());
        }
        if(orderListForm.getTagStatus() != null) {
            query.setTagStatus(orderListForm.getTagStatus());
        }
        query.setSize(orderListForm.getLimit());
        query.setFrom(orderListForm.getOffset());
        query.setOrderBy(CollectionUtil.asArrayList(new OrderBy("createTime", SortOrder.DESC)));
        query.setIsDeleted(null);
        return query;
    }

    @Override
    protected List<RefundOrderListBO> searchOrders(OrderSearchQuery orderSearchQuery) {
        List<RefundOrderListBO> orderListBOS = new ArrayList();
        Set<Long> buyerIds = new HashSet<>();
        Map<String, Long> OrderNumUserIdMap = new HashMap<>();
        OrderSearchService orderSearchService = BeanFactory.getBean(OrderSearchService.class);
        SearchResult<OrderSearchBO> orderSearchBOSearchResult = orderSearchService.query(orderSearchQuery);

        List<OrderSearchBO> orderSearchBOS = orderSearchBOSearchResult.getData();
        TOTAL_TL.set(orderSearchBOSearchResult.getTotalHits());

        orderSearchBOS.forEach(x -> {
            OrderNumUserIdMap.put(x.getOrderNum(), x.getUserId());
            buyerIds.add(x.getUserId());
        });
        UserService userService = BeanFactory.getBean(UserService.class);
        List<UserDO> userDOS = userService.getUserByUserIds(new ArrayList<>(buyerIds));
        OrderService orderService = BeanFactory.getBean(OrderService.class);
        List<OrderDO> orderDOS = orderService.getOrderByOrderNums(OrderNumUserIdMap);
        Map<String, List<OrderItemDO>> orderItemDOMap = orderService.queryByOrderNums(OrderNumUserIdMap);
        Map<Long, UserDO> userDOMap = new HashMap<>();
        for (UserDO userDO : userDOS) {
            userDOMap.put(userDO.getId(), userDO);
        }
        orderDOS.forEach(x -> {
            UserDO userDO = userDOMap.get(x.getUserId());
            RefundOrderListBO converter = RefundOrderListBO.converter(x, userDO, orderItemDOMap.get(x.getOrderNum()));
            orderListBOS.add(converter);
        });

        Collections.sort(orderListBOS);
        return orderListBOS;
    }

    @Override
    public Map<OrderStatusForStatisticEnum, Long> statisticByQuery(OrderListForm orderListForm) {
        OrderSearchService orderSearchService = BeanFactory.getBean(OrderSearchService.class);
        return orderSearchService.statisticByQuery(buildCommonOrderSearchQuery(orderListForm));
    }
}
