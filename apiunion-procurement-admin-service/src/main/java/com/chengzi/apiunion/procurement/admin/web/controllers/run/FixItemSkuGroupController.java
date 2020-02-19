package com.chengzi.apiunion.procurement.admin.web.controllers.run;

import com.chengzi.apiunion.common.data.beans.ListRouteOperate;
import com.chengzi.apiunion.common.data.beans.RouteQuery;
import com.chengzi.apiunion.common.web.controllers.AbstractManageController;
import com.chengzi.apiunion.item.dao.ItemSkuGroupMapper;
import com.chengzi.apiunion.item.pojo.ItemSkuDO;
import com.chengzi.apiunion.item.pojo.ItemSkuGroupDO;
import com.chengzi.apiunion.item.service.ItemSkuService;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import com.chengzi.common.web.formbean.EmptyForm;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 月汐
 * @date 2019/10/24 15:21
 */
public class FixItemSkuGroupController extends AbstractManageController<EmptyForm> {

    private static final int PAGE_SIZE = 10000;

    @Override
    protected Result<?> doInnerBiz(HttpServletRequest request, HttpServletResponse response, EmptyForm command) throws Exception {
        ItemSkuGroupMapper itemSkuGroupMapper = BeanFactory.getBean(ItemSkuGroupMapper.class);
        ItemSkuService itemSkuService = BeanFactory.getBean(ItemSkuService.class);
        long count = itemSkuGroupMapper.countSkuGroup();
        int page = (int) count / PAGE_SIZE + 1;
        int deleteNum = 0;

        for (int i = 0; i < page; i++) {
            RouteQuery query = new RouteQuery();
            query.setOffset(PAGE_SIZE * i);
            query.setLimit(PAGE_SIZE);
            List<ItemSkuGroupDO> itemSkuGroupDOS = itemSkuGroupMapper.selectByPage(query);
            List<ItemSkuDO> itemSkuList = itemSkuService.getSkusByItemIds(CollectionUtil.getFieldValueList(itemSkuGroupDOS, "itemId"));
            Map<Long, ItemSkuDO> itemSkuMap = CollectionUtil.toMap(itemSkuList, "id");

            List<Long> deleteIds = new ArrayList<>();
            for (ItemSkuGroupDO itemSkuGroup : itemSkuGroupDOS) {
                if (itemSkuMap.get(itemSkuGroup.getSkuId()) == null) {
                    logger.error("sku不存在" + itemSkuGroup.toJsonString());
                    continue;
                }
                if (itemSkuGroup.getItemId() != itemSkuMap.get(itemSkuGroup.getSkuId()).getItemId()) {
                    deleteIds.add(itemSkuGroup.getId());
                }
            }

            if (CollectionUtil.isNotEmpty(deleteIds)) {
                deleteNum += itemSkuGroupMapper.deleteByIds(ListRouteOperate.of(deleteIds));
            }
        }
        return Result.buildSuccessResult("本次共清理" + deleteNum + "条数据");
    }
}
