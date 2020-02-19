package com.chengzi.apiunion.procurement.admin.web.controllers.supplier.item;

import com.chengzi.apiunion.common.data.search.elastic.pojo.SearchResult;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.supplier.item.CheckTemplateItemDetailForm;
import com.chengzi.apiunion.procurement.admin.web.helper.template.TemplateItemDetailHelper;
import com.chengzi.apiunion.procurement.admin.web.pojo.template.TemplateItemDetailBO;
import com.chengzi.apiunion.supplier.common.item.pojo.query.SupplierTemplateItemSearchQuery;
import com.chengzi.apiunion.supplier.common.item.pojo.search.SupplierTemplateItemSearchBO;
import com.chengzi.apiunion.supplier.common.item.service.SupplierTemplateItemSearchService;
import com.chengzi.apiunion.supplier.common.template.util.TemplateItemHelper;
import com.chengzi.common.data.beans.PageDataList;
import com.chengzi.common.data.support.Range;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 *  A端查询模板商品数据
 */
public class CheckSameTemplateItemDetailController extends AbstractApiController<CheckTemplateItemDetailForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, CheckTemplateItemDetailForm command) throws Exception {

        SupplierTemplateItemSearchService templateItemSearchService = BeanFactory.getBean(SupplierTemplateItemSearchService.class);

        //构建查询 Bean
        SupplierTemplateItemSearchQuery searchQuery = buildSearchQuery(command);
        SearchResult<SupplierTemplateItemSearchBO> searchResult = templateItemSearchService.queryItem(searchQuery);
        long total = searchResult.getTotalHits();

        if (total > 0) {
            List<Long> templateItemIds = CollectionUtil.getFieldValueList(searchResult.getData(), "id");

            List<TemplateItemDetailBO> templateItemDetailBOS = TemplateItemDetailHelper.buildTemplateItemDetailBOS(templateItemIds);
            return Result.buildSuccessResult(new PageDataList(total, command.getPage(), command.getLimit(), templateItemDetailBOS));
        }
        return Result.buildSuccessResult(new PageDataList<>(0, command.getPage(), command.getLimit(), new ArrayList<>()));
    }

    private SupplierTemplateItemSearchQuery buildSearchQuery(CheckTemplateItemDetailForm form) {
        SupplierTemplateItemSearchQuery searchQuery = new SupplierTemplateItemSearchQuery();

        searchQuery.setKeyword(form.getKeyword());
        searchQuery.setFrom(form.getOffset());
        searchQuery.setSize(form.getLimit());

        return searchQuery;
    }
}
