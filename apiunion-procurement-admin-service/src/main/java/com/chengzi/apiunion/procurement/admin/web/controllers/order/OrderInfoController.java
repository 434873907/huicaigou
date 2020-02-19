package com.chengzi.apiunion.procurement.admin.web.controllers.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chengzi.apiunion.common.module.config.pojo.ExpressConfigDO;
import com.chengzi.apiunion.common.module.config.service.ExpressConfigService;
import com.chengzi.apiunion.common.pay.PayPlatform;
import com.chengzi.apiunion.item.enums.ThirdSyncItemTypeEnum;
import com.chengzi.apiunion.item.pojo.ItemSkuInfo;
import com.chengzi.apiunion.item.pojo.cache.ItemCO;
import com.chengzi.apiunion.item.service.ItemService;
import com.chengzi.apiunion.order.enums.OrderAddressVerifyStatusEnum;
import com.chengzi.apiunion.order.pojo.OrderPayInfoDO;
import com.chengzi.apiunion.order.service.OrderPayInfoService;
import com.chengzi.common.data.beans.BaseDO;
import com.chengzi.common.data.enums.BooleanStatusEnum;
import com.chengzi.common.data.enums.PlatformEnum;
import com.chengzi.common.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.summercool.web.servlet.BeanFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chengzi.apiunion.common.module.currency.util.AmountUtil;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.express.pojo.OrderExpressDO;
import com.chengzi.apiunion.express.service.OrderExpressService;
import com.chengzi.apiunion.expresscompany.pojo.ExpressCompanyDO;
import com.chengzi.apiunion.expresscompany.service.ExpressCompanyService;
import com.chengzi.apiunion.item.pojo.ItemSkuDO;
import com.chengzi.apiunion.item.pojo.ItemSkuSupplierDO;
import com.chengzi.apiunion.item.service.ItemImageService;
import com.chengzi.apiunion.item.service.ItemSkuService;
import com.chengzi.apiunion.item.util.SkuUtil;
import com.chengzi.apiunion.order.enums.OrderPkgStatusEnum;
import com.chengzi.apiunion.order.enums.OrderStatusEnum;
import com.chengzi.apiunion.order.pojo.OrderDO;
import com.chengzi.apiunion.order.pojo.OrderDeliveryInfo;
import com.chengzi.apiunion.order.pojo.OrderItemDO;
import com.chengzi.apiunion.order.pojo.snapshot.FeeSnapshot;
import com.chengzi.apiunion.order.pojo.snapshot.ItemSnapshot;
import com.chengzi.apiunion.order.service.OrderService;
import com.chengzi.apiunion.procurement.admin.web.formbean.order.OrderInfoForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.order.OrderAddressBO;
import com.chengzi.apiunion.procurement.admin.web.pojo.order.OrderBO;
import com.chengzi.apiunion.procurement.admin.web.pojo.order.OrderBO.PayInfo;
import com.chengzi.apiunion.procurement.admin.web.pojo.order.OrderExpressBO;
import com.chengzi.apiunion.procurement.admin.web.pojo.order.OrderInfoBO;
import com.chengzi.apiunion.procurement.admin.web.pojo.order.OrderItemBO;
import com.chengzi.apiunion.procurement.admin.web.pojo.order.OrderItemBO.ThirdItemSkuAttrBO;
import com.chengzi.apiunion.procurement.admin.web.pojo.order.OrderPkgBO;
import com.chengzi.apiunion.supplier.pojo.SupplierDO;
import com.chengzi.apiunion.supplier.service.SupplierService;
import com.chengzi.apiunion.user.pojo.UserDO;
import com.chengzi.apiunion.user.service.UserService;
import com.chengzi.common.data.validate.ErrorConstants;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import com.chengzi.common.util.EnumUtil;

/**
 * <p>
 * </p>
 * User: 摩天
 * Date: 2018-10-30 10:55
 * 修改为可以查看已经被用户删除的订单
 */
public class OrderInfoController extends AbstractApiController<OrderInfoForm> {

