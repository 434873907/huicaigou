package com.chengzi.apiunion.procurement.admin.web.controllers.shop;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.data.search.elastic.pojo.SearchResult;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.shop.QueryShopListForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.shop.QueryShopListBO;
import com.chengzi.apiunion.shop.pojo.search.ShopSearchBO;
import com.chengzi.apiunion.shop.pojo.search.query.ShopSearchQuery;
import com.chengzi.apiunion.shop.search.ShopSearchService;
import com.chengzi.apiunion.shop.service.ShopService;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import com.chengzi.common.web.formbean.EmptyForm;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 查询所有店铺
 *
 * @author 月汐
 * @date 2018/11/16 11:30
 */
public class QueryShopListController extends AbstractApiController<QueryShopListForm> {
    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, QueryShopListForm command) throws Exception {
        ShopSearchService shopSearchService = BeanFactory.getBean(ShopSearchService.class);
        ShopService shopService = BeanFactory.getBean(ShopService.class);

        ShopSearchQuery query = new ShopSearchQuery();
        query.setName(command.getName());
        query.setIsDeleted(false);
        query.setRouteId(RequestContext.getRouteId());
        query.setStatus(1);

        SearchResult<ShopSearchBO> result = shopSearchService.query(query);
        List<Long> shopIds = CollectionUtil.getFieldValueList(result.getData(), "id");

        return Result.buildSuccessResult(QueryShopListBO.convert(shopService.getByIdsWithCache(shopIds)));
    }
}
