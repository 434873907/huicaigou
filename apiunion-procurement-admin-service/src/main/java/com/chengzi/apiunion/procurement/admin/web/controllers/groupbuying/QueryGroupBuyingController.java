package com.chengzi.apiunion.procurement.admin.web.controllers.groupbuying;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.pojo.cache.ItemCO;
import com.chengzi.apiunion.item.service.ItemService;
import com.chengzi.apiunion.procurement.admin.web.formbean.groupbuying.QueryGroupBuyingForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.groupbuying.QueryGroupBuyingBO;
import com.chengzi.apiunion.groupbuying.pojo.GroupBuyingDO;
import com.chengzi.apiunion.groupbuying.pojo.GroupBuyingQuery;
import com.chengzi.apiunion.groupbuying.service.GroupBuyingService;
import com.chengzi.common.data.beans.PageDataList;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import com.chengzi.common.util.DateUtil;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 团购列表
 *
 * @author 月汐
 * @date 2019/2/18 20:19
 */
public class QueryGroupBuyingController extends AbstractApiController<QueryGroupBuyingForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, QueryGroupBuyingForm command) throws Exception {
        GroupBuyingService groupBuyingService = BeanFactory.getBean(GroupBuyingService.class);
        ItemService itemService = BeanFactory.getBean(ItemService.class);

        GroupBuyingQuery query = new GroupBuyingQuery();
        query.setKeyword(command.getKeyword());
        query.setStatus(command.getStatus());
        query.setLimit(command.getLimit());
        query.setOffset(command.getOffset());

        List<GroupBuyingDO> groupBuyingDOList = groupBuyingService.queryGroupBuying(query);

        List<Long> itemIds = CollectionUtil.getFieldValueList(groupBuyingDOList, "itemId");
        List<ItemCO> itemCOList = itemService.getItemWithCacheByIds(itemIds);
        Map<Long, ItemCO> idItemCOMap = CollectionUtil.toMap(itemCOList, "id");

        List<QueryGroupBuyingBO> boList = new ArrayList<>();
        for (GroupBuyingDO groupBuyingDO : groupBuyingDOList) {
            QueryGroupBuyingBO bo = new QueryGroupBuyingBO();
            bo.setId(groupBuyingDO.getId());
            bo.setName(groupBuyingDO.getName());
            bo.setBanner(groupBuyingDO.getBanner());
            ItemCO itemCO = idItemCOMap.get(groupBuyingDO.getItemId());
            bo.setItemName(itemCO.getName());
            bo.setItemId(groupBuyingDO.getItemId());
            bo.setActTime(formatTime(groupBuyingDO.getStartTime(), groupBuyingDO.getEndTime()));
            bo.setParticipateNum(groupBuyingDO.getParticipateNum());
            bo.setStatus(groupBuyingDO.getStatus());
            if (bo.getStatus() == 0) {
                bo.setActStatusDesc("未启用");
            } else {
                if (groupBuyingDO.getEndTime().getTime() < System.currentTimeMillis()) {
                    bo.setActStatusDesc("已结束");
                } else if (groupBuyingDO.getStartTime().getTime() > System.currentTimeMillis()) {
                    bo.setActStatusDesc("未开始");
                } else {
                    bo.setActStatusDesc("进行中");
                }
            }
            boList.add(bo);
        }

        long total = groupBuyingService.countByQuery(query);

        return Result.buildSuccessResult(new PageDataList<>(total, command.getPage(), command.getLimit(), boList));
    }

    private String formatTime(Date startTime, Date endTime) {
        return DateUtil.formatDate(startTime, DateUtil.YYYY_MM_DD_HH_MM_SS) + "-" + DateUtil.formatDate(endTime, DateUtil.YYYY_MM_DD_HH_MM_SS);
    }

}
