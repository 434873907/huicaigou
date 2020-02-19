package com.chengzi.apiunion.procurement.admin.web.controllers.inventory;

import com.chengzi.apiunion.common.data.search.elastic.pojo.SearchResult;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.inventory.enums.InventoryKeywordTypeEnum;
import com.chengzi.apiunion.inventory.pojo.InventoryDO;
import com.chengzi.apiunion.inventory.service.InventoryService;
import com.chengzi.apiunion.item.enums.ItemStatusEnum;
import com.chengzi.apiunion.item.pojo.search.query.ItemSearchQuery;
import com.chengzi.apiunion.procurement.admin.web.formbean.inventory.QueryInventoryForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.inventory.QueryInventoryBO;
import com.chengzi.common.data.beans.PageDataList;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import org.apache.commons.lang3.StringUtils;
import org.summercool.hsf.util.LangUtil;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取商品库存列表
 *
 * @author 月汐
 * @date 2018/12/18 16:49
 */
public class QueryInventoryController extends AbstractApiController<QueryInventoryForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, QueryInventoryForm command) throws Exception {
        InventoryService inventoryService = BeanFactory.getBean(InventoryService.class);
        ItemSearchQuery query = new ItemSearchQuery();
        query.setSupplierIds(command.getSupplierIds());
        query.setBrandIds(command.getBrandIds());
        if (StringUtils.isNotBlank(command.getKeywordValue())) {
            if (command.getKeywordType() == InventoryKeywordTypeEnum.ITEM_ID.getCode()) {
                Long itemId = LangUtil.parseLong(command.getKeywordValue());
                if (itemId == null) {
                    return Result.buildOpFailedResult("请输入正确的商品ID");
                }
                query.setItemIds(CollectionUtil.asHashSet(itemId));
            } else if (command.getKeywordType() == InventoryKeywordTypeEnum.ITEM_NAME.getCode()) {
                query.setItemName(command.getKeywordValue());
            } else if (command.getKeywordType() == InventoryKeywordTypeEnum.UPC.getCode()) {
                query.setUpc(command.getKeywordValue());
            }
        }
        query.setChannelTypes(CollectionUtil.asHashSet(command.getChannelType()));

        if (command.getStatus() == 3) {
            query.setStatus(ItemStatusEnum.OFFLINE);
        }

        query.setFrom(command.getOffset());
        query.setSize(command.getLimit());
        SearchResult<InventoryDO> result = inventoryService.queryInventory(query);
        long total = result.getTotalHits();
        List<QueryInventoryBO> boList = new ArrayList<>();
        for (InventoryDO inventoryDO : result.getData()) {
            boList.add(QueryInventoryBO.convert(inventoryDO));
        }
        return Result.buildSuccessResult(new PageDataList<>(total, command.getPage(), command.getLimit(), boList));
    }
}
