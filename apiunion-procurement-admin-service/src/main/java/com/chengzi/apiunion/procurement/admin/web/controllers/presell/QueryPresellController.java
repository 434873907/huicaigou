package com.chengzi.apiunion.procurement.admin.web.controllers.presell;

import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.pojo.cache.ItemCO;
import com.chengzi.apiunion.item.service.ItemService;
import com.chengzi.apiunion.procurement.admin.web.formbean.presell.QueryPresellForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.presell.QueryPresellBO;
import com.chengzi.apiunion.presell.pojo.PresellDO;
import com.chengzi.apiunion.presell.pojo.PresellQuery;
import com.chengzi.apiunion.presell.service.PresellService;
import com.chengzi.common.data.beans.PageDataList;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 月汐
 * @date 2019/2/26 9:56
 */
public class QueryPresellController extends AbstractApiController<QueryPresellForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, QueryPresellForm command) throws Exception {
        PresellService presellService = BeanFactory.getBean(PresellService.class);
        ItemService itemService = BeanFactory.getBean(ItemService.class);

        PresellQuery query = new PresellQuery();
        query.setKeyword(command.getKeyword());
        query.setStatus(command.getStatus());
        query.setLimit(command.getLimit());
        query.setLimit(command.getLimit());
        query.setOffset(command.getOffset());

        List<PresellDO> presellDOList = presellService.query(query);

        List<Long> itemIds = CollectionUtil.getFieldValueList(presellDOList, "itemId");
        List<ItemCO> itemCOList = itemService.getItemWithCacheByIds(itemIds);
        Map<Long, ItemCO> idItemCOMap = CollectionUtil.toMap(itemCOList, "id");

        List<QueryPresellBO> boList = new ArrayList<>();
        for (PresellDO presellDO : presellDOList) {
            QueryPresellBO bo = new QueryPresellBO();
            bo.setId(presellDO.getId());
            bo.setName(presellDO.getName());
            ItemCO itemCO = idItemCOMap.get(presellDO.getItemId());
            if (itemCO == null) {
                continue;
            }
            bo.setItemName(itemCO.getName());
            bo.setDepositAmount(presellDO.getDepositAmount());
            bo.setDepositPaidNum(presellDO.getDepositPaidNum());
            bo.setTailPaidNum(presellDO.getTailPaidNum());
            bo.setActualPaidAmount(presellDO.getActualPaidAmount());
            bo.setStatus(presellDO.getStatus());
            if (bo.getStatus() == 0) {
                bo.setActStatusDesc("未启用");
            } else {
                if (presellDO.getTailEndTime().getTime() < System.currentTimeMillis()) {
                    bo.setActStatusDesc("已结束");
                } else if (presellDO.getDepositStartTime().getTime() > System.currentTimeMillis()) {
                    bo.setActStatusDesc("未开始");
                } else {
                    bo.setActStatusDesc("进行中");
                }
            }
            boList.add(bo);
        }

        long total = presellService.countByQuery(query);

        return Result.buildSuccessResult(new PageDataList<>(total, command.getPage(), command.getLimit(), boList));
    }
}
