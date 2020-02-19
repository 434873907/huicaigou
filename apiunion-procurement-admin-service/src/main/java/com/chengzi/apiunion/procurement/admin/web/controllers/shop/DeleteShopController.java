package com.chengzi.apiunion.procurement.admin.web.controllers.shop;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.data.search.elastic.pojo.SearchResult;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.enums.ItemStatusEnum;
import com.chengzi.apiunion.item.pojo.search.ItemSearchBO;
import com.chengzi.apiunion.item.pojo.search.query.ItemSearchQuery;
import com.chengzi.apiunion.item.search.ItemSearchService;
import com.chengzi.apiunion.procurement.admin.web.formbean.shop.DeleteShopForm;
import com.chengzi.apiunion.shop.service.ShopService;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

/**
 * 删除店铺
 *
 * @author 月汐
 * @date 2018/11/15 17:23
 */
public class DeleteShopController extends AbstractApiController<DeleteShopForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, DeleteShopForm command) throws Exception {
        ItemSearchService itemSearchService = BeanFactory.getBean(ItemSearchService.class);
        ItemSearchQuery query = new ItemSearchQuery();
        Set<Long> shopIds = new HashSet<>();
        shopIds.add(command.getId());
        query.setShopIds(shopIds);
        query.setStatus(ItemStatusEnum.ONLINE);
        query.setSize(1);
        query.setFrom(0);
        query.setRouteId(RequestContext.getRouteId());
        SearchResult<ItemSearchBO> itemSearchBOSearchResult = itemSearchService.query(query);
        if (itemSearchBOSearchResult.getData() != null && itemSearchBOSearchResult.getData().size() > 0) {
            return Result.buildOpFailedResult("该店铺下还有已上架商品");
        }
        ShopService shopService = BeanFactory.getBean(ShopService.class);
        return shopService.delete(command.getId());
    }

}
