package com.chengzi.apiunion.procurement.admin.web.controllers.order;

import com.chengzi.apiunion.common.data.excel.ExcelRow;
import com.chengzi.apiunion.common.web.controllers.excel.AsyncImportExcelsController;
import com.chengzi.apiunion.express.pojo.OrderExpressImportDO;
import com.chengzi.apiunion.express.service.OrderExpressService;
import com.chengzi.apiunion.order.enums.OrderRefundStatusEnum;
import com.chengzi.apiunion.order.pojo.OrderDO;
import com.chengzi.apiunion.order.pojo.OrderItemDO;
import com.chengzi.apiunion.order.pojo.OrderRefundDO;
import com.chengzi.apiunion.order.service.OrderRefundService;
import com.chengzi.apiunion.order.service.OrderService;
import com.chengzi.apiunion.procurement.admin.web.formbean.order.UploadExpressForm;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 上传订单物流
 *
 * @author 月汐
 * @date 2018/12/12 16:35
 */
public class UploadExpressController extends AsyncImportExcelsController<UploadExpressForm> {
    @Override
    protected Result<?> doImport(HttpServletRequest request, HttpServletResponse response, UploadExpressForm command, Map<MultipartFile, List<ExcelRow>> fileMap) {
        OrderExpressService orderExpressService = BeanFactory.getBean(OrderExpressService.class);
        OrderService orderService = BeanFactory.getBean(OrderService.class);

        for (Map.Entry<MultipartFile, List<ExcelRow>> fileEntry : fileMap.entrySet()) {
            List<OrderExpressImportDO> importDOList = new ArrayList<>();
            for (int i = 1; i < fileEntry.getValue().size(); i++) {
                ExcelRow excelRow = fileEntry.getValue().get(i);
                String orderNum = excelRow.get(4);
                if (StringUtils.isBlank(orderNum)) {
                    continue;
                }
                String pkgNo = excelRow.get(5);
                long itemId = Long.parseLong(excelRow.get(7));
                long skuId = Long.parseLong(excelRow.get(9));
                int number = Integer.parseInt(excelRow.get(10));
                String expressCompany = excelRow.get(11);
                String expressNum = excelRow.get(12);
                long userId = Long.parseLong(excelRow.get(17));
                OrderExpressImportDO importDO = new OrderExpressImportDO();
                importDO.setOrderNum(orderNum);
                importDO.setPkgNo(pkgNo);
                importDO.setItemId(itemId);
                importDO.setSkuId(skuId);
                importDO.setNumber(number);
                importDO.setExpressCompany(expressCompany);
                importDO.setExpressNum(expressNum);
                importDOList.add(importDO);
                importDO.setRowNum(i + 1);
                importDO.setUserId(userId);
            }

            Map<String, Long> orderNumUserMap = new HashMap<>();
            importDOList.forEach(importDO -> orderNumUserMap.put(importDO.getOrderNum(), importDO.getUserId()));

            List<OrderDO> orders = orderService.getOrderByOrderNums(orderNumUserMap);
            Result verifyOrders = verifyOrders(orders);

            if (!verifyOrders.isSuccess()) {
                return verifyOrders;
            }

            Map<String, List<OrderItemDO>> orderNumItemsMap = orderService.queryByOrderNums(orderNumUserMap);

            List<OrderItemDO> orderItemList = new ArrayList<>();
            orderNumItemsMap.values().forEach(orderItemList::addAll);

            Result<?> result = orderExpressService.importExpressFromExl(importDOList, command.getType(), orderItemList);

            if (result.isSuccess()) {
                orderExpressService.verifyOrdersExpressStatus(orderNumUserMap);
            }
        }
        return Result.buildSuccessResult("添加成功");
    }

    private Result verifyOrders(List<OrderDO> orders) {
        OrderRefundService orderRefundService = BeanFactory.getBean(OrderRefundService.class);

        StringBuilder failStr = new StringBuilder();
        boolean canAddExpress = true;
        failStr.append("上传失败，订单");

        for (OrderDO order : orders) {
            if (order.getIsApplyRefund() == 1) {
                List<OrderRefundDO> orderRefundDOS = orderRefundService.queryRefundByOrderNum(order.getUserId(), order.getOrderNum());
                for (OrderRefundDO orderRefundDO : orderRefundDOS) {
                    if (orderRefundDO.getInitiator() == 1) {
                        if (orderRefundDO.getRefundStatus() == OrderRefundStatusEnum.REFUND_APPLY.getCode()) {
                            canAddExpress = false;
                            failStr.append(order.getOrderNum()).append(",");
                        }
                    }
                }
            }
        }

        if (canAddExpress) {
            return Result.buildEmptySuccess();
        } else {
            failStr.deleteCharAt(failStr.length() - 1);
            failStr.append("有取消申请待审核");
            return Result.buildOpFailedResult(failStr.toString());
        }
    }
}
