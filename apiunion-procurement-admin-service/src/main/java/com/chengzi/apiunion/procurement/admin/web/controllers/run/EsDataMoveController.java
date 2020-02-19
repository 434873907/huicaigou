/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.controllers.run;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequestBuilder;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import com.chengzi.apiunion.common.data.search.elastic.SearchClientFactory;
import com.chengzi.apiunion.common.data.search.elastic.pojo.ScrollResult;
import com.chengzi.apiunion.common.web.controllers.AbstractManageController;
import com.chengzi.apiunion.procurement.admin.web.formbean.run.EsDataMoveForm;
import com.chengzi.common.data.validate.Result;

/**
 * ES数据迁移
 * 
 * @author Kolor
 */
public class EsDataMoveController extends AbstractManageController<EsDataMoveForm> {

    private static final int _1800_000 = 1800_000;

    @Override
    protected Result<?> doInnerBiz(HttpServletRequest request, HttpServletResponse response, EsDataMoveForm command) throws Exception {
        Client client = SearchClientFactory.getClient(command.getBizKey());
        SearchRequestBuilder requestBuilder = client.prepareSearch(command.getSourceIndex()).setTypes(command.getBizKey().getType())
                .setSearchType(SearchType.DEFAULT).setSize(1000).setScroll(new TimeValue(_1800_000));

        // 自定义条件装配
        if (command.getBeginTime() != null) {
            BoolQueryBuilder postFilter = QueryBuilders.boolQuery();
            postFilter.must(QueryBuilders.rangeQuery("esRefreshTime").from(command.getBeginTime()));
            requestBuilder.setPostFilter(postFilter);
        }
        try {
            SearchResponse res = requestBuilder.execute().get();
            ScrollResult<T> scrollResult = new ScrollResult<>();
            scrollResult.setScrollId(res.getScrollId());
            scrollResult.setTotalHits(res.getHits().getTotalHits());

            BulkRequestBuilder bulkRequestBuilder = client.prepareBulk();
            SearchHits searchHits = res.getHits();
            if (searchHits != null) {
                for (SearchHit searchHit : searchHits) {
                    String id = searchHit.getId();
                    if (!StringUtils.contains(id, "#")) {
                        continue;
                    }
                    String json = searchHit.getSourceAsString();
                    //
                    bulkRequestBuilder
                            .add(client.prepareIndex(command.getDestIndex(), command.getBizKey().getType(), id).setSource(json, XContentType.JSON));
                }
            }

            // 如果未获取完全，则继续后续数据的获取
            if (scrollResult.getTotalHits() > scrollResult.getData().size()) {
                while (true) {
                    SearchScrollRequestBuilder scrollRequest = client.prepareSearchScroll(scrollResult.getScrollId())
                            .setScroll(new TimeValue(_1800_000));
                    res = scrollRequest.execute().actionGet();

                    searchHits = res.getHits();
                    if (searchHits != null && searchHits.getTotalHits() > 0) {
                        for (SearchHit searchHit : searchHits) {
                            String id = searchHit.getId();
                            if (!StringUtils.contains(id, "#")) {
                                continue;
                            }
                            String json = searchHit.getSourceAsString();
                            //
                            bulkRequestBuilder.add(client.prepareIndex(command.getDestIndex(), command.getBizKey().getType(), id).setSource(json,
                                    XContentType.JSON));
                        }
                        bulkRequestBuilder.execute().get();
                        bulkRequestBuilder = client.prepareBulk();
                    } else {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Result.buildEmptySuccess();
    }

}
