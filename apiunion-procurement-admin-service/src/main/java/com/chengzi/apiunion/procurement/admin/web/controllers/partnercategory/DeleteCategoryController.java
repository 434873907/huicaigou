package com.chengzi.apiunion.procurement.admin.web.controllers.partnercategory;

import com.chengzi.apiunion.common.context.RequestContext;
import com.chengzi.apiunion.common.data.search.elastic.pojo.SearchResult;
import com.chengzi.apiunion.common.web.controllers.AbstractApiController;
import com.chengzi.apiunion.item.enums.ItemStatusEnum;
import com.chengzi.apiunion.item.pojo.search.ItemSearchBO;
import com.chengzi.apiunion.item.pojo.search.query.ItemSearchQuery;
import com.chengzi.apiunion.item.search.ItemSearchService;
import com.chengzi.apiunion.partnercategory.constant.PartnerCategoryConstant;
import com.chengzi.apiunion.partnercategory.pojo.CategoryUnit;
import com.chengzi.apiunion.partnercategory.pojo.PartnerCategoryDO;
import com.chengzi.apiunion.partnercategory.service.PartnerCategoryService;
import com.chengzi.apiunion.procurement.admin.web.formbean.partnercategory.DeleteCategoryForm;
import com.chengzi.common.data.validate.ErrorConstants;
import com.chengzi.common.data.validate.Result;
import com.chengzi.common.util.CollectionUtil;
import org.summercool.web.servlet.BeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 删除类目
 *
 * @author 月汐
 * @date 2018/10/12 16:22
 */
public class DeleteCategoryController extends AbstractApiController<DeleteCategoryForm> {
    @Override
    protected Result<String> doBiz(HttpServletRequest request, HttpServletResponse response, DeleteCategoryForm command) throws Exception {
        PartnerCategoryService service = BeanFactory.getBean(PartnerCategoryService.class);
        PartnerCategoryDO partnerCategoryDO = service.getById(command.getId());
        ItemSearchService itemSearchService = BeanFactory.getBean(ItemSearchService.class);
        ItemSearchQuery query = new ItemSearchQuery();
        if (partnerCategoryDO.getDepth() == PartnerCategoryConstant.FIRST_CLASS_CATEGORY) {
            query.setCategorys(CollectionUtil.asHashSet(new CategoryUnit(partnerCategoryDO.getId(), 0, 0)));
        } else if (partnerCategoryDO.getDepth() == PartnerCategoryConstant.SECOND_CLASS_CATEGORY) {
            query.setCategorys(CollectionUtil.asHashSet(new CategoryUnit(0, partnerCategoryDO.getId(), 0)));
        } else if (partnerCategoryDO.getDepth() == PartnerCategoryConstant.THIRD_CLASS_CATEGORY) {
            query.setCategorys(CollectionUtil.asHashSet(new CategoryUnit(0, 0, partnerCategoryDO.getId())));
        }
        query.setStatus(ItemStatusEnum.ONLINE);
        query.setSize(1);
        query.setFrom(0);
        query.setRouteId(RequestContext.getRouteId());
        SearchResult<ItemSearchBO> itemSearchBOSearchResult = itemSearchService.query(query);
        if (itemSearchBOSearchResult.getData() != null && itemSearchBOSearchResult.getData().size() > 0) {
            return Result.buildOpFailedResult("该类目下还有已上架商品");
        }
        int result = service.delete(command.getId());
        if (result == 1) {
            return Result.buildSuccessResult("删除成功");
        }
        return Result.buildFailResult(ErrorConstants.ERR_OP_FAILED, "操作失败");
    }
}
