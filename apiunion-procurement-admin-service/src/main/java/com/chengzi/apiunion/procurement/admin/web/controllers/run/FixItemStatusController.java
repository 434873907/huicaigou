/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.controllers.run;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.summercool.web.servlet.BeanFactory;

import com.chengzi.apiunion.common.data.beans.SimpleRouteOperate;
import com.chengzi.apiunion.common.event.EventBusFactory;
import com.chengzi.apiunion.common.module.shard.enums.TableShardStrategyDefinitionEnum;
import com.chengzi.apiunion.common.module.shard.strategy.DivisorTableShardStrategy;
import com.chengzi.apiunion.common.mybatis.shard.ShardUtil;
import com.chengzi.apiunion.common.web.controllers.AbstractManageController;
import com.chengzi.apiunion.item.dao.ItemSkuMapper;
import com.chengzi.apiunion.item.dao.ItemSkuSupplierMapper;
import com.chengzi.apiunion.item.enums.ItemOfflineTypeEnum;
import com.chengzi.apiunion.item.enums.ItemStatusEnum;
import com.chengzi.apiunion.item.event.pojo.ItemStatusCheckEvent;
import com.chengzi.apiunion.item.pojo.ItemRichDO;
import com.chengzi.apiunion.item.pojo.ItemSkuInfo;
import com.chengzi.apiunion.item.pojo.ItemSkuSupplierDO;
import com.chengzi.apiunion.item.pojo.ItemSkuSupplierQuery;
import com.chengzi.apiunion.item.pojo.ThirdItemSkuAttr;
import com.chengzi.apiunion.item.service.ItemService;
import com.chengzi.apiunion.item.service.ItemSkuService;
import com.chengzi.common.data.enums.BooleanStatusEnum;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import com.chengzi.common.web.formbean.EmptyForm;

/**
 * 修复商品/规格状态
 * 
 * @author Kolor
 */
public class FixItemStatusController extends AbstractManageController<EmptyForm> {

