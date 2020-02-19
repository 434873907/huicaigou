package com.chengzi.apiunion.procurement.admin.web.controllers.item;

import com.chengzi.apiunion.common.data.search.elastic.pojo.SearchResult;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.enums.ItemStatusEnum;
import com.chengzi.apiunion.item.pojo.search.ItemSearchBO;
import com.chengzi.apiunion.item.pojo.search.query.ItemSearchQuery;
import com.chengzi.apiunion.item.search.ItemSearchService;
import com.chengzi.apiunion.procurement.admin.web.formbean.item.MergeItemGroupForm;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 月汐
 * @date 2019/07/16 11:01
 */
public class GetGroupItemNumController extends AbstractApiController<MergeItemGroupForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, MergeItemGroupForm command) throws Exception {
        ItemSearchService itemSearchService = BeanFactory.getBean(ItemSearchService.class);

        if (CollectionUtil.isEmpty(command.getGroupIds())) {
            return Result.buildSuccessResult(command.getItemIds().size());
        }

        ItemSearchQuery query = new ItemSearchQuery();
        query.setGroupIds(command.getGroupIds());
        query.setFrom(0);
        query.setSize(1);
        query.setStatusList(CollectionUtil.asArrayList(ItemStatusEnum.ONLINE,ItemStatusEnum.OFFLINE));

        SearchResult<ItemSearchBO> searchResult = itemSearchService.query(query);
        return Result.buildSuccessResult(searchResult.getTotalHits() + command.getItemIds().size());
    }
}
