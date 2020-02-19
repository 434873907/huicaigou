package com.chengzi.apiunion.procurement.admin.web.helper.order;

import com.chengzi.apiunion.common.data.search.elastic.pojo.OrderBy;
import com.chengzi.apiunion.common.data.search.elastic.pojo.SearchResult;
import com.chengzi.apiunion.order.enums.OrderStatusForStatisticEnum;
import com.chengzi.apiunion.order.pojo.OrderAbnormalDO;
import com.chengzi.apiunion.order.pojo.OrderDO;
import com.chengzi.apiunion.order.pojo.OrderItemDO;
import com.chengzi.apiunion.order.pojo.search.OrderSearchBO;
import com.chengzi.apiunion.order.query.OrderSearchQuery;
import com.chengzi.apiunion.order.service.OrderAbnormalService;
import com.chengzi.apiunion.order.service.OrderSearchService;
import com.chengzi.apiunion.order.service.OrderService;
import com.chengzi.apiunion.procurement.admin.web.formbean.order.OrderListForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.order.OrderListBO;
import com.chengzi.apiunion.user.pojo.UserDO;
import com.chengzi.apiunion.user.service.UserService;
import com.chengzi.common.data.support.Range;
import com.chengzi.common.util.CollectionUtil;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.search.sort.SortOrder;
import org.summercool.util.LangUtil;
import org.summercool.web.servlet.BeanFactory;

import java.util.*;

import static com.chengzi.apiunion.order.constant.OrderStatusConstants.*;
import static com.chengzi.apiunion.order.enums.OrderStatusEnum.*;
import static com.chengzi.apiunion.procurement.admin.web.pojo.order.OrderListBO.converter;

/**
 * <p>
 * </p>
 * User: 摩天
 * Date: 2019-02-12 17:12
 */
public class DefaultOrderListHelper extends AbstractOrderListHelper<OrderSearchQuery,OrderListBO> {

    @Override
    protected OrderSearchQuery buildCommonOrderSearchQuery(OrderListForm orderListForm) {
        OrderSearchQuery query = new OrderSearchQuery();
        query.setDateRange(new Range(orderListForm.getStartTime(), orderListForm.getEndTime()));
        List<Integer> orderStatus = new ArrayList<>();
        switch (orderListForm.getStatus()) {
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
            //todo louchongxiao 订单异常标记，暂时不返回数据
            case ABNORMAL:
                query.setAbnormalStatus(1);
                if (orderListForm.getAbnormalType() > 0) {
                    query.setAbnormalType(orderListForm.getAbnormalType());
                }
                break;
            case CLOSE:
                orderStatus.add(700);
                break;
            case WAIT_PAYMENT:
                orderStatus.add(ORDER_STATUS_CREATE.getCode());
                orderStatus.add(ORDER_STATUS_CREATE_DEPOSIT.getCode());
                orderStatus.add(ORDER_STATUS_CREATE_OFFLINE.getCode());
                orderStatus.add(ORDER_STATUS_DEPOSIT.getCode());
        }
        query.setStatus(orderStatus);
        // if (!StringUtils.isEmpty(orderListForm.getKeyword())) {
        //     if (orderListForm.getKeywordType() == ITEM_ID.getCode()) {
        //         query.setItemId(LangUtil.parseLong(orderListForm.getKeyword()));
        //     } else if (orderListForm.getKeywordType() == ITEM_NAME.getCode()) {
        //         query.setItemName(orderListForm.getKeyword());
        //     } else if (orderListForm.getKeywordType() == RECEIVER_NAME.getCode()) {
        //         query.setReceiverName(orderListForm.getKeyword());
        //     } else if (orderListForm.getKeywordType() == ORDER_NUM.getCode()) {
        //         query.setOrderNum(orderListForm.getKeyword());
        //     }
        // }

        query.setKeyword(orderListForm.getKeyword());

        if (CollectionUtil.isNotEmpty(orderListForm.getUserIds())) {
            query.setUserIds(new ArrayList<>(orderListForm.getUserIds()));
        }
        if (CollectionUtil.isNotEmpty(orderListForm.getSupplierIds())) {
            query.setSupplierIds(new ArrayList<>(orderListForm.getSupplierIds()));
        }
        if(orderListForm.getIsDelete() != null) {
            query.setIsDeleted(orderListForm.getIsDelete() == 1 ? true: false);
        } else {
            query.setIsDeleted(null);
        }
        if(orderListForm.getTagStatus() != null) {
            query.setTagStatus(orderListForm.getTagStatus());
        }
        if(orderListForm.getCreatePlatform() != null) {
            query.setCreatePlatform(orderListForm.getCreatePlatform());
        }
        query.setSize(orderListForm.getLimit());
        query.setFrom(orderListForm.getOffset());
        query.setOrderBy(CollectionUtil.asArrayList(new OrderBy("createTime", SortOrder.DESC)));
        query.setIsDeleted(null);
        return query;
    }

    @Override
    protected List<OrderListBO> searchOrders(OrderSearchQuery orderSearchQuery) {
        List<OrderListBO> orderListBOS = new ArrayList();
        Set<Long> buyerIds = new HashSet<>();
        Map<String, Long> orderNumUserIdMap = new HashMap<>();
        OrderSearchService orderSearchService = BeanFactory.getBean(OrderSearchService.class);
        SearchResult<OrderSearchBO> orderSearchBOSearchResult = orderSearchService.query(orderSearchQuery);

        List<OrderSearchBO> orderSearchBOS = orderSearchBOSearchResult.getData();
        TOTAL_TL.set(orderSearchBOSearchResult.getTotalHits());

        orderSearchBOS.forEach(x -> {
            orderNumUserIdMap.put(x.getOrderNum(), x.getUserId());
            buyerIds.add(x.getUserId());
        });
        UserService userService = BeanFactory.getBean(UserService.class);
        List<UserDO> userDOS = userService.getUserByUserIds(new ArrayList<>(buyerIds));

        OrderService orderService = BeanFactory.getBean(OrderService.class);
        OrderAbnormalService orderAbnormalService = BeanFactory.getBean(OrderAbnormalService.class);
//        List<OrderDO> orderDOS = orderService.getOrderByOrderNums(orderNumUserIdMap);
        List<OrderDO> orderDOS = orderService.selectAllOrderByOrderNums(orderNumUserIdMap);
        Map<String, List<OrderItemDO>> orderItemDOMap = orderService.queryByOrderNums(orderNumUserIdMap);
        Map<String, List<OrderAbnormalDO>> orderNumAbnormalsMap = orderAbnormalService.queryByOrderNums(orderNumUserIdMap);
        Map<Long, UserDO> userDOMap = new HashMap<>();
        for (UserDO userDO : userDOS) {
            userDOMap.put(userDO.getId(), userDO);
        }
        orderDOS.forEach(x -> {
            UserDO userDO = userDOMap.get(x.getUserId());
            orderListBOS.add(converter(x, userDO, orderItemDOMap.get(x.getOrderNum()), orderNumAbnormalsMap.get(x.getOrderNum())));
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
