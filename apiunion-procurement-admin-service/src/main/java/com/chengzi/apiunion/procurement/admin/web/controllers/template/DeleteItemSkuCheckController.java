package com.chengzi.apiunion.procurement.admin.web.controllers.template;

import com.chengzi.apiunion.common.data.search.elastic.pojo.SearchResult;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.procurement.admin.web.formbean.template.DeleteItemSkuCheckForm;
import com.chengzi.apiunion.supplier.common.item.pojo.query.SupplierItemSearchQuery;
import com.chengzi.apiunion.supplier.common.item.pojo.search.SupplierItemSearchBO;
import com.chengzi.apiunion.supplier.common.item.service.SupplierItemSearchService;
import com.chengzi.common.data.validate.Result;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhiyuan
 * 删除模板商品SKU检查
 */
public class DeleteItemSkuCheckController extends AbstractApiController<DeleteItemSkuCheckForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, DeleteItemSkuCheckForm command) throws Exception {

        SupplierItemSearchService itemSearchService = BeanFactory.getBean(SupplierItemSearchService.class);

        SupplierItemSearchQuery searchQuery = new SupplierItemSearchQuery();
        searchQuery.setIncludesFields(new String[]{"id"});
        searchQuery.setTemplateId(command.getTemplateItemId());
        searchQuery.setTemplateItemSkuId(command.getTemplateItemSkuId());
        try {
            SearchResult<SupplierItemSearchBO> searchResult = itemSearchService.queryItem(searchQuery);
            if (searchResult.isEmpty()) {
                return Result.buildSuccessResult("该规格可以删除");
            } else {
                return Result.buildOpFailedResult("有商家正使用该规格，无法删除");
            }
        } catch (Exception e) {
            LOGGER.error("检测规格错误", e);
            return Result.buildOpFailedResult("规格检测错误");
        }
    }
}
