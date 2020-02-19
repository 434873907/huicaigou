/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.controllers.run;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.StringUtils;
import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.common.data.beans.ListRouteOperate;
import com.chengzi.apiunion.common.web.controllers.AbstractManageController;
import com.chengzi.apiunion.item.dao.ThirdItemMapper;
import com.chengzi.apiunion.item.enums.ItemStatusEnum;
import com.chengzi.apiunion.item.pojo.ItemRichDO;
import com.chengzi.apiunion.item.pojo.ItemSkuInfo;
import com.chengzi.apiunion.item.pojo.ItemSkuSupplierDO;
import com.chengzi.apiunion.item.pojo.ThirdItemDO;
import com.chengzi.apiunion.item.pojo.ThirdItemQuery;
import com.chengzi.apiunion.item.pojo.ThirdItemSkuAttr;
import com.chengzi.apiunion.item.service.ItemService;
import com.chengzi.apiunion.item.service.ItemSkuService;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import com.chengzi.common.web.formbean.EmptyForm;

/**
 * 清理同步商品
 * 
 * @author Kolor
 */
public class CleanSameSyncItemController extends AbstractManageController<EmptyForm> {

    @Override
    protected Result<?> doInnerBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {
        ThirdItemMapper thirdItemMapper = BeanFactory.getBean(ThirdItemMapper.class);
        ItemService itemService = BeanFactory.getBean(ItemService.class);
        ItemSkuService itemSkuService = BeanFactory.getBean(ItemSkuService.class);
        //        ItemSkuService itemSkuService = BeanFactory.getBean(ItemSkuSupplierService.class);

        ThirdItemQuery query = new ThirdItemQuery();
        List<ThirdItemDO> thirdItemList = thirdItemMapper.selectByQuery(query);
        Map<String, List<ThirdItemDO>> map = CollectionUtil.toListMap(thirdItemList, "authId + thirdItemId");

        Map<Long, Long> delItemIds = new HashMap<>();
        for (List<ThirdItemDO> list : map.values()) {
            if (list.size() <= 1) {
                continue;
            }
            List<Long> itemIds = CollectionUtil.getFieldValueList(list, "itemId");
            List<ItemRichDO> itemList = itemService.getItemRichByIds(itemIds);

            List<ItemSkuInfo> skuList = itemSkuService.getSkuInfosByItemIds(itemIds);
            Map<Long, List<ItemSkuInfo>> skuMap = CollectionUtil.toListMap(skuList, "itemId");

            List<ItemSkuSupplierDO> skuSupplierList = itemSkuService.getSkuSupplierByItemIds(itemIds, null);
            Map<Long, List<ItemSkuSupplierDO>> skuSupplierMap = CollectionUtil.toListMap(skuSupplierList, "skuId");

            Collections.sort(itemList, (x, y) -> {
                return y.getStatus() - x.getStatus();
            });

            for (int i = 0; i < itemList.size(); i++) {
                ItemRichDO x = itemList.get(i);
                List<ItemSkuInfo> xSkuList = skuMap.get(x.getId());
                for (int j = i + 1; j < itemList.size(); j++) {
                    ItemRichDO y = itemList.get(j);
                    if (delItemIds.containsKey(y.getId())) {
                        continue;
                    }

                    if (y.getStatus() == ItemStatusEnum.INIT.getCode()) {
                        List<ItemSkuInfo> ySkuList = skuMap.get(y.getId());
                        if (xSkuList.size() != ySkuList.size()) {
                            continue;
                        }
                        // 匹配SKU是否完全一致
                        boolean notMatch = false;
                        for (ItemSkuInfo xisi : xSkuList) {
                            List<ItemSkuSupplierDO> xSkuSuppliers = skuSupplierMap.get(xisi.getId());
                            boolean foundSku = false;
                            for (ItemSkuInfo yisi : ySkuList) {
                                if (StringUtils.equals(xisi.getSkuValues().toJsonString(), yisi.getSkuValues().toJsonString())) {
                                    foundSku = true;
                                    List<ItemSkuSupplierDO> ySkuSuppliers = skuSupplierMap.get(yisi.getId());
                                    if (CollectionUtil.isEmpty(xSkuSuppliers)) {
                                        if (CollectionUtil.isEmpty(ySkuSuppliers)) {
                                            continue;
                                        } else {
                                            notMatch = true;
                                            break;
                                        }
                                    } else {
                                        if (CollectionUtil.isEmpty(ySkuSuppliers)) {
                                            notMatch = true;
                                            break;
                                        }
                                    }

                                    if (xSkuSuppliers.size() != ySkuSuppliers.size()) {
                                        notMatch = true;
                                        break;
                                    } else {
                                        for (ItemSkuSupplierDO xiss : xSkuSuppliers) {
                                            ThirdItemSkuAttr xAttr = ThirdItemSkuAttr.fromJson(xiss.getThirdAttr());
                                            boolean foundSupplier = false;
                                            for (ItemSkuSupplierDO yiss : ySkuSuppliers) {
                                                if (xiss.getSupplierId() == yiss.getSupplierId()) {
                                                    ThirdItemSkuAttr yAttr = ThirdItemSkuAttr.fromJson(yiss.getThirdAttr());
                                                    if (xAttr.getThirdSkuId().equals(yAttr.getThirdSkuId())) {
                                                        foundSupplier = true;
                                                        break;
                                                    }
                                                }
                                            }
                                            if (!foundSupplier) {
                                                notMatch = true;
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                            if (!foundSku) {
                                notMatch = true;
                                break;
                            }
                        }
                        if (!notMatch) {
                            // 匹配供应商是否完全一致
                            delItemIds.put(y.getId(), x.getId());
                        }
                    }
                }
            }
        }

        Map<Long, List<Long>> refMap = new HashMap<>();
        for (Entry<Long, Long> entry : delItemIds.entrySet()) {
            long y = entry.getKey();
            long x = entry.getValue();

            Long p = delItemIds.get(x);
            while (p != null) {
                x = p;
                p = delItemIds.get(x);
            }

            List<Long> l = refMap.get(x);
            if (l == null) {
                refMap.put(x, l = new ArrayList<>());
            }
            l.add(y);
        }

        try {
            for (Entry<Long, List<Long>> entry : refMap.entrySet()) {
                thirdItemMapper.deleteByItemIds(ListRouteOperate.of(entry.getValue()));
                itemService.deleteByIds(entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Result.buildSuccessResult(refMap);
    }

}
