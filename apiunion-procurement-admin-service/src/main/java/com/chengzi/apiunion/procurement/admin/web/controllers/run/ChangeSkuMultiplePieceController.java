package com.chengzi.apiunion.procurement.admin.web.controllers.run;

import com.chengzi.apiunion.common.data.beans.ListRouteOperate;
import com.chengzi.apiunion.common.data.beans.RouteOperate;
import com.chengzi.apiunion.common.data.beans.SimpleRouteOperate;
import com.chengzi.apiunion.common.event.EventBusFactory;
import com.chengzi.apiunion.common.web.controllers.AbstractManageController;
import com.chengzi.apiunion.item.dao.ItemSkuGroupMapper;
import com.chengzi.apiunion.item.dao.ItemSkuMapper;
import com.chengzi.apiunion.item.dao.ItemSkuValueMapper;
import com.chengzi.apiunion.item.event.pojo.ItemsUpdatedEvent;
import com.chengzi.apiunion.item.pojo.ItemSkuDO;
import com.chengzi.apiunion.item.pojo.ItemSkuGroupDO;
import com.chengzi.apiunion.item.pojo.ItemSkuValueDO;
import com.chengzi.apiunion.item.service.ItemSkuService;
import com.chengzi.apiunion.item.util.ItemShardUtil;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import com.chengzi.common.web.formbean.EmptyForm;
import org.summercool.mybatis.util.StackTraceUtil;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * @author 行者
 * 改造多件装修数据
 */
public class ChangeSkuMultiplePieceController extends AbstractManageController<EmptyForm> {

    @Override
    protected Result<?> doInnerBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {
        ItemSkuGroupMapper itemSkuGroupMapper = BeanFactory.getBean(ItemSkuGroupMapper.class);
        ItemSkuMapper itemSkuMapper = BeanFactory.getBean(ItemSkuMapper.class);
        ItemSkuService itemSkuService = BeanFactory.getBean(ItemSkuService.class);
        ItemSkuValueMapper itemSkuValueMapper = BeanFactory.getBean(ItemSkuValueMapper.class);
        List<ItemSkuGroupDO> itemSkuGroupDOList = itemSkuGroupMapper.selectAll(new RouteOperate());

        HashSet<Long> needDelskuGroupIds = new HashSet<>();
        HashSet<Long> failGroupIds = new HashSet<>();
        if (CollectionUtil.isNotEmpty(itemSkuGroupDOList)) {
            Map<Long, List<ItemSkuGroupDO>> itemIdSkuGroupMap = CollectionUtil.toListMap(itemSkuGroupDOList, "itemId");
            Map<Long, List<Long>> shardItemMap = ItemShardUtil.shardItemIds(itemIdSkuGroupMap.keySet());
            for (Map.Entry<Long, List<Long>> entry : shardItemMap.entrySet()) {
                Long shardItemId = entry.getKey();
                List<Long> itemIds = entry.getValue();
                List<ItemSkuDO> skuDOList = itemSkuMapper.selectByItemIds(ListRouteOperate.of(itemIds));
                Map<Object, ItemSkuDO> skuIdSkuDOMap = CollectionUtil.toMap(skuDOList, "id");

                List<ItemSkuValueDO> skuValueDOList = itemSkuValueMapper.selectByItemIds(ListRouteOperate.of(itemIds));
                Map<Long, List<ItemSkuValueDO>> skuIdskuValueDOMap = CollectionUtil.toListMap(skuValueDOList, "skuId");

                for (long itemId : itemIds) {
                    List<ItemSkuGroupDO> groupDOList = itemIdSkuGroupMap.get(itemId);
                    for (ItemSkuGroupDO groupDO : groupDOList) {
                        ItemSkuDO skuDO = skuIdSkuDOMap.get(groupDO.getSkuId());
                        ItemSkuDO outSkuDO = skuIdSkuDOMap.get(groupDO.getOutSkuId());
                        if (skuDO != null && outSkuDO != null) {
                            skuDO.setQuantity(groupDO.getQuantity());
                            skuDO.setSkuValue(outSkuDO.getSkuValue());
                            skuDO.setGroup(false);
                            try {
                                itemSkuMapper.update(skuDO);
                            } catch (Exception e) {
                                if (StackTraceUtil.isDuplicateKeyException(e)) {
                                    ItemSkuDO existedSkuDO = itemSkuMapper.selectByUdxKey(SimpleRouteOperate.of(skuDO.getItemId()), skuDO.getSkuValue(),skuDO.getQuantity());
                                    if (existedSkuDO != null) {
                                        skuDO.setId(existedSkuDO.getId());
                                        itemSkuMapper.update(skuDO);
                                    } else {
                                        failGroupIds.add(groupDO.getId());
                                        continue;
                                    }
                                } else {
                                    failGroupIds.add(groupDO.getId());
                                    continue;
                                }
                            }

                            List<ItemSkuValueDO> itemSkuValueDOList = skuIdskuValueDOMap.get(groupDO.getOutSkuId());
                            if (CollectionUtil.isNotEmpty(itemSkuValueDOList)) {
                                for (ItemSkuValueDO itemSkuValueDO : itemSkuValueDOList) {
                                    itemSkuValueDO.setId(0);
                                    itemSkuValueDO.setSkuId(groupDO.getSkuId());
                                    try {
                                        itemSkuValueMapper.insert(itemSkuValueDO);
                                    } catch (Exception e) {
                                        if (StackTraceUtil.isDuplicateKeyException(e)) {
                                            ItemSkuValueDO existedSkuValue = itemSkuValueMapper.selectBySkuNameId(SimpleRouteOperate.of(itemSkuValueDO.getItemId()),
                                                    itemSkuValueDO.getSkuId(), itemSkuValueDO.getSkuNameId());
                                            if (existedSkuValue != null) {
                                                itemSkuValueDO.setId(existedSkuValue.getId());
                                                itemSkuValueMapper.update(itemSkuValueDO);
                                            } else {
                                                failGroupIds.add(groupDO.getId());
                                            }
                                        } else {
                                            failGroupIds.add(groupDO.getId());
                                        }
                                    }
                                }
                            }
                            needDelskuGroupIds.add(groupDO.getId());
                        } else {
                            failGroupIds.add(groupDO.getId());
                        }
                    }
                }
            }
            if (CollectionUtil.isNotEmpty(needDelskuGroupIds)) {
                itemSkuGroupMapper.deleteByIds(ListRouteOperate.of(needDelskuGroupIds));
            }
            EventBusFactory.getMQEventBus().post(new ItemsUpdatedEvent(itemIdSkuGroupMap.keySet()));
        }
        return Result.buildSuccessResult("操作完成，失败数据如下: " + failGroupIds);
    }
}
