package com.chengzi.apiunion.procurement.admin.web.controllers.brand;

import com.chengzi.apiunion.brand.pojo.BrandDO;
import com.chengzi.apiunion.brand.pojo.search.BrandSearchBO;
import com.chengzi.apiunion.brand.pojo.search.query.BrandSearchQuery;
import com.chengzi.apiunion.brand.search.BrandSearchService;
import com.chengzi.apiunion.brand.service.BrandService;
import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.data.search.elastic.pojo.OrderBy;
import com.chengzi.apiunion.common.data.search.elastic.pojo.SearchResult;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.enums.ItemStatusEnum;
import com.chengzi.apiunion.item.pojo.search.AggBucket;
import com.chengzi.apiunion.item.pojo.search.FieldAggBuckets;
import com.chengzi.apiunion.item.pojo.search.ItemSearchBO;
import com.chengzi.apiunion.item.pojo.search.query.ItemSearchField;
import com.chengzi.apiunion.item.pojo.search.query.ShareAggregationQuery;
import com.chengzi.apiunion.item.search.ItemSearchService;
import com.chengzi.apiunion.procurement.admin.web.formbean.brand.QueryBrandListForm;
import com.chengzi.apiunion.procurement.admin.web.pojo.brand.QueryBrandListBO;
import com.chengzi.common.data.beans.PageDataList;
import com.chengzi.common.data.support.Tuple2;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import org.elasticsearch.search.sort.SortOrder;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 品牌列表查询
 *
 * @author 月汐
 * @date 2018/10/15 15:09
 */
public class QueryBrandListController extends AbstractApiController<QueryBrandListForm> {
    @Override
    protected Result<PageDataList<QueryBrandListBO>> doBiz(HttpServletRequest request, HttpServletResponse response, QueryBrandListForm command) throws Exception {
        BrandService brandService = BeanFactory.getBean(BrandService.class);
        BrandSearchService brandSearchService = BeanFactory.getBean(BrandSearchService.class);
        ItemSearchService itemSearchService = BeanFactory.getBean(ItemSearchService.class);

        BrandSearchQuery query = new BrandSearchQuery();
        query.setRouteId(RequestContext.getRouteId());
        query.setLetter(command.getLetter());
        query.setName(command.getName());
        query.setSize(command.getLimit());
        query.setStatus(command.getStatus());
        query.setFrom(command.getOffset());
        query.setOrderBy(CollectionUtil.asArrayList(
                new OrderBy("propStatus", SortOrder.ASC),
                new OrderBy("toppingTime", SortOrder.DESC),
                new OrderBy("id", SortOrder.DESC)
        ));

        SearchResult<BrandSearchBO> searchResult = brandSearchService.query(query);
        if (searchResult.isEmpty()) {
            return Result.buildSuccessResult(new PageDataList<>(0, command.getPage(), command.getLimit(), new ArrayList<>()));
        }

        List<Long> brandIds = CollectionUtil.getFieldValueList(searchResult.getData(), "id");

        Map<Long, Tuple2<Long, Long>> brandItemNumTuple = itemSearchService.aggByBrand(brandIds);

        List<BrandDO> list = brandService.getByIds(brandIds);
        Map<Long, BrandDO> idBrandDOMap = CollectionUtil.toMap(list, "id");
        List<QueryBrandListBO> resultList = new ArrayList<>();
        for (BrandSearchBO brandSearchBO : searchResult.getData()) {
            BrandDO row = idBrandDOMap.get(brandSearchBO.getId());
            if (row == null) {
                continue;
            }
            Tuple2<Long, Long> itemNumTuple = brandItemNumTuple.get(row.getId());
            if (itemNumTuple == null) {
                resultList.add(QueryBrandListBO.convert(row,
                        0,
                        0));
            } else {
                resultList.add(QueryBrandListBO.convert(row,
                        itemNumTuple.getV1().intValue(),
                        itemNumTuple.getV2().intValue()));
            }
        }
        long total = searchResult.getTotalHits();
        return Result.buildSuccessResult(new PageDataList<>(total, command.getPage(), command.getLimit(), resultList));
    }
}