    @Override
    protected Result<?> doInnerBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {
        ItemService itemService = BeanFactory.getBean(ItemService.class);
        ItemSkuService itemSkuService = BeanFactory.getBean(ItemSkuService.class);
        ItemSkuMapper itemSkuMapper = BeanFactory.getBean(ItemSkuMapper.class);

        ItemSkuSupplierMapper itemSkuSupplierMapper = BeanFactory.getBean(ItemSkuSupplierMapper.class);
        ItemSkuSupplierQuery query = new ItemSkuSupplierQuery();
        query.setMatchItemId(false);

        Set<Long> firedItemIds = new HashSet<>();
        Set<Long> noCateItemIds = new HashSet<>();
        int totalItemNum = 0;
        int totalSkuNum = 0;
        Set<String> tableNames = ShardUtil.loadTableSchema(TableShardStrategyDefinitionEnum.ITEM_SKU_SUPPLIER);
        DivisorTableShardStrategy shardStrategy = (DivisorTableShardStrategy) TableShardStrategyDefinitionEnum.ITEM_SKU_SUPPLIER.getShardStrategy();
        for (String tableName : tableNames) {
            int idx = StringUtils.lastIndexOf(tableName, "_");
            int suffixNum = Integer.parseInt(StringUtils.substring(tableName, idx + 1));

            query.setItemId(suffixNum * shardStrategy.getDivisor() + 1);
            List<ItemSkuSupplierDO> list = itemSkuSupplierMapper.selectByQuery(query);
            Map<Long, List<ItemSkuSupplierDO>> supplierMap = CollectionUtil.toListMap(list, "itemId");

            Set<Long> itemIds = CollectionUtil.getDisctinctFieldValueList(list, "itemId");
            List<ItemRichDO> itemList = itemService.getItemRichByIds(itemIds);
            Map<Long, ItemRichDO> itemMap = CollectionUtil.toMap(itemList, "id");

            List<ItemSkuInfo> skuList = itemSkuService.getSkuInfosByItemIds(itemIds);
            Map<Long, ItemSkuInfo> skuMap = CollectionUtil.toMap(skuList, "id");

            for (List<ItemSkuSupplierDO> l : supplierMap.values()) {
                Set<Long> onlineSkuIds = new HashSet<>();
                Set<Long> offlineSkuIds = new HashSet<>();
                Map<Long, List<ItemSkuSupplierDO>> map = CollectionUtil.toListMap(l, "skuId");

                boolean hasAvaliableSku = false;
                boolean hasOnlineSku = false;
                boolean hasThirdSupplier = false;
                for (List<ItemSkuSupplierDO> ll : map.values()) {
                    boolean hasOnline = false;
                    for (ItemSkuSupplierDO itemSkuSupplierDO : ll) {
                        ItemRichDO itemRich = itemMap.get(itemSkuSupplierDO.getItemId());
                        if (itemRich == null || itemRich.isDeleted()) {
                            continue;
                        }
                        ItemSkuInfo skuInfo = skuMap.get(itemSkuSupplierDO.getSkuId());
                        if (skuInfo == null) {
                            continue;
                        }
                        ThirdItemSkuAttr attr = ThirdItemSkuAttr.fromJson(itemSkuSupplierDO.getThirdAttr());
                        if (attr != null && attr.getAuthId() > 0) {
                            hasThirdSupplier = true;
                        }
                        if (itemSkuSupplierDO.isAvaliable()) {
                            hasAvaliableSku = true;
                        }
                        if (itemSkuSupplierDO.isStatusOnSale()) {
                            hasOnlineSku = true;
                            hasOnline = true;
                            if (skuInfo.getStatus() != BooleanStatusEnum.YES.getCode()) {
                                onlineSkuIds.add(skuInfo.getId());
                            }
                        }
                    }
                    if (!hasOnline) {
                        ItemSkuSupplierDO itemSkuSupplierDO = ll.get(0);

                        ItemRichDO itemRich = itemMap.get(itemSkuSupplierDO.getItemId());
                        if (itemRich == null || itemRich.isDeleted()) {
                            continue;
                        }
                        ItemSkuInfo skuInfo = skuMap.get(itemSkuSupplierDO.getSkuId());
                        if (skuInfo == null) {
                            continue;
                        }

                        if (skuInfo.getStatus() != BooleanStatusEnum.NO.getCode()) {
                            offlineSkuIds.add(skuInfo.getId());
                        }
                    }
                }
                // 更新规格状态
                if (!onlineSkuIds.isEmpty()) {
                    itemSkuMapper.updateStatus(SimpleRouteOperate.of(l.get(0).getItemId()), new ArrayList<>(onlineSkuIds),
                            BooleanStatusEnum.YES.getCode());
                    totalSkuNum++;
                }
                if (!offlineSkuIds.isEmpty()) {
                    itemSkuMapper.updateStatus(SimpleRouteOperate.of(l.get(0).getItemId()), new ArrayList<>(offlineSkuIds),
                            BooleanStatusEnum.NO.getCode());
                    totalSkuNum++;
                }
                // 更新商品状态
                ItemRichDO itemRich = itemMap.get(l.get(0).getItemId());
                if (itemRich != null) {
                    if (hasAvaliableSku) {
                        if (itemRich.getCateId3() > 0) {
                            if ((itemRich.getStatus() == ItemStatusEnum.INIT.getCode())
                                    || (itemRich.getStatus() == ItemStatusEnum.OFFLINE.getCode()
                                            && itemRich.getOfflineType() == ItemOfflineTypeEnum.AUTO_OFFLINE.getCode())) {
                                itemService.updateItemStatus(CollectionUtil.asArrayList(l.get(0).getItemId()), ItemStatusEnum.ONLINE, null, "数据修正上架");
                                totalItemNum++;
                            }
                        } else {
                            noCateItemIds.add(itemRich.getId());
                        }
                    } else if (hasOnlineSku) {
                        if (itemRich.getStatus() != ItemStatusEnum.OFFLINE.getCode() && hasThirdSupplier) {
                            itemService.updateItemStatus(CollectionUtil.asArrayList(l.get(0).getItemId()), ItemStatusEnum.OFFLINE,
                                    ItemOfflineTypeEnum.AUTO_OFFLINE, "数据修正上架");
                            totalItemNum++;
                        }
                    } else {
                        if (firedItemIds.add(itemRich.getId())) {
                            EventBusFactory.getSyncEventBus().post(new ItemStatusCheckEvent(CollectionUtil.asArrayList(itemRich.getId())));
                        }
                    }
                }
            }
        }
        return Result.buildSuccessResult(String.format("上架商品%d个，规格状态修改%d个，类目问题未上加%s", totalItemNum, totalSkuNum, noCateItemIds.toString()));
    }

}
