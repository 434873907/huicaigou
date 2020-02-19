package com.chengzi.apiunion.procurement.admin.web.controllers.order;

import com.alibaba.fastjson.JSON;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.pojo.ItemSkuSupplierDO;
import com.chengzi.apiunion.item.service.ItemImageService;
import com.chengzi.apiunion.item.service.ItemSkuService;
import com.chengzi.apiunion.order.pojo.OrderItemDO;
import com.chengzi.apiunion.order.pojo.snapshot.ItemSnapshot;
import com.chengzi.apiunion.order.service.OrderService;
import com.chengzi.apiunion.procurement.admin.web.formbean.order.OrderItemListForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.order.OrderItemListBO;
import com.chengzi.apiunion.supplier.pojo.SupplierDO;
import com.chengzi.apiunion.supplier.service.SupplierService;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * <p>
 *     更换供应商OrderItem列表
 * </p>
 * User: 摩天
 * Date: 2018-11-15 11:41
 */
public class OrderItemListController extends AbstractApiController<OrderItemListForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, OrderItemListForm command) throws Exception {
        long userId = command.getUserId();
        String orderNum = command.getOrderNum();

        OrderService orderService = BeanFactory.getBean(OrderService.class);
        ItemSkuService itemSkuService = BeanFactory.getBean(ItemSkuService.class);
        ItemImageService itemImageService = BeanFactory.getBean(ItemImageService.class);
        SupplierService supplierService = BeanFactory.getBean(SupplierService.class);

        Set<Long> supplierIds = new HashSet<>();
        Map<Long, Set<Long>> itemSkuIdMap = new HashMap<>();
        Map<Long, List<ItemSkuSupplierDO>> skuSupplierMap = new HashMap<>();
        Map<Long, SupplierDO> supplierMap = new HashMap<>();

        List<OrderItemDO> orderItemDOList = orderService.queryByOrderNum(userId, orderNum);

        for (OrderItemDO orderItemDO : orderItemDOList) {
            Set<Long> skuIds = itemSkuIdMap.get(orderItemDO.getItemId());
            if (CollectionUtil.isEmpty(skuIds)) {
                skuIds = new HashSet<>();
                itemSkuIdMap.put(orderItemDO.getItemId(), skuIds);
            }
            skuIds.add(orderItemDO.getSkuId());
        }

        //获取供应商
        for (Map.Entry<Long, Set<Long>> entry : itemSkuIdMap.entrySet()) {
            List<ItemSkuSupplierDO> itemSkuSupplierDOS = itemSkuService.getSkuSupplierBySkuIds(new ArrayList<>(entry.getValue()), entry.getKey());
            itemSkuSupplierDOS.forEach(x -> {
                if (x.getSupplierQuantity() > 0) {
                    supplierIds.add(x.getSupplierId());
                }

                List<ItemSkuSupplierDO> supplierDOS = skuSupplierMap.get(x.getSkuId());
                if (CollectionUtil.isEmpty(supplierDOS)) {
                    supplierDOS = new ArrayList<>();
                    skuSupplierMap.put(x.getSkuId(), supplierDOS);
                }
                supplierDOS.add(x);
            });
        }
        List<SupplierDO> supplierDOS = supplierService.getSuppliers(new ArrayList<>(supplierIds));
        for (SupplierDO supplierDO : supplierDOS) {
            supplierMap.put(supplierDO.getId(), supplierDO);
        }

        //获取商品主图图片
        Map<Long, String> itemMainImgMap = itemImageService.getItemMainImageByItemIds(itemSkuIdMap.keySet());

        List<OrderItemListBO> orderItemListBOS = new ArrayList<>();
        orderItemDOList.forEach(x -> {
            ItemSnapshot itemSnapshot = JSON.parseObject(x.getItemSnapshot(), ItemSnapshot.class);
            OrderItemListBO orderItemListBO = new OrderItemListBO();
            orderItemListBO.setCount(x.getBuyNum());
            orderItemListBO.setItemNum(x.getItemId() + "");
            orderItemListBO.setSupplierName(itemSnapshot.getSupplierName());
            orderItemListBO.setItemTitle(itemSnapshot.getItemName());
            orderItemListBO.setItemImg(itemMainImgMap.get(x.getItemId()));
            List<ItemSkuSupplierDO> supplierDOList = skuSupplierMap.get(x.getSkuId());
            for (ItemSkuSupplierDO supplierDO : supplierDOList) {
                if (itemSnapshot.getSupplierId() != supplierDO.getSupplierId()) {
                    orderItemListBO.addOtherSupplier(supplierDO.getSupplierId(), supplierMap.get(supplierDO.getSupplierId()).getSupplierName());
                }
            }

            orderItemListBOS.add(orderItemListBO);
        });
        return Result.buildSuccessResult(orderItemListBOS);
    }
}