    @Override
    protected Result<OrderInfoBO> doBiz(HttpServletRequest request, HttpServletResponse response, OrderInfoForm command)
            throws Exception {
        long userId = command.getUserId();
        String orderNum = command.getOrderNum();
//        int prderStatus = command.getOrderStatus();

        OrderService orderService = BeanFactory.getBean(OrderService.class);
        UserService userService = BeanFactory.getBean(UserService.class);
        ItemImageService itemImageService = BeanFactory.getBean(ItemImageService.class);
        OrderExpressService orderExpressService = BeanFactory.getBean(OrderExpressService.class);
        ExpressCompanyService expressCompanyService = BeanFactory.getBean(ExpressCompanyService.class);
        ExpressConfigService expressConfigService = BeanFactory.getBean(ExpressConfigService.class);
        ItemSkuService itemSkuService = BeanFactory.getBean(ItemSkuService.class);
        ItemService itemService = BeanFactory.getBean(ItemService.class);
        SupplierService supplierService = BeanFactory.getBean(SupplierService.class);
        OrderPayInfoService orderPayInfoService = BeanFactory.getBean(OrderPayInfoService.class);
        OrderDO orderDO = orderService.getByOrderNum(userId, orderNum);
        if (orderDO == null) {
            return Result.buildFailResult(ErrorConstants.ERR_ILLEGAL_ARGUMENT, "未查询到相应订单");
        }

        List<OrderItemDO> orderItemDOS = orderService.queryByOrderNum(userId, orderNum);
        UserDO userDO = userService.getUserById(orderDO.getUserId());

        //<pkgNum,OrderItemDO> 同一包裹号的商品
        Map<String, List<OrderItemDO>> pkgItemMap = new HashMap<>();

        //itemId
        List<Long> itemIds = new ArrayList<>();
        //pkgNo
        Set<String> pkgNos = new HashSet<>();
        //itemId-skuIds
        Map<Long, List<Long>> itemIdSkuIdsMap = new HashMap<>();

        OrderInfoBO orderInfoBO = new OrderInfoBO();
        OrderBO orderInfo = new OrderBO();

        orderItemDOS.forEach(x -> {
            List<OrderItemDO> orderItemDOList = pkgItemMap.get(x.getPkgNo());
            if (CollectionUtil.isEmpty(orderItemDOList)) {
                orderItemDOList = new ArrayList<>();
                pkgItemMap.put(x.getPkgNo(), orderItemDOList);
            }
            ItemSnapshot itemSnapshot = JSON.parseObject(x.getItemSnapshot(), ItemSnapshot.class);
            if (itemSnapshot.getThirdAttr() != null && x.getPkgStatus() != OrderPkgStatusEnum.ORDERED.getCode()) {
                orderInfo.setSupportPushOrder(true);
            }
            itemIds.add(x.getItemId());
            pkgNos.add(x.getPkgNo());
            orderItemDOList.add(x);
            if (OrderStatusEnum.ORDER_STATUS_WAIT_SEND.getCode() == x.getStatus()) {
                List<Long> skuIds = itemIdSkuIdsMap.computeIfAbsent(x.getItemId(), k -> new ArrayList<>());
                skuIds.add(x.getSkuId());
            }
        });
        FeeSnapshot feeSnapshot = JSON.parseObject(orderDO.getFeeSnapshot(), FeeSnapshot.class);
        //供应商
        Map<String, Map<Long, OrderItemBO.Supplier>> supplierMap = new HashMap<>();
        if (CollectionUtil.isNotEmpty(itemIdSkuIdsMap)) {
            List<ItemSkuSupplierDO> itemSkuSupplierDOList = new ArrayList<>();
            for (Map.Entry<Long, List<Long>> entry : itemIdSkuIdsMap.entrySet()) {
                List<ItemSkuSupplierDO> itemSkuSupplierDOS = itemSkuService.getSkuSupplierBySkuIds(entry.getValue(), entry.getKey());
                itemSkuSupplierDOList.addAll(itemSkuSupplierDOS);
            }
            Set<Long> supplierIds = CollectionUtil.getDisctinctFieldValueList(itemSkuSupplierDOList, "supplierId");
            List<SupplierDO> supplierDOS = supplierService.getSuppliers(CollectionUtil.asArrayListFromIterable(supplierIds));
            Map<Object, SupplierDO> supplierDOMap = CollectionUtil.toMap(supplierDOS, "id");
            for (ItemSkuSupplierDO itemSkuSupplierDO : itemSkuSupplierDOList) {
                if (itemSkuSupplierDO.getSupplierQuantity() <= 0) {
                    continue;
                }
                Map<Long, OrderItemBO.Supplier> suppliers = supplierMap.computeIfAbsent(itemSkuSupplierDO.getItemId() + "-" + itemSkuSupplierDO.getSkuId(), k -> new HashMap<>());
                OrderItemBO.Supplier supplier = new OrderItemBO.Supplier();
                supplier.setId(itemSkuSupplierDO.getSupplierId());
                supplier.setName(supplierDOMap.get(itemSkuSupplierDO.getSupplierId()).getSupplierName());
                suppliers.put(supplier.getId(), supplier);
            }
        }
        //订单信息
        orderInfo.setBuyer(userDO.getNickName());
        orderInfo.setBuyerRemark(userDO.getRemark());
        orderInfo.setOrderNum(orderDO.getOrderNum());
        orderInfo.setUserOrderNum(orderDO.getUserOrderNum());
        orderInfo.setOrderStatus(orderDO.getStatus());
        orderInfo.setExpAmount(feeSnapshot.getExpAmount().doubleValue());
        orderInfo.setTotalAmount(orderDO.getTotalAmount());
        orderInfo.setPayAmount(orderDO.getPayAmount());
        orderInfo.setCouponAmount(AmountUtil.formatDisplayDecimal(feeSnapshot.getCouponAmount()).doubleValue()
                + AmountUtil.formatDisplayDecimal(feeSnapshot.getActDeductAmonut()).doubleValue());
        orderInfo.setItemAmount(feeSnapshot.getItemAmount().doubleValue());
        orderInfo.setCreateTime(DateUtil.formatDate(orderDO.getCreateTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
        orderInfo.setAddressVerifyStatus(orderDO.getAddressVerifyStatus());
        orderInfo.setAddressVerifyDesc(orderDO.getAddressVerifyDesc());
        PlatformEnum platformEnum = PlatformEnum.parse(orderDO.getCreatePlatform());
        if (platformEnum != null) {
            orderInfo.setCreatePlatform(platformEnum.getName());
        }
        // 获取支付信息
        List<OrderPayInfoDO> orderPayInfos = orderPayInfoService.queryByOrderNum(userId, orderNum);
        List<PayInfo> payInfoList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(orderPayInfos)) {
            for (OrderPayInfoDO orderPayInfoDO : orderPayInfos) {
                PayInfo payInfo = new PayInfo();
                payInfo.setPayAmount(
                        AmountUtil.getDisplayPriceStartWithSymbol(orderDO.getCurrency(), orderPayInfoDO.getPayAmount() == null ? BigDecimal.ZERO : orderPayInfoDO.getPayAmount())
                );
                payInfo.setPayMethod(PayPlatform.parse(orderPayInfoDO.getPayType()).getName());
                payInfo.setPayTradeNo(orderPayInfoDO.getPayTradeNo());
                payInfo.setPayTradeAccount(orderPayInfoDO.getPayTradeAccount());
                payInfo.setPayTime(orderPayInfoDO.getPayTime());
                payInfoList.add(payInfo);
            }
        }
        orderInfo.setPayInfoList(payInfoList);

        orderInfoBO.setOrderInfo(orderInfo);

        //地址信息
        OrderDeliveryInfo oderDeliveryInfo = JSONObject.parseObject(orderDO.getOrderDeliveryInfo(), OrderDeliveryInfo.class);
        OrderAddressBO orderAddressInfo = OrderAddressBO.convert(orderDO, oderDeliveryInfo);
        orderInfoBO.setReceiverInfo(orderAddressInfo);

        if (StringUtils.isNotEmpty(orderDO.getOriginalDeliveryInfo())) {
            OrderDeliveryInfo originalDeliveryInfo = JSONObject.parseObject(orderDO.getOriginalDeliveryInfo(), OrderDeliveryInfo.class);
            OrderAddressBO originalAddressInfo = OrderAddressBO.convert(orderDO, originalDeliveryInfo);
            orderInfoBO.setOriginalReceiverInfo(originalAddressInfo);
        }

        //<itemId,imgUrl>
        Map<Long, String> itemMainImgMap = itemImageService.getItemMainImageByItemIds(itemIds);
        //<pkgNo,Lig<OrderExpressDO>>
        Map<String, List<OrderExpressDO>> orderAddressMap = orderExpressService.getByPkgNos(new ArrayList<>(pkgNos), orderDO.getUserId());
        //skuInfo
        List<ItemSkuInfo> skuInfoList = itemSkuService.getSkuInfosByItemIds(itemIds);
        List<ItemCO> itemCOList = itemService.getItemWithCacheByIds(itemIds);
        Map<Object, ItemCO> itemIdItemCOMap = CollectionUtil.toMap(itemCOList, "id");
        Map<String, String> itemSkuMap = new HashMap<>();
        for (ItemSkuInfo skuInfo : skuInfoList) {
            itemSkuMap.put(skuInfo.getItemId() + "-" + skuInfo.getId(), SkuUtil.buildSkuDescIncludeGroup(skuInfo, itemIdItemCOMap.get(skuInfo.getItemId()).getUnit()));
        }

        //商品信息
        List<OrderPkgBO> orderItemInfoList = new ArrayList<>();
        pkgItemMap.entrySet().forEach(x -> {
            OrderPkgBO orderPkgBO = new OrderPkgBO();

            List<OrderItemDO> orderItemDOList = x.getValue();
            List<OrderItemBO> pkgItemInfo = new ArrayList<>();
            orderItemDOList.forEach(y -> {
                ItemSnapshot itemSnapshot = JSON.parseObject(y.getItemSnapshot(),
                        ItemSnapshot.class);

                OrderItemBO orderItemInfo = new OrderItemBO();
                orderItemInfo.setOrderItemId(y.getId());
                orderItemInfo.setSpec(itemSkuMap.get(y.getItemId() + "-" + y.getSkuId()));
                orderItemInfo.setChannelPrice(itemSnapshot.getChannelPrice().doubleValue());
                orderItemInfo.setCount(y.getBuyNum());
                orderItemInfo.setItemNum(y.getItemId() + "");
                orderItemInfo.setItemImg(itemMainImgMap.get(y.getItemId()));
                orderItemInfo.setItemTitle(itemSnapshot.getItemName());
                orderItemInfo.setPrice(itemSnapshot.getPrice().doubleValue());
                orderItemInfo.setSkuNum(y.getSkuId() + "");
                orderItemInfo.setWeight(itemSnapshot.getItemWeight());
                orderItemInfo.setSupplierId(itemSnapshot.getSupplierId());
                orderItemInfo.setSupplierName(itemSnapshot.getSupplierName());
                orderItemInfo.setStatus(y.getStatus());
                if (itemSnapshot.getThirdAttr() != null && itemSnapshot.getThirdAttr().getAuthId() > 0) {
                    orderPkgBO.setSupportOperateExpress(false);
                    orderItemInfo.setPkgStatus(y.getPkgStatus());
                    orderItemInfo.setPkgStatusName(EnumUtil.parse(OrderPkgStatusEnum.class, y.getPkgStatus()).getName());
                    orderItemInfo.setPkgStatusDesc(y.getPkgStatusDesc());

                    if (itemSnapshot.getThirdAttr().getApiType() != ThirdSyncItemTypeEnum.NULL_EXP.getCode() &&
                            itemSnapshot.getThirdAttr().getApiType() != ThirdSyncItemTypeEnum.SUPER_EXP.getCode() &&
                            itemSnapshot.getThirdAttr().getApiType() != ThirdSyncItemTypeEnum.SUPER_EXP_STORAGE.getCode()) {
                        orderInfoBO.setSupportChangeAddress(false);
                    }

                    if (orderDO.getAddressVerifyStatus() != OrderAddressVerifyStatusEnum.NORMAL.getCode() &&
                            orderDO.getAddressVerifyStatus() != OrderAddressVerifyStatusEnum.VERIFY_FAILED.getCode()) {
                        orderInfoBO.setSupportChangeAddress(false);
                    }

                    if (orderDO.getStatus() != OrderStatusEnum.ORDER_STATUS_WAIT_SEND.getCode() &&
                            orderDO.getStatus() != OrderStatusEnum.ORDER_STATUS_WAIT_CONFIRM.getCode()) {
                        orderInfoBO.setSupportChangeAddress(false);
                    }

                    ThirdItemSkuAttrBO thirdAttrBO = new ThirdItemSkuAttrBO();
                    BeanUtils.copyProperties(itemSnapshot.getThirdAttr(), thirdAttrBO);
                    orderItemInfo.setThirdAttr(thirdAttrBO);
                }
                orderItemInfo.setChannelType(itemSnapshot.getChannelType());
                orderItemInfo.setChannelName(itemSnapshot.getChannelName());
                if (OrderStatusEnum.ORDER_STATUS_WAIT_SEND.getCode() == y.getStatus()) {
                    if (supplierMap.get(y.getItemId() + "-" + y.getSkuId()) != null) {
                        orderItemInfo.setSupplierList(CollectionUtil.asArrayListFromIterable(supplierMap.get(y.getItemId() + "-" + y.getSkuId()).values()));
                    } else {
                        orderItemInfo.setSupplierList(new ArrayList<>());
                    }
                } else {
                    orderItemInfo.setSupplierList(new ArrayList<>());
                }
                pkgItemInfo.add(orderItemInfo);
            });

            List<OrderExpressBO> orderExpressInfos = new ArrayList<>();
            List<OrderExpressDO> orderExpressDOS = orderAddressMap.get(x.getKey());
            if (!CollectionUtil.isEmpty(orderExpressDOS)) {
                for (OrderExpressDO expressDO : orderExpressDOS) {
                    OrderExpressBO pkgExpressInfo = new OrderExpressBO();
                    ExpressCompanyDO expressCompanyDO = expressCompanyService.getById(expressDO.getExpressCompany());
                    ExpressConfigDO expressConfig = expressConfigService.selectByIdFromCache(expressCompanyDO.getExpressConfigId());
                    Map<String, String> expressCompanyMap = expressConfigService.getExpressMap();
                    pkgExpressInfo.setCompany(expressCompanyDO.getCompanyName());
                    if (expressConfig.isHide()) {
                        String companyName = expressCompanyMap.get(expressDO.getRealExpressCompanyName());
                        if (StringUtils.isNotBlank(companyName)) {
                            pkgExpressInfo.setExpressName(companyName);
                        } else {
                            pkgExpressInfo.setExpressName(expressDO.getRealExpressCompanyName());
                        }
                        pkgExpressInfo.setExpressNum(expressDO.getRealExpressNum());
                        pkgExpressInfo.setCompanyId(expressDO.getExpressCompany());
                    } else {
                        pkgExpressInfo.setExpressName(expressCompanyDO.getCompanyName());
                        pkgExpressInfo.setExpressNum(expressDO.getExpressNum());
                        pkgExpressInfo.setCompanyId(expressDO.getExpressCompany());
                    }
                    String expressDetail = expressDO.getDetail();
                    pkgExpressInfo.setExpressDetail(formatExpressDetail(expressDetail));
                    pkgExpressInfo.setExpressId(expressDO.getId());
                    orderExpressInfos.add(pkgExpressInfo);
                }
            }
            orderPkgBO.setPkgNo(x.getKey());
            orderPkgBO.setPkgExpressInfos(orderExpressInfos);
            orderPkgBO.setPkgItemInfo(pkgItemInfo);
            orderItemInfoList.add(orderPkgBO);

        });

        if (orderDO.getIsDeleted() == BaseDO.DELETED) {
            orderInfoBO.getOrderInfo().setDesc("该订单已被用户删除");
        }

        orderInfoBO.setOrderItemInfo(orderItemInfoList);

        return Result.buildSuccessResult(orderInfoBO);
    }


    /**
     * 格式化物流信息
     */
    @SuppressWarnings("rawtypes")
    public static List<String> formatExpressDetail(String detail) {
        if (StringUtils.isBlank(detail)) {
            return new ArrayList<>();
        }
        try {
            JSONArray arr = JSON.parseArray(detail);
            List<String> expressDetails = new ArrayList<>();
            for (Object item : arr) {
                if (item instanceof Map && !((Map) item).isEmpty()) {
                    Map.Entry entry = (Map.Entry) ((Map) item).entrySet().iterator().next();
                    expressDetails.add(entry.getKey() + " " + entry.getValue());
                } else {
                    expressDetails.add(item.toString());
                }
            }
            return expressDetails;
        } catch (Exception e) {
            return CollectionUtil.asArrayList(detail);
        }
    }
}
