package com.chengzi.apiunion.procurement.admin.web.controllers.brand;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.data.search.elastic.pojo.SearchResult;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.brand.service.BrandService;
import com.chengzi.apiunion.item.enums.ItemStatusEnum;
import com.chengzi.apiunion.item.pojo.search.ItemSearchBO;
import com.chengzi.apiunion.item.pojo.search.query.ItemSearchQuery;
import com.chengzi.apiunion.item.search.ItemSearchService;
import com.chengzi.apiunion.procurement.admin.web.formbean.brand.DeleteBrandForm;
import com.chengzi.common.data.validate.ErrorConstants;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import org.apache.ecs.html.Col;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

/**
 * 删除品牌
 *
 * @author 月汐
 * @date 2018/10/15 16:07
 */
public class DeleteBrandController extends AbstractApiController<DeleteBrandForm> {

    @Override
    protected Result<?> doBiz(HttpServletRequest request, HttpServletResponse response, DeleteBrandForm command) throws Exception {
        ItemSearchService itemSearchService = BeanFactory.getBean(ItemSearchService.class);
        ItemSearchQuery query = new ItemSearchQuery();
        Set<Long> brandIds = new HashSet<>();
        brandIds.add(command.getId());
        query.setBrandIds(brandIds);
        query.setStatusList(CollectionUtil.asArrayList(ItemStatusEnum.ONLINE,ItemStatusEnum.OFFLINE));
        query.setSize(1);
        query.setFrom(0);
        query.setRouteId(RequestContext.getRouteId());
        SearchResult<ItemSearchBO> itemSearchBOSearchResult = itemSearchService.query(query);
        if (itemSearchBOSearchResult.getData() != null && itemSearchBOSearchResult.getData().size() > 0) {
            return Result.buildOpFailedResult("当前品牌含有商品，请迁移品牌后再进行删除");
        }
        BrandService service = BeanFactory.getBean(BrandService.class);
        int result = service.delete(command.getId());
        if (result == 1) {
            return Result.buildSuccessResult("删除成功");
        }
        return Result.buildFailResult(ErrorConstants.ERR_OP_FAILED, "操作失败");
    }

}
